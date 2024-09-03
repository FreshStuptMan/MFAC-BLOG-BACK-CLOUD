package com.mfac.gateway.listeners;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONParser;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.mfac.gateway.constant.RouterConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class RouterListener {
    @Resource
    private RouteDefinitionWriter writer;
    @Resource
    private NacosConfigManager manager;
    // 记录路由编号
    private Set<String> routerIds =  new HashSet<>();
    /**
     * 初始化路由
     */
    @PostConstruct
    public void initGatewayRouter() throws NacosException {
        // 第一次建立连接时需要拉取配置信息
        String configInfo = manager.getConfigService()
                .getConfigAndSignListener(RouterConstant.ROUTER_CONFIG_DATA_ID, RouterConstant.ROUTER_CONFIG_GROUP, 5000, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }
            // 监听
            @Override
            public void receiveConfigInfo(String configInfo) {
                // 监听到变化后更新路由表
                updateRouter(configInfo);
            }
        });
        // 第一次建立连接时需要拉取配置信息后初始话路由
        updateRouter(configInfo);
    }

    /**
     * 更新路由配置
     * @param configInfo
     */
    private void updateRouter(String configInfo) {
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
        //清空之前的路由表
        for (String routerId : routerIds) {
            writer.delete(Mono.just(routerId)).subscribe();
        }
        routerIds.clear();
        // 判断是否有新的路由要更新
        if (CollectionUtils.isEmpty(routeDefinitions)) {
            // 无新路由配置，直接结束
            return;
        }
        // 写入最新的路由表
        routeDefinitions.forEach(router -> {
            routerIds.add(router.getId());
            writer.save(Mono.just(router)).subscribe();
        });
    }
}
