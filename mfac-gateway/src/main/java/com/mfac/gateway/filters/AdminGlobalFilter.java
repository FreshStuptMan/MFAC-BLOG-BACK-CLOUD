package com.mfac.gateway.filters;

import com.mfac.gateway.constant.PathConstant;
import com.mfac.gateway.properties.JWTProperties;
import com.mfac.gateway.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;


@Component
public class AdminGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private JWTProperties jwtProperties;

    /**
     * 只负责校验TOKEN
     * 如果解析成功则放行
     * 解析失败则返回401错误码
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求Request
        ServerHttpRequest request = exchange.getRequest();
        // 是否为敏感路径
        if (!isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        //从请求头中获取令牌
        String token = null;
        List<String> headers = request.getHeaders().get(jwtProperties.getTokenName());
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }
        //校验令牌
        Long userId = null;
        try {
            Claims claims = JWTUtil.parseJWT(jwtProperties.getSecretKey(), token);
            userId = Long.valueOf(claims.get(jwtProperties.getUserId()).toString());
        } catch (Exception ex) {
            // 解析出错说明未通过校验，直接返回未登录的响应
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 校验通过，将解析出来的用户消息放入请求头中
        String userInfo = userId.toString();
        ServerWebExchange newExchange = exchange.mutate().request(builder -> {
            builder.header("user-info", userInfo);
        }).build();
        // 放行
        return chain.filter(newExchange);
    }

    /**
     * 设置过滤优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 判断请求路径是否为敏感路径
     * @param path
     * @return
     */
    private boolean isExclude(String path) {
        if (antPathMatcher.match(PathConstant.ADMIN_PATH_PATTERN, path)) {
            return true;
        }
        return false;
    }
}
