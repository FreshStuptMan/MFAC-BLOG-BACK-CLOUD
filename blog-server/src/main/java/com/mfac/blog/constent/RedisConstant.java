package com.mfac.blog.constent;

public class RedisConstant {
    // 用户ip去重键前缀
    public final static String USER_IP_PREFIX = "user";
    // 博客浏览量键前缀
    public final static String BLOG_VIEW_PREFIX = "view";
    // 博客浏览量键格式
    public final static String BLOG_VIEW_PATTERN = "view:*";
    // 博客浏览量落库任务
    public final static String SCHEDULER_BlogViewScheduler_LOCK = "lock:scheduler:BlogViewScheduler";
}
