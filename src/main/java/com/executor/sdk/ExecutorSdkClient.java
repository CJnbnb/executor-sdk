package com.executor.sdk;

import com.alibaba.fastjson.JSONObject;
import com.executor.sdk.builder.TaskBuilder;
import com.executor.sdk.exception.ExecutorSdkException;
import com.executor.sdk.model.TaskRequest;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Executor SDK 核心客户端，业务方直接注入使用。
 */
public class ExecutorSdkClient {

    private static final Logger log = LoggerFactory.getLogger(ExecutorSdkClient.class);

    private final ExecutorSdkProperties properties;
    private DefaultMQProducer producer;

    public ExecutorSdkClient(ExecutorSdkProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() throws MQClientException {
        String ak = properties.getAccessKey();
        String sk = properties.getSecretKey();
        if (ak != null && !ak.isBlank() && sk != null && !sk.isBlank()) {
            AclClientRPCHook aclHook = new AclClientRPCHook(new SessionCredentials(ak, sk));
            producer = new DefaultMQProducer(properties.getGroup(), aclHook);
            log.info("ExecutorSdkClient started with ACL, nameserver={}, group={}, accessKey={}",
                    properties.getNameserver(), properties.getGroup(), ak);
        } else {
            producer = new DefaultMQProducer(properties.getGroup());
            log.info("ExecutorSdkClient started (no ACL), nameserver={}, group={}",
                    properties.getNameserver(), properties.getGroup());
        }
        producer.setNamesrvAddr(properties.getNameserver());
        producer.start();
    }

    @PreDestroy
    public void shutdown() {
        if (producer != null) {
            producer.shutdown();
            log.info("ExecutorSdkClient shutdown");
        }
    }

    /** 开始构建一个任务，返回 Builder */
    public TaskBuilder newTask(String taskName) {
        return new TaskBuilder(taskName, this::doSend);
    }

    /** 直接发送 TaskRequest */
    public boolean send(TaskRequest request) {
        doSend(request);
        return true;
    }

    private void doSend(TaskRequest request) {
        String topic = properties.getTopic();
        String tag = request.getTaskName();
        String body = JSONObject.toJSONString(request);
        Message msg = new Message(topic, tag, body.getBytes());

        try {
            SendResult result = producer.send(msg);
            log.info("Task registered: task={}, biz={}.{}, msgId={}",
                    request.getTaskName(), request.getBizName(), request.getBizGroup(), result.getMsgId());
        } catch (Exception e) {
            log.error("Failed to send task: task={}, error={}", request.getTaskName(), e.getMessage());
            throw new ExecutorSdkException("任务注册失败: " + request.getTaskName(), e);
        }
    }
}
