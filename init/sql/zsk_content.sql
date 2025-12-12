DROP TABLE IF EXISTS `content_note`;
CREATE TABLE `content_note`
(
    `id`             bigint(11)                                  NOT NULL COMMENT '主键',
    `user_id`        bigint(11)                                  NOT NULL COMMENT '用户ID',
    `tenant_id`      varchar(32) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '租户id',
    `note_name`      varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '笔记名称',
    `note_tags`      varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '笔记标签（多个标签用英文逗号分隔，如：Java,MySQL,优化）',
    `description`    varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '笔记简介/描述',
    `broad_code`     varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '大类（如：技术、生活、职场）',
    `narrow_code`    varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '小类（如：技术-Java、生活-美食）',
    `note_grade`     tinyint UNSIGNED DEFAULT NULL COMMENT '笔记等级（1-入门 2-进阶 3-高级 4-专家）',
    `note_mode`      tinyint UNSIGNED DEFAULT NULL COMMENT '笔记模式（1-公开 2-仅自己可见 3-指定租户可见 4-付费可见）',
    `suitable_users` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '适合人群（多个人群用英文逗号分隔，如：初学者,开发工程师,架构师）',

    `audit_status`   tinyint UNSIGNED NOT NULL COMMENT '审核状态（0-待审核 1-审核通过 2-审核驳回 3-已撤回）',
    `status`         tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '笔记状态（1-正常 2-下架 3-草稿 4-过期）',
    `publish_time`   datetime                                DEFAULT NULL COMMENT '笔记发布时间（审核通过后生效）',
    `view_count`     bigint UNSIGNED DEFAULT 0 COMMENT '笔记浏览量',
    `like_count`     bigint UNSIGNED DEFAULT 0 COMMENT '笔记点赞量',
    `version`        bigint UNSIGNED DEFAULT 0 COMMENT '乐观锁版本号（防并发更新冲突）',
    `remark`         varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注（如审核驳回原因、特殊说明）',

    `create_by`      varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人',
    `create_name`    varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人名称',
    `create_time`    datetime                                NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人',
    `update_name`    varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新人名称',
    `update_time`    datetime                                NOT NULL COMMENT '修改时间',
    `deleted`        tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `idx_cn_broad_code` (`broad_code`) COMMENT '大类索引（高频分类查询）',
    INDEX            `idx_cn_narrow_code` (`narrow_code`) COMMENT '小类索引（细分分类查询）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='内容管理服务_笔记信息表';

DROP TABLE IF EXISTS `content_note_pic`;
CREATE TABLE `content_note_pic`
(
    `id`      bigint(11)                                  NOT NULL COMMENT '主键',
    `note_id` bigint(11)                                  NOT NULL COMMENT '笔记ID',
    `pic_url` varchar(1024) COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片URL',
    `sort`    tinyint UNSIGNED DEFAULT 1 COMMENT '排序（1-封面图）',

    `create_by`      varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人',
    `create_name`    varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人名称',
    `create_time`    datetime                                NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人',
    `update_name`    varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新人名称',
    `update_time`    datetime                                NOT NULL COMMENT '修改时间',
    `deleted`        tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`),
    INDEX     `idx_np_note_id` (`note_id`) COMMENT '笔记ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='内容管理服务_笔记多图关联表';

DROP TABLE IF EXISTS `content_note_comment`;
CREATE TABLE `content_note_comment`
(
    `id`                bigint(11)                                  NOT NULL COMMENT '主键',
    `note_id`           bigint (11)   NOT NULL COMMENT '关联笔记ID（关联content_note.id）',
    `comment_user_id`   varchar(64) COLLATE utf8mb4_general_ci   NOT NULL COMMENT '评论人ID',
    `comment_content`   varchar(1000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
    `parent_comment_id` bigint UNSIGNED DEFAULT NULL COMMENT '父评论ID（用于回复：NULL为根评论，非NULL为回复某条评论）',
    `audit_status`      tinyint UNSIGNED NOT NULL COMMENT '审核状态（0-待审核 1-审核通过 2-审核驳回）',
    `status`            tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '评论状态（1-正常 2-隐藏 3-删除）',
    `like_count`        bigint UNSIGNED DEFAULT 0 COMMENT '评论点赞数',
    `version`           bigint UNSIGNED DEFAULT 0 COMMENT '乐观锁版本号（防并发更新冲突）',
    -- 公共字段（与笔记表对齐，适配拦截器自动填充）
    `create_by`      varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人',
    `create_name`    varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人名称',
    `create_time`    datetime                                NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人',
    `update_name`    varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新人名称',
    `update_time`    datetime                                NOT NULL COMMENT '修改时间',
    `deleted`        tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    -- 索引优化（核心关联+业务查询）
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                 `idx_nc_note_id` (`note_id`) COMMENT '笔记ID索引（核心关联，查询某笔记的所有评论）',
    INDEX                 `idx_nc_parent_comment_id` (`parent_comment_id`) COMMENT '父评论ID索引（查询某条评论的回复）',
    INDEX                 `idx_nc_audit_status` (`audit_status`) COMMENT '审核状态索引（审核流程查询）',
    INDEX                 `idx_nc_status` (`status`) COMMENT '评论状态索引（筛选正常/隐藏评论）',
    INDEX                 `idx_nc_create_time` (`create_time`) COMMENT '评论时间索引（按时间排序/筛选）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='内容管理服务_笔记评论表';