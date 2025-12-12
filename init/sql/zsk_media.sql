DROP TABLE IF EXISTS `media_files`;
CREATE TABLE `media_files`
(
    `id`           bigint(11) NOT NULL COMMENT '参数主键',
    `tenant_id`    varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
    `file_type`    varchar(12) COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '文件类型（图片、文档，视频）',
    `tags`         varchar(120) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '标签',
    `bucket`       varchar(128) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '存储目录',
    `file_id`      varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件id',
    `file_name`   varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
    `file_path`    varchar(512) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '存储路径',
    `url`          varchar(1024) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '媒资文件访问地址',
    `user_name`    varchar(60) COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '上传人',
    `create_date`  datetime                                 DEFAULT NULL COMMENT '上传时间',
    `change_date`  datetime                                 DEFAULT NULL COMMENT '修改时间',
    `status`       varchar(12) COLLATE utf8mb4_general_ci   DEFAULT '1' COMMENT '状态,1:正常，0:不展示',
    `remark`       varchar(32) COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '备注',
    `audit_status` varchar(12) COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '审核状态',
    `audit_mind`   varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '审核意见',
    `file_size`    bigint                                   DEFAULT NULL COMMENT '文件大小',

    `create_by`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`  varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`  datetime                               NOT NULL COMMENT '创建时间',
    `update_by`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`  varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`  datetime                               NOT NULL COMMENT '修改时间',
    `deleted`      tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_mf_file_id` (`file_id`) USING BTREE COMMENT '文件id唯一索引 '
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='媒资服务_文件表';


DROP TABLE IF EXISTS `media_process`;
CREATE TABLE `media_process`
(
    `id`          bigint(11) NOT NULL COMMENT '参数主键',
    `tenant_id`   varchar(32) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '租户id',
    `file_id`     varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件标识',
    `file_name`   varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
    `bucket`      varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储桶',
    `file_path`   varchar(512) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '存储路径',
    `status`      varchar(12) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
    `create_date` datetime                                NOT NULL COMMENT '上传时间',
    `finish_date` datetime                                 DEFAULT NULL COMMENT '完成时间',
    `fail_count`  int                                      DEFAULT '0' COMMENT '失败次数',
    `url`         varchar(1024) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '媒资文件访问地址',
    `error_msg`   varchar(1024) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '失败原因',

    `create_by`   varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人',
    `create_name` varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人名称',
    `create_time` datetime                                NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人',
    `update_name` varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新人名称',
    `update_time` datetime                                NOT NULL COMMENT '修改时间',
    `deleted`     tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_mp_file_id` (`file_id`) USING BTREE COMMENT '文件id唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='媒资服务_文件类型转换表';


DROP TABLE IF EXISTS `media_process_history`;
CREATE TABLE `media_process_history`
(
    `id`          bigint(11) NOT NULL COMMENT '参数主键',
    `tenant_id`   varchar(32) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '租户id',
    `file_id`     varchar(120) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '文件标识',
    `file_name`   varchar(255) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '文件名称',
    `bucket`      varchar(128) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '存储源',
    `status`      varchar(12) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
    `create_date` datetime                                 NOT NULL COMMENT '上传时间',
    `finish_date` datetime                                 NOT NULL COMMENT '完成时间',
    `url`         varchar(1024) COLLATE utf8mb4_general_ci NOT NULL COMMENT '媒资文件访问地址',
    `fail_count`  int                                      DEFAULT '0' COMMENT '失败次数',
    `file_path`   varchar(512) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '文件路径',
    `error_msg`   varchar(1024) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '失败原因',

    `create_by`   varchar(64) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '创建人',
    `create_name` varchar(64) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '创建人名称',
    `create_time` datetime                                 NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '修改人',
    `update_name` varchar(64) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '更新人名称',
    `update_time` datetime                                 NOT NULL COMMENT '修改时间',
    `deleted`     tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_mph_file_id` (`file_id`) USING BTREE COMMENT '文件id唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='媒资服务_文件处理历史表';