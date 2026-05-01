package com.executor.sdk;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xxl.job.process")
public class ExecutorSdkProperties {

    /** RocketMQ NameServer 地址（必填） */
    private String nameserver;

    /** 任务注册 Topic，默认 executorConsumeTask */
    private String topic = "executorConsumeTask";

    /** Producer Group，默认 executorProduceGroup */
    private String group = "executorProduceGroup";

    /** RocketMQ ACL AccessKey（可选） */
    private String accessKey;

    /** RocketMQ ACL SecretKey（可选） */
    private String secretKey;

    public String getNameserver() { return nameserver; }
    public void setNameserver(String nameserver) { this.nameserver = nameserver; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
}
