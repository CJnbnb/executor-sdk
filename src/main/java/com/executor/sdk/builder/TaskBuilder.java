package com.executor.sdk.builder;

import com.executor.sdk.enums.ScheduledType;
import com.executor.sdk.exception.ExecutorSdkException;
import com.executor.sdk.model.TaskRequest;

import java.util.function.Consumer;

public class TaskBuilder {

    private final TaskRequest request;
    private final Consumer<TaskRequest> sender;

    public TaskBuilder(String taskName, Consumer<TaskRequest> sender) {
        this.request = new TaskRequest();
        this.request.setTaskName(taskName);
        this.request.setEnable(true);
        this.sender = sender;
    }

    /** 设置业务名和业务分组（必填） */
    public TaskBuilder biz(String bizName, String bizGroup) {
        request.setBizName(bizName);
        request.setBizGroup(bizGroup);
        return this;
    }

    /** 设置为 Cron 定时任务 */
    public TaskBuilder cron(String cronExpression) {
        request.setScheduledConf(cronExpression);
        request.setScheduledType(ScheduledType.CRON);
        return this;
    }

    /** 设置为一次性任务 */
    public TaskBuilder once(long executeTimeMs) {
        request.setScheduledConf(null);
        request.setScheduledType(ScheduledType.ONCE);
        request.setExecuteTime(executeTimeMs);
        return this;
    }

    /** 任务负载（JSON 字符串） */
    public TaskBuilder payload(String payload) {
        request.setPayload(payload);
        return this;
    }

    /** 是否启用，默认 true */
    public TaskBuilder enable(boolean enable) {
        request.setEnable(enable);
        return this;
    }

    /** 业务目标 Topic，默认 executorPool */
    public TaskBuilder topic(String topic) {
        request.setTopic(topic);
        return this;
    }

    /** 发送任务注册请求 */
    public boolean schedule() {
        validate();
        sender.accept(request);
        return true;
    }

    private void validate() {
        if (request.getBizName() == null || request.getBizName().isBlank()) {
            throw new ExecutorSdkException("bizName 不能为空，请调用 .biz(name, group)");
        }
        if (request.getBizGroup() == null || request.getBizGroup().isBlank()) {
            throw new ExecutorSdkException("bizGroup 不能为空，请调用 .biz(name, group)");
        }
        if (request.getScheduledType() == null) {
            throw new ExecutorSdkException("必须设置调度类型：.cron(expr) 或 .once(timeMs)");
        }
        if (ScheduledType.ONCE.equals(request.getScheduledType()) && request.getExecuteTime() == null) {
            throw new ExecutorSdkException("一次性任务必须设置 executeTime");
        }
    }
}
