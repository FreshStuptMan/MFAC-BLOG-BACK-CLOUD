package com.mfac.email.listeners;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfac.email.client.FriendLinkClient;
import com.mfac.email.constant.EmailConstant;
import com.mfac.email.constant.RabbitMQConstant;
import com.mfac.email.pojo.Result;
import com.mfac.email.pojo.dto.FriendLinkEmailMessage;
import com.mfac.email.pojo.entity.FriendLink;
import com.mfac.email.pojo.entity.FriendLinkEmailRecord;
import com.mfac.email.util.EmailUtil;
import com.mfac.email.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Objects;

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
    private void EmailMessageSenderListener(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendLinkEmailMessage emailMessage = objectMapper.readValue(message.getBody(), FriendLinkEmailMessage.class);
        ThreadLocalUtil.setCurrentId(emailMessage.getUserId());
        // 格式转换
        LinkedHashMap<String, Object> recordMap = (LinkedHashMap) friendLinkClient.friendLinkEmailRecordDetail(emailMessage.getId()).getData();
        FriendLinkEmailRecord record = tranToRecord(recordMap);
        // 格式转换
        LinkedHashMap<String, Object> friendLinkMap = (LinkedHashMap) friendLinkClient.friendLinkDetail(record.getFriendLinkId()).getData();
        FriendLink friendLink = tranToFriendLink(friendLinkMap);

        // 发送邮件
        if (EmailConstant.FRIEND_LINK_UP_NOTIFY_EMAIL.equals(record.getEmailType())) {
            emailUtil.FriendLinkUpEmailSender(friendLink);
        } else if (EmailConstant.FRIEND_LINK_DOWN_NOTIFY_EMAIL.equals(record.getEmailType())) {
            emailUtil.FriendLinkDownEmailSender(friendLink);
        } else {
            // 邮件类型错误
            updateEmailRecordStatus(emailMessage, EmailConstant.SEND_STATUS_FAULT, "邮件类型错误，发送失败");
            return;
        }
        // 更新邮件记录状态
        updateEmailRecordStatus(emailMessage, EmailConstant.SEND_STATUS_SUCCESS, null);
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
    private void TryTimeExhaustedListener(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendLinkEmailMessage emailMessage = objectMapper.readValue(message.getBody(), FriendLinkEmailMessage.class);
        ThreadLocalUtil.setCurrentId(emailMessage.getUserId());
        updateEmailRecordStatus(emailMessage, EmailConstant.SEND_STATUS_FAULT, "消费者消费失败，重试次数耗尽！请检查邮箱消息或消费者消费部分！");
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
    private void OverTimeListener(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendLinkEmailMessage emailMessage = objectMapper.readValue(message.getBody(), FriendLinkEmailMessage.class);
        ThreadLocalUtil.setCurrentId(emailMessage.getUserId());
        updateEmailRecordStatus(emailMessage, EmailConstant.SEND_STATUS_FAULT, "消费者消费失败，消息超时！");
    }



    /**
     * 更新记录状态
     * @param message
     * @param status
     * @param reason
     */
    private void updateEmailRecordStatus (FriendLinkEmailMessage message, Integer status, String reason) {
        FriendLinkEmailRecord record = new FriendLinkEmailRecord();
        LinkedHashMap<String, Object> recordMap = (LinkedHashMap) friendLinkClient.friendLinkEmailRecordDetail(message.getId()).getData();
        FriendLinkEmailRecord dbRecord = tranToRecord(recordMap);
        if (EmailConstant.SEND_STATUS_SENDING.equals(dbRecord.getStatus())) {
            record.setId(message.getId());
            record.setStatus(status);
            record.setFaultReason(reason);
            friendLinkClient.friendLinkEmailRecordChangeStatus(record);
        }
    }

    /**
     * 格式转换
     * @param map
     * @return
     */
    private FriendLinkEmailRecord tranToRecord(LinkedHashMap<String, Object> map) {
        FriendLinkEmailRecord record = new FriendLinkEmailRecord();
        record.setId(Long.valueOf((String) map.get("id")));
        record.setFriendLinkId(Long.valueOf((String) map.get("friendLinkId")));
        record.setEmailType((Integer) map.get("emailType"));
        return record;
    }


    /**
     * 格式转换
     * @param map
     * @return
     */
    private FriendLink tranToFriendLink(LinkedHashMap<String, Object> map) {
        FriendLink friendLink = new FriendLink();
        friendLink.setId(Long.valueOf((String) map.get("id")));
        friendLink.setName((String) map.get("name"));
        friendLink.setLink((String) map.get("link"));
        friendLink.setDescription((String) map.get("description"));
        friendLink.setAuthor((String) map.get("author"));
        friendLink.setEmail((String) map.get("email"));
        if (map.get("downReason") != null) {
            friendLink.setDownReason((String) map.get("downReason"));
        }
        return friendLink;
    }
}
