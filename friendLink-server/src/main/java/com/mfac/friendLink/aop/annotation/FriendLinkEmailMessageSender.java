package com.mfac.friendLink.aop.annotation;

import java.lang.annotation.*;

/**
 * 自定义友链邮件消息发送注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FriendLinkEmailMessageSender {
}
