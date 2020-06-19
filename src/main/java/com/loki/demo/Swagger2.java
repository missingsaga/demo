package com.loki.demo;

import com.loki.demo.dto.UserDTO;
import com.loki.demo.properties.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置Swagger2
 *
 * @author Loki
 */
@Profile({"dev", "test"}) // 在生产环境不开启
@Configuration
@EnableSwagger2
@ConditionalOnProperty({"swagger.host"})
@RequiredArgsConstructor
public class Swagger2 {

    private final SwaggerProperties swagger;

    /**
     * 创建rest接口
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName(swagger.getGroupName()).apiInfo(apiInfo())
                .host(swagger.getHost()).globalOperationParameters(getGlobalOperationParameters()).select()
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                .paths(PathSelectors.any()).build().ignoredParameterTypes(ModelMap.class, Pageable.class, UserDTO.class);
    }

    /**
     * 基本信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swagger.getTitle()).description(swagger.getDesc())
                .termsOfServiceUrl(swagger.getServiceUrl()).version(swagger.getVersion() + LocalDateTime.now()).build();
    }

    /**
     * 请求认证参数
     *
     * @return
     */
    private List<Parameter> getGlobalOperationParameters() {
        List<Parameter> list = new ArrayList<Parameter>();
        Parameter auth = new ParameterBuilder().name("Authorization").description("Authorization")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("Bearer ").required(true).build();
        Parameter csrf = new ParameterBuilder().name("X-CSRF-TOKEN").description("X-CSRF-TOKEN")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("").required(false).build();
        list.add(auth);
        list.add(csrf);
        return list;
    }

}
