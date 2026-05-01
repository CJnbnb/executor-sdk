# executor-sdk

业务方轻量级任务注册客户端，基于 RocketMQ 实现任务下发，通过 Spring Boot Starter 方式集成。

## 快速开始

### Maven

```xml
<dependency>
    <groupId>com.executor</groupId>
    <artifactId>executor-sdk</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 配置

```yaml
xxl:
  job:
    process:
      nameserver: 127.0.0.1:9876                  # RocketMQ NameServer 地址（必填）
      topic: executorConsumeTask                   # 任务注册 Topic，默认 executorConsumeTask
      group: executorProduceGroup                  # Producer Group，默认 executorProduceGroup
      access-key: ${ROCKETMQ_AK:}                  # ACL AccessKey（可选）
      secret-key: ${ROCKETMQ_SK:}                  # ACL SecretKey（可选）
```

### 使用

```java
@RestController
public class TaskController {

    @Autowired
    private ExecutorSdkClient client;

    /** 注册 Cron 定时任务 */
    @PostMapping("/task/cron")
    public String cronTask() {
        client.newTask("reportJob")
                .biz("report", "daily")
                .cron("0 0 8 * * ?")
                .payload("{\"type\":\"daily_report\"}")
                .schedule();
        return "ok";
    }

    /** 注册一次性任务 */
    @PostMapping("/task/once")
    public String onceTask() {
        long executeAt = System.currentTimeMillis() + 3600_000; // 1 小时后
        client.newTask("cleanJob")
                .biz("maintenance", "clean")
                .once(executeAt)
                .topic("executorPool")
                .schedule();
        return "ok";
    }
}
```

## API

### 配置属性

| 属性 | 说明 | 默认值 |
|------|------|--------|
| `xxl.job.process.nameserver` | RocketMQ NameServer 地址 | 无（必填） |
| `xxl.job.process.topic` | 任务消息 Topic | `executorConsumeTask` |
| `xxl.job.process.group` | Producer Group | `executorProduceGroup` |
| `xxl.job.process.access-key` | ACL AccessKey | 无 |
| `xxl.job.process.secret-key` | ACL SecretKey | 无 |

### TaskBuilder

| 方法 | 说明 |
|------|------|
| `.biz(bizName, bizGroup)` | 设置业务名和业务分组（必填） |
| `.cron(cronExpression)` | 设置为 Cron 定时任务 |
| `.once(executeTimeMs)` | 设置为一次性任务 |
| `.payload(json)` | 任务负载（JSON 字符串），透传给消费者 |
| `.enable(bool)` | 是否启用，默认 `true` |
| `.topic(topic)` | 业务目标 Topic，默认 `executorPool` |
| `.schedule()` | 参数校验并发送注册请求 |

### 枚举

| 常量 | 值 | 说明 |
|------|-----|------|
| `ScheduledType.CRON` | `"1"` | Cron 定时任务 |
| `ScheduledType.ONCE` | `"2"` | 一次性任务 |

## 技术栈

- Java 21
- Spring Boot 3.5.0
- RocketMQ Spring Boot Starter 2.2.3
- Lombok
