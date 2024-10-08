package com.mfac.blog.aop.aspect;

import com.mfac.blog.constent.RedisConstant;
import com.mfac.blog.pojo.Result;
import com.mfac.blog.pojo.vo.BlogDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class BlogViewUpdaterAspect {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.mfac.blog.aop.annotation.BlogViewUpdater)")
    public void BlogViewUpdaterAspectPointcut() {}

    /**
     * 成功获取到博客后更新缓存并将缓存中的浏览量同步到返回结果中
     * @param joinPoint
     * @param result
     */
    @AfterReturning(pointcut = "BlogViewUpdaterAspectPointcut()", returning = "result")
    public void afterReturningAdvice (JoinPoint joinPoint, Result result) {
        Boolean readFlag = true; // 是否浏览过
        // 获取请求ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr().replace(':', '.');
        // 获取博客id
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        // 使用 LUA脚本 保证事务的原子性
        String ipKey = ipKeyAppend(ip, id);
        String viewKey = viewKeyAppend(id);
        String luaScript =
                "local ipKey = KEYS[1]" + "\n" +
                "local viewKey = KEYS[2]" + "\n" +
                "if redis.call('exists', ipKey) == 0 then" + "\n" + // 判断该 user:ip:id 是否存在
                "redis.call('set', ipKey, 1, 'EX', 86400)" + "\n" + // 不存在则创建该条记录并设置24H过期时间
                "if redis.call('exists', viewKey) == 0 then" + "\n" + // 判断 view:id 是否存在
                "redis.call('set', viewKey, 0)" + "\n" + // 不存在则创建 view:id 并初始化为0
                "end" + "\n" +
                "return tonumber(redis.call('incr', viewKey))" + "\n" + // 增加并返回该条浏览量
                "else" + "\n" + // user:ip:id 存在
                "if redis.call('exists', viewKey) == 0 then" + "\n" + // 判断 viewKey 是否存在
                "redis.call('set', viewKey, 0)" + "\n" + // 不存在则创建 view:id 并初始化为0和返回
                "return 0" + "\n" +
                "else" + "\n" +
                "return tonumber(redis.call('get', viewKey))" + "\n" +// view:id 存在则返回当前值
                "end" + "\n" +
                "end";
        List<String> keys = Arrays.asList(ipKey, viewKey);
        // 执行脚本并同步至返回数据中
        Long views = (Long) redisTemplate.execute(new DefaultRedisScript<>(luaScript, Long.class), keys);
        BlogDetailVO detail = (BlogDetailVO) result.getData();
        detail.setView(detail.getView() + Integer.parseInt(views.toString()));
    }


    /**
     * 拼接 user:ip:id
     * @param ip
     * @param id
     * @return
     */
    private String ipKeyAppend(String ip, Long id) {
        return RedisConstant.USER_IP_PREFIX + ":" + ip + ":" + id;
    }

    /**
     * 拼接 view:id
     * @param id
     * @return
     */
    private String viewKeyAppend(Long id) {
        return RedisConstant.BLOG_VIEW_PREFIX + ":" + id;
    }
}
