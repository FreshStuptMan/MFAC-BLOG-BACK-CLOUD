package com.mfac.user.config;

import com.mfac.user.interceptor.AdminInterceptor;
import com.mfac.user.interceptor.SrcPathInterceptor;
import com.mfac.user.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {


    @Resource
    private AdminInterceptor adminInterceptor;
    @Resource
    private SrcPathInterceptor srcPathInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .order(1);
        registry.addInterceptor(srcPathInterceptor)
                .addPathPatterns("/login")
                .addPathPatterns("/admin/**")
                .order(0);
    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
    public Docket docket() {
        Contact contact = new Contact(
                "FreshStuptMan", "", ""
        );
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("MFAC用户接口文档")
                .version("1.0")
                .contact(contact)
                .description("MFAC用户接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mfac.user.controller.admin"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 扩展 Spring MVC 消息转换器 统一消息格式
     * @param converters
     */
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 为该消息转换器设置一个对象转换器(将java对象按指定格式序列化为json数据)
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自定义消息转换器放入容器中，并设置在最前方，最先执行
        converters.add(0,converter);
    }
}
