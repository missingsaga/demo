package com.loki.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger配置参数
 * 
 * @author Loki
 *
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {

    private String host;

    private String groupName;

    private String basePackage;

    private String title;

    private String desc;

    private String serviceUrl;

    private String version;

}
