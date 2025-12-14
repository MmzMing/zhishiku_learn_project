# 知识库-微服务脚手架

## 一、服务模块

| 模块                       | 端口号 | 说明               |
| -------------------------- | ------ | ------------------ |
| zhishiku-gateway           | 30000  | 网关服务           |
| zhishiku-auth              | 30010  | 认证服务           |
| zhishiku-service-monitor   | 30020  | 监控服务           |
| zhishiku-service-system    | 30030  | 系统服务           |
| zhishiku-service-content   | 40010  | 知识库内容管理服务 |
| zhishiku-service-media     | 40020  | 媒体文件服务       |
| zhishiku-service-ai-chat   | 40030  | AI聊天室           |

## 二、技术架构

### 2.1 核心框架

| 技术           | 说明                 |
| -------------- | -------------------- |
| Spring Boot    | 基础框架             |
| Spring Cloud   | 微服务框架           |
| Nacos          | 注册中心 & 配置中心  |
| Sentinel       | 流量控制 & 熔断降级  |
| Gateway        | 微服务网关           |

### 2.2 数据存储

| 技术        | 说明             |
| ----------- | ---------------- |
| MySQL       | 关系型数据库     |
| MyBatis Plus| 持久层框架       |
| Redis       | 缓存 & 会话共享  |
| MinIO       | 分布式文件存储   |

### 2.3 其他组件

| 技术      | 说明             |
| --------- | ---------------- |
| XXL-JOB   | 分布式任务调度   |
| Swagger   | API文档生成      |
| Lombok    | 代码简化工具     |
| Hutool    | Java工具类库     |

## 三、项目结构

```
zhishiku_learn_project
├── init/                           # 初始化配置
│   ├── nacos/dev/                  # Nacos开发环境配置
│   └── sql/                        # 数据库初始化脚本
├── zhishiku-api/                   # API接口模块（Feign客户端）
├── zhishiku-common/                # 公共模块
│   ├── zhishiku-common-core        # 核心工具类、异常、常量
│   ├── zhishiku-common-log         # 日志模块
│   ├── zhishiku-common-mybatis     # MyBatis Plus封装
│   ├── zhishiku-common-redis       # Redis操作封装
│   ├── zhishiku-common-security    # 安全模块
│   ├── zhishiku-common-swagger     # 接口文档集成
│   └── zhishiku-common-xxl-job     # 任务调度注册
├── zhishiku-modules/               # 业务模块
│   ├── zhishiku-service-auth       # 认证服务
│   ├── zhishiku-service-content    # 内容管理服务
│   ├── zhishiku-service-gateway    # 网关服务
│   ├── zhishiku-service-media      # 媒体文件服务
│   └── zhishiku-service-system     # 系统服务
├── zhishiku-parent/                # 父POM（依赖版本管理）
└── docker-compose.env.yml          # Docker环境配置
```

## 四、基础设施

| 服务            | 版本                           | 端口        | 说明           |
| --------------- | ------------------------------ | ----------- | -------------- |
| MySQL           | 8.0.26                         | 3306        | 关系型数据库   |
| Redis           | 6.2.7                          | 6379        | 缓存服务       |
| Nacos           | 2.x                            | 8848        | 注册/配置中心  |
| MinIO           | RELEASE.2022-09-07T22-25-02Z   | 9000, 9001  | 对象存储       |
| XXL-JOB-Admin   | 2.3.1                          | 8088        | 任务调度控制台 |
| Elasticsearch   | 8.6.0                          | 9200        | 搜索引擎       |
| Kibana          | 8.6.0                          | 5601        | ES可视化工具  |

## 五、环境配置

### 5.1 开发环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.x
- MinIO

### 5.2 配置说明

项目采用多环境配置方式：
- `bootstrap.yml` - 基础配置（Nacos连接信息）
- `bootstrap-dev.yml` - 开发环境配置
- `bootstrap-prod.yml` - 生产环境配置

## 六、快速启动
