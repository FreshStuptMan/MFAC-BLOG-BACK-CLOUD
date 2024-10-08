package com.mfac.blog.task;

import cn.hutool.core.collection.CollUtil;
import com.mfac.blog.constent.RedisConstant;
import com.mfac.blog.service.BlogService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
/**
 * 博客浏览量落库任务
 */
public class BlogViewScheduler {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private BlogService blogService;
    /**
     * 每日凌晨 3：30 将 redis中的 浏览量落库
     */
    @Scheduled(cron = "0 30 3 * * ?")
    public void SyncBlogViewTask() {
        boolean acquire = redisTemplate.opsForValue().setIfAbsent(RedisConstant.SCHEDULER_BlogViewScheduler_LOCK, "locking", Duration.ofSeconds(30));
        if (acquire) {
            try {
                String luaScript =
                        "local viewKeys = redis.call('keys', 'view:*')" + "\n" +
                                "local viewData = {}" + "\n" +
                                "for _, key in ipairs(viewKeys) do" + "\n" +
                                "local id = key:match(':([^:]+)')" + "\n" +
                                "local view = tonumber(redis.call('get', key))" + "\n" +
                                "if view then" + "\n" +
                                "viewData[id] = view" + "\n" +
                                "redis.call('del', key)" + "\n" +
                                "end" + "\n" +
                                "end" + "\n" +
                                "return cjson.encode(viewData)";
                // 执行lua脚本，获取redis中的数据
                Map<String, Integer> redisData = (Map<String, Integer>) redisTemplate.execute(
                        new DefaultRedisScript<>(luaScript,  Map.class),
                        Collections.emptyList(),
                        RedisConstant.BLOG_VIEW_PATTERN
                );
                // 转换格式
                Map<Long, Integer> dbData = new HashMap<>();
                if (redisData != null) {
                    for (Map.Entry<String, Integer> entry : redisData.entrySet()) {
                        Long id = Long.parseLong(entry.getKey());
                        dbData.put(id, entry.getValue());
                    }
                }
                // 落库
                if (CollUtil.isNotEmpty(dbData)) {
                    blogService.updateViewBatch(dbData);
                }
            }
            finally {
                redisTemplate.delete(RedisConstant.SCHEDULER_BlogViewScheduler_LOCK);
            }
        }
    }

    /**
     * 获取博客ID
     * @param viewKey
     * @return
     */
    private Long parseId(String viewKey) {
        return Long.parseLong(viewKey.split(":")[1]);
    }

 }
