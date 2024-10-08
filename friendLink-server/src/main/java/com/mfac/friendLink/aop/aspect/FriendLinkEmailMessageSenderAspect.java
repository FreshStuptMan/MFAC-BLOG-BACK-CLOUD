package com.mfac.friendLink.aop.aspect;

import com.mfac.friendLink.constant.EmailConstant;
import com.mfac.friendLink.constant.FriendLinkConstant;
import com.mfac.friendLink.pojo.entity.FriendLink;
import com.mfac.friendLink.pojo.entity.FriendLinkEmailRecord;
import com.mfac.friendLink.service.FriendLinkEmailRecordService;
import com.mfac.friendLink.service.FriendLinkService;
import com.mfac.friendLink.util.RabbitMQUtil;
import com.mfac.friendLink.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class FriendLinkEmailMessageSenderAspect {
    @Resource
    private FriendLinkEmailRecordService friendLinkEmailRecordService;

    @Resource
    private FriendLinkService friendLinkService;
    @Resource
    private RabbitMQUtil rabbitMQUtil;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.mfac.friendLink.aop.annotation.FriendLinkEmailMessageSender)")
    public void FriendLinkEmailMessageSenderPointcut() {}

    /**
     * 友链状态成功修改后，投递邮件消息至消息队列
     */
    @AfterReturning("FriendLinkEmailMessageSenderPointcut()")
    public void afterReturningAdvice (JoinPoint joinPoint) {
        log.info("友链状态修改成功=====触发切片=====开始发送邮件消息！");
        Object[] args = joinPoint.getArgs();
        Long id = SnowFlakeUtil.create();
        if (args.length > 0 && args[0] instanceof FriendLink) {
            // 获取友链信息
            FriendLink friendLink = (FriendLink) args[0];
            FriendLink db = friendLinkService.detail(friendLink.getId());
            // 记录邮件消息
            FriendLinkEmailRecord record = new FriendLinkEmailRecord();
            record.setId(id);
            record.setEmail(db.getEmail());
            record.setRecordTime(LocalDateTime.now());
            record.setStatus(EmailConstant.SEND_STATUS_SENDING);
            record.setFriendLinkId(friendLink.getId());
            if (FriendLinkConstant.UP_STATUS.equals(friendLink.getStatus())) {
                record.setEmailType(EmailConstant.FRIEND_LINK_UP_NOTIFY_EMAIL);
            } else {
                record.setEmailType(EmailConstant.FRIEND_LINK_DOWN_NOTIFY_EMAIL);
            }
            friendLinkEmailRecordService.create(record);
            // 投递消息
            rabbitMQUtil.EmailMessageSender(id);
        }
        log.info("切片执行结束，邮件消息投递成功！");
    }
}
