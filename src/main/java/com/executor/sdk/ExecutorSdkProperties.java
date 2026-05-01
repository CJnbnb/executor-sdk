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

    public String getNameserver() { return nameserver; }
    public void setNameserver(String nameserver) { this.nameserver = nameserver; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }
}
