package com.mfac.user.interceptor;



import com.mfac.user.util.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 网关已处理，直接获取请求头携带的用户信息
            String userInfo = request.getHeader("user-info");
            Long userId = Long.valueOf(userInfo);
            ThreadLocalUtil.setCurrentId(userId);
            log.info("user-id: {}", userInfo);
        } catch (Exception e) {
            // 报错说明解析异常直接拒绝
            response.setStatus(403);
            return false;
        }
        return true;
    }
}
