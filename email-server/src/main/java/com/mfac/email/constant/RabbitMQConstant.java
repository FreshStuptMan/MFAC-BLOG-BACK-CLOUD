package com.mfac.email.constant;

public class RabbitMQConstant {
    public static final String FRIEND_LINK_EMAIL_MESSAGE_QUEUE = "direct.friendLink.email.message.queue";
    public static final String EXCHANGE = "mfac.direct.exchange";
    public static final String FRIEND_LINK_EMAIL_MESSAGE_QUEUE_KEY = "friendLink.email";

    /**
     * 消费重试耗尽处理
     */
    public static final String TRY_TIME_EXHAUSTED_QUEUE = "direct.tryTimeExhausted.queue";
    public static final String TRY_TIME_EXHAUSTED_QUEUE_KEY = "tryTimeExhausted";

    /**
     * 消息超时处理
     */
    public static final String OVER_TIME_QUEUE = "direct.overTime.queue";
    public static final String OVER_TIME_QUEUE_KEY = "overTime";
    public static final String FRIEND_LINK_EMAIL_MESSAGE_TTL = "300000";



}
