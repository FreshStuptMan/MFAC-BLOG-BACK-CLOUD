package com.mfac.blog.aop.annotation;

import java.lang.annotation.*;

/**
 * 自定义博客浏览量拼接
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BlogViewAdder {
}
