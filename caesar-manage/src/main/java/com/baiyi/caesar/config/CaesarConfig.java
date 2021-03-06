package com.baiyi.caesar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author baiyi
 * @Date 2020/5/26 6:08 下午
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "caesar", ignoreInvalidFields = true)
public class CaesarConfig {

    private String version;
    private Boolean openTask; // 启用任务
    private String url;

}
