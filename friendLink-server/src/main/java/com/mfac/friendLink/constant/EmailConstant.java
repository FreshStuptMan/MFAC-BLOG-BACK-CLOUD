package com.mfac.friendLink.constant;

public class EmailConstant {
    /**
     * 友链上架通知邮件
     */
    public static final Integer FRIEND_LINK_UP_NOTIFY_EMAIL = 1;
    /**
     *   友链下架通知邮件
     */
    public static final Integer FRIEND_LINK_DOWN_NOTIFY_EMAIL = 2;

    /**
     * 发送中
     */
    public static final Integer SEND_STATUS_SENDING = 0;
    /**
     * 发送成功
     */
    public static final Integer SEND_STATUS_SUCCESS = 1;
    /**
     * 发送失败
     */
    public static final Integer SEND_STATUS_FAULT = 2;


    /**
     * 邮件模板
     */
    public static final String EMAIL_SENDER = "1367213168@qq.com";
    public static final String FRIEND_LINK_UP_EMAIL_TITLE = "来自MFAC的友链上架确认通知邮件";
    public static final String FRIEND_LINK_UP_EMAIL_TEXT = "好，您的网站，已被MFAC博客(mfac.love)收录为友链，以下是您网站的上架信息,请您确认，如有任何问题，请直接回复该邮箱即可!\n";
    public static final String FRIEND_LINK_DOWN_EMAIL_TITLE = "来自MFAC的友链下架确认通知邮件";

    public static final String FRIEND_LINK_DOWN_EMAIL_TEXT = "很抱歉通知您，您的网站已从MFAC(mfac.love)中下架，以下是您网站的下架信息，请您确认，如有任何问题，请直接回复该邮箱即可!\n";

}
