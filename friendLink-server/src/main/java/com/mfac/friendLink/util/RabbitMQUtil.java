package com.mfac.friendLink.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.mfac.friendLink.constant.EmailConstant;
import com.mfac.friendLink.constant.RabbitMQConstant;
import com.mfac.friendLink.pojo.dto.FriendLinkEmailMessage;
import com.mfac.friendLink.pojo.entity.FriendLinkEmailRecord;
import com.mfac.friendLink.service.FriendLinkEmailRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.UUID;
@Slf4j
@Component
public class RabbitMQUtil {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private FriendLinkEmailRecordService friendLinkEmailRecordService;
    /**
     * 生产者
     * 邮件消息发送者
     * @param id
     */
    public void EmailMessageSender (Long id) {
        try {
            CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
            cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
                @Override
                public void onFailure(Throwable ex) {
                    // 这个是处理Future发生的异常，基本不会触发
                    updateEmailRecordStatus(id, EmailConstant.SEND_STATUS_FAULT, "生产者报错(RabbitMQUtil.EmailMessageSender):"+ex.getMessage());

                }
                @Override
                public void onSuccess(CorrelationData.Confirm result) {
                    // Future 接收到回调结果的处理逻辑
                    if (!result.isAck()) {
                        // 发送失败，更新邮件记录状态
                        updateEmailRecordStatus(id, EmailConstant.SEND_STATUS_FAULT, "生产者报错(RabbitMQUtil.EmailMessageSender):NACK");
                    }
                }
            });
            // 设置回调处理并发送消息
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            Message message = new Message(JSON.toJSONBytes(new FriendLinkEmailMessage(id, ThreadLocalUtil.getCurrentId())), messageProperties);
            rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE, RabbitMQConstant.FRIEND_LINK_EMAIL_MESSAGE_QUEUE_KEY, message, cd);
        } catch (Exception e) {
            // 发送失败，更新邮件记录状态
            updateEmailRecordStatus(id, EmailConstant.SEND_STATUS_FAULT, "生产者报错(RabbitMQUtil.EmailMessageSender):"+e.getMessage());
        }
    }

    /**
     * 更新记录状态
     * @param id
     * @param status
     * @param reason
     */
    private void updateEmailRecordStatus (Long id, Integer status, String reason) {
        FriendLinkEmailRecord record = new FriendLinkEmailRecord();
        FriendLinkEmailRecord dbRecord = friendLinkEmailRecordService.detail(id);
        if (EmailConstant.SEND_STATUS_SENDING.equals(dbRecord.getStatus())) {
            record.setId(id);
            record.setStatus(status);
            record.setFaultReason(reason);
            friendLinkEmailRecordService.changeStatus(record);
        }
    }
}
