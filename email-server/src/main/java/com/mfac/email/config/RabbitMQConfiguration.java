package com.mfac.email.config;

import com.mfac.email.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Configuration
public class RabbitMQConfiguration {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             * 当消息投递到交换机，而找不到对应队列时，触发
             * @param returnedMessage
             */
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.error("消息投递失败,触发 return callback");
                log.error("exchange: {}", returnedMessage.getExchange());
                log.error("routingKey: {}", returnedMessage.getRoutingKey());
                log.error("message: {}", returnedMessage.getMessage());
                log.error("replyCode: {}", returnedMessage.getReplyCode());
                log.error("replyText: {}", returnedMessage.getReplyText());
            }
        });
    }

    /**
     * 消费者消费失败重试耗尽时触发投递
     * @param rabbitTemplate
     * @return
     */
    @Bean
    public MessageRecoverer republishMessageRecover(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, RabbitMQConstant.EXCHANGE, RabbitMQConstant.TRY_TIME_EXHAUSTED_QUEUE_KEY);
    }
}