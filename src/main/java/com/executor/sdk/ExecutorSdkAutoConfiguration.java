package com.executor.sdk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ExecutorSdkProperties.class)
@ConditionalOnProperty(prefix = "xxl.job.process", name = "nameserver")
public class ExecutorSdkAutoConfiguration {

    @Bean
    public ExecutorSdkClient executorSdkClient(ExecutorSdkProperties properties) {
        return new ExecutorSdkClient(properties);
    }
}
