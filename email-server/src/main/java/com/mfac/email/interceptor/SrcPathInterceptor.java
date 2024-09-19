package com.mfac.email.interceptor;

import com.mfac.email.constant.ServerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求源拦截器
 */
@Slf4j
@Component
public class SrcPathInterceptor implements HandlerInterceptor {
    @Resource
    private DiscoveryClient client;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断请求来源是否来自网关或其他服务
        try {
            String remoteAddress = request.getRemoteAddr();
            if (requestSrcCheck(remoteAddress)) {
                // 请求来自于网关或其他服务则放行
                return true;
            }
        } catch (Exception e) {
            // 请求路径解析报错直接返回403拒绝提供服务
            response.setStatus(403);
            return false;
        }
        // 请求不来自于网关或其他服务，返回403拒绝提供服务
        response.setStatus(403);
        return false;
    }

    /**
     * 判断请求来源是否为网关或其他服务
     * @param address
     * @return
     * @throws Exception
     */
    private boolean requestSrcCheck(String address) throws Exception {
        if (address == null) {
            return false;
        }
        if (address.equals("")) {
            return false;
        }
        List<ServiceInstance> blogInstances = client.getInstances(ServerConstant.BLOG_SERVER);
        List<ServiceInstance> toolInstances = client.getInstances(ServerConstant.TOOL_SERVER);
        List<ServiceInstance> userInstances = client.getInstances(ServerConstant.USER_SERVER);
        List<ServiceInstance> gatewayInstances = client.getInstances(ServerConstant.GATEWAY_SERVER);
        List<ServiceInstance> friendLinkInstances = client.getInstances(ServerConstant.FRIEND_LINK_SERVER);
        List<ServiceInstance> emailInstances = client.getInstances(ServerConstant.EMAIL_SERVER);
        for (ServiceInstance instance  : gatewayInstances) {
            if (address.equals(instance.getHost())) {
                return true;
            }
        }
        for (ServiceInstance instance  : blogInstances) {
            if (address.equals(instance.getHost())) {
                return true;
            }
        }
        for (ServiceInstance instance  : toolInstances) {
            if (address.equals(instance.getHost())) {
                return true;
            }
        }
        for (ServiceInstance instance  : userInstances) {
            if (address.equals(instance.getHost())) {
                return true;
            }
        }
        for (ServiceInstance instance  : friendLinkInstances) {
            if (address.equals(instance.getHost())) {
                return true;
            }
        }
        for (ServiceInstance instance  : emailInstances) {
            if (address.equals(instance.getHost())) {
                return true;
            }
        }
        return false;
    }
}
