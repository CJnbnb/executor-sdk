package com.executor.sdk.model;

/**
 * 任务注册请求，字段与 Executor 端 ProcessCommonTaskDTO 契约对齐。
 */
public class TaskRequest {

    /** 任务名称 */
    private String taskName;

    /** 业务名称 */
    private String bizName;

    /** 业务分组 */
    private String bizGroup;

    /** Cron 表达式，为 null 时表示一次性任务 */
    private String scheduledConf;

    /** 调度类型：1-CRON, 2-ONCE */
    private String scheduledType;

    /** 一次性任务的执行时间戳（毫秒），仅 scheduledType=ONCE 时有效 */
    private Long executeTime;

    /** 是否启用，默认 true */
    private Boolean enable;

    /** 任务负载（JSON 字符串），透传给业务消费者 */
    private String payload;

    /** 业务目标 Topic，默认 "executorPool" */
    private String topic;

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getBizName() { return bizName; }
    public void setBizName(String bizName) { this.bizName = bizName; }

    public String getBizGroup() { return bizGroup; }
    public void setBizGroup(String bizGroup) { this.bizGroup = bizGroup; }

    public String getScheduledConf() { return scheduledConf; }
    public void setScheduledConf(String scheduledConf) { this.scheduledConf = scheduledConf; }

    public String getScheduledType() { return scheduledType; }
    public void setScheduledType(String scheduledType) { this.scheduledType = scheduledType; }

    public Long getExecuteTime() { return executeTime; }
    public void setExecuteTime(Long executeTime) { this.executeTime = executeTime; }

    public Boolean getEnable() { return enable; }
    public void setEnable(Boolean enable) { this.enable = enable; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
