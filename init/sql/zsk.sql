CREATE TABLE `media_process_history`
(
    `id`          bigint(16) NOT NULL COMMENT '主键',
    `file_id`     varchar(120)  NOT NULL COMMENT '文件标识',
    `file_name`   varchar(255)  NOT NULL COMMENT '文件名称',
    `bucket`      varchar(128)  NOT NULL COMMENT '存储源',
    `status`      varchar(12)   NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
    `create_date` datetime      NOT NULL COMMENT '上传时间',
    `finish_date` datetime      NOT NULL COMMENT '完成时间',
    `url`         varchar(1024) NOT NULL COMMENT '媒资文件访问地址',
    `fail_count`  int           DEFAULT 0 COMMENT '失败次数',
    `file_path`   varchar(512)  DEFAULT NULL COMMENT '文件路径',
    `error_msg`   varchar(1024) DEFAULT NULL COMMENT '失败原因',
    `create_by`   varchar(64)   NOT NULL COMMENT '创建人',
    `create_name` varchar(64)   NOT NULL COMMENT '创建人名称',
    `create_time` datetime      NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64)   NOT NULL COMMENT '修改人',
    `update_name` varchar(64)   NOT NULL COMMENT '更新人名称',
    `update_time` datetime      NOT NULL COMMENT '修改时间',
    `deleted`     tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_media_process_id` (`file_id`) USING BTREE COMMENT '文件id唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'minio上传历史处理信息' ROW_FORMAT = DYNAMIC;
