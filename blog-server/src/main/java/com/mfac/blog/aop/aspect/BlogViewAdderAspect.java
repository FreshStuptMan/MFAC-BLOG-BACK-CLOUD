package com.mfac.blog.aop.aspect;

import cn.hutool.core.collection.CollUtil;
import com.mfac.blog.constent.RedisConstant;
import com.mfac.blog.pojo.PageResult;
import com.mfac.blog.pojo.Result;
import com.mfac.blog.pojo.vo.BlogListVO;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class BlogViewAdderAspect {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.mfac.blog.aop.annotation.BlogViewAdder)")
    public void BlogViewAdderAspect() {}

    /**
     * 在获取到博客列表后同步redis中的浏览量
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "BlogViewAdderAspect()", returning = "result")
    public void afterReturningAspect(JoinPoint joinPoint, Result result) {
        // 获取数据库浏览量
        PageResult page = (PageResult) result.getData();
        List<BlogListVO> list = page.getRecords();
        // 非空校验
        if (!CollUtil.isEmpty(list)) {
            // 拼接 view:id
            List<String> viewKeys = list.stream().
                    map(blog -> RedisConstant.BLOG_VIEW_PREFIX + ":" + blog.getId()).
                    collect(Collectors.toList());

            // 从redis中获取
            List<Integer> views = redisTemplate.opsForValue().multiGet(viewKeys);
            // 同步至响应结果
            for (int i=0;i<list.size();i++) {
                BlogListVO blog = list.get(i);
                Integer view = views.get(i);
                if (view != null) {
                    blog.setView(blog.getView() + view);
                }
            }
            // 重新设置
            page.setRecords(list);
            result.setData(page);
        }
    }
}
