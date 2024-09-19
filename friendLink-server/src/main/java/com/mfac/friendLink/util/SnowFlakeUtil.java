package com.mfac.friendLink.util;

import cn.hutool.core.util.IdUtil;

/**
 * 雪花ID生成器
 */
public class SnowFlakeUtil {
    /**
     * 生成随机且唯一的雪花ID
     * @return
     */
    public static Long create() {
        return IdUtil.getSnowflakeNextId();
    }
}
