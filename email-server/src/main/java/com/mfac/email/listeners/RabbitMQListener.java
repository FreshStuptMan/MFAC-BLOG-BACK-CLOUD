package com.mfac.email.listeners;
import com.alibaba.fastjson.JSON;
import com.mfac.email.client.FriendLinkClient;
import com.mfac.email.constant.EmailConstant;
import com.mfac.email.constant.RabbitMQConstant;
import com.mfac.email.pojo.dto.FriendLinkEmailMessage;
import com.mfac.email.pojo.entity.FriendLink;
import com.mfac.email.pojo.entity.FriendLinkEmailRecord;
import com.mfac.email.util.EmailUtil;
import com.mfac.email.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消费者
 * 发送邮件
 */
@Slf4j
@Component
public class RabbitMQListener {

    @Resource
    private FriendLinkClient friendLinkClient;
    @Resource
    private EmailUtil emailUtil;

    /**
     * 业务消费者
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMQConstant.FRIEND_LINK_EMAIL_MESSAGE_QUEUE),
            exchange = @Exchange(name = RabbitMQConstant.EXCHANGE, type = ExchangeTypes.DIRECT),
            key = RabbitMQConstant.FRIEND_LINK_EMAIL_MESSAGE_QUEUE_KEY,
            arguments = {
                    @Argument(name = "x-dead-letter-exchange", value = RabbitMQConstant.EXCHANGE),
                    @Argument(name = "x-dead-letter-routing-key", value = RabbitMQConstant.OVER_TIME_QUEUE_KEY),
                    @Argument(name = "x-message-ttl", value = RabbitMQConstant.FRIEND_LINK_EMAIL_MESSAGE_TTL, type = "java.lang.Integer")
            }
    ))
    private void EmailMessageSenderListener(FriendLinkEmailMessage message) {
        FriendLinkEmailRecord record = (FriendLinkEmailRecord) friendLinkClient.friendLinkEmailRecordDetail(message.getId()).getData();
        FriendLink friendLink = (FriendLink) friendLinkClient.friendLinkDetail(record.getFriendLinkId()).getData();
        // 发送邮件
        if (EmailConstant.FRIEND_LINK_UP_NOTIFY_EMAIL.equals(record.getEmailType())) {
            emailUtil.FriendLinkUpEmailSender(friendLink);
        } else if (EmailConstant.FRIEND_LINK_DOWN_NOTIFY_EMAIL.equals(record.getEmailType())) {
            emailUtil.FriendLinkDownEmailSender(friendLink);
        } else {
            // 邮件类型错误
            updateEmailRecordStatus(message, EmailConstant.SEND_STATUS_FAULT, "邮件类型错误，发送失败");
            return;
        }
        // 更新邮件记录状态
        updateEmailRecordStatus(message, EmailConstant.SEND_STATUS_SUCCESS, null);
    }

    /**
     * 重试耗尽消费者
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMQConstant.TRY_TIME_EXHAUSTED_QUEUE),
            exchange = @Exchange(name = RabbitMQConstant.EXCHANGE, type = ExchangeTypes.DIRECT),
            key = RabbitMQConstant.TRY_TIME_EXHAUSTED_QUEUE_KEY
    ))
    private void TryTimeExhaustedListener(FriendLinkEmailMessage message) {
        updateEmailRecordStatus(message, EmailConstant.SEND_STATUS_FAULT, "消费者消费失败，重试次数耗尽！请检查邮箱消息或消费者消费部分！");
    }


    /**
     * 消息超时消费者
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMQConstant.OVER_TIME_QUEUE),
            exchange = @Exchange(name = RabbitMQConstant.EXCHANGE, type = ExchangeTypes.DIRECT),
            key = RabbitMQConstant.OVER_TIME_QUEUE_KEY
    ))
    private void OverTimeListener(FriendLinkEmailMessage message) {
        updateEmailRecordStatus(message, EmailConstant.SEND_STATUS_FAULT, "消费者消费失败，消息超时！");
    }



    /**
     * 更新记录状态
     * @param message
     * @param status
     * @param reason
     */
    private void updateEmailRecordStatus (FriendLinkEmailMessage message, Integer status, String reason) {
        ThreadLocalUtil.setCurrentId(message.getUserId());
        FriendLinkEmailRecord record = new FriendLinkEmailRecord();
        FriendLinkEmailRecord dbRecord = (FriendLinkEmailRecord) friendLinkClient.friendLinkEmailRecordDetail(message.getId()).getData();
        if (EmailConstant.SEND_STATUS_SENDING.equals(dbRecord.getStatus())) {
            record.setId(message.getId());
            record.setStatus(status);
            record.setFaultReason(reason);
            friendLinkClient.friendLinkEmailRecordChangeStatus(record);
        }
    }
}
