DROP TABLE IF EXISTS `sys_login_infor`;
CREATE TABLE `sys_login_infor`
(
    `id`          bigint(11) NOT NULL COMMENT '参数主键',
    `user_name`   varchar(50) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
    `ip_addr`     varchar(128) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
    `status`      char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
    `msg`         varchar(255) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示信息',
    `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统服务_系统访问记录';

DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`
(
    `id`             bigint(11) NOT NULL COMMENT '参数主键',
    `title`          varchar(50) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
    `business_type`  varchar(50) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method`         varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
    `operator_type`  int(11) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name`      varchar(50) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
    `oper_url`       varchar(255) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
    `oper_ip`        varchar(128) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
    `oper_location`  varchar(255) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
    `oper_param`     varchar(2000) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
    `json_result`    varchar(2000) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
    `status`         int(11) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    `error_msg`      varchar(2000) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
    `oper_time`      datetime NULL DEFAULT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统服务_操作日志记录';

DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`             bigint(11) NOT NULL COMMENT '参数主键',
    `notice_title`   varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
    `notice_type`    char(1) COLLATE utf8mb4_general_ci     NOT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_content` longblob NULL COMMENT '公告内容',
    `status`         char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    `remark`         varchar(255) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',

    `create_by`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`    datetime                               NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`    datetime                               NOT NULL COMMENT '修改时间',
    `deleted`        tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统服务_通知公告表';

DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`
(
    `id`          bigint(11) NOT NULL COMMENT '参数主键',
    `dict_name`   varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
    `dict_type`   varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `status`      char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',

    `create_by`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time` datetime                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time` datetime                               NOT NULL COMMENT '修改时间',
    `deleted`     tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_dt_dict_type`(`dict_type`) USING BTREE COMMENT '字典类型唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统服务_字典类型表';

DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    `id`          bigint(11) NOT NULL COMMENT '参数主键',
    `dict_code`   varchar(20)                            NOT NULL COMMENT '字典编码',
    `dict_sort`   int(11) NULL DEFAULT 0 COMMENT '字典排序',
    `dict_label`  varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `css_class`   varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`  varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
    `is_default`     char(1) COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '默认（Y是 N否）',
    `status`      char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',

    `create_by`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time` datetime                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time` datetime                               NOT NULL COMMENT '修改时间',
    `deleted`     tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_dd_dict_code` (`dict_code`) USING BTREE COMMENT '字典编码唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统服务_字典数据表';



DROP TABLE IF EXISTS `sys_tenant_data`;
CREATE TABLE `sys_tenant_data`
(
    `id`               bigint(11)                            NOT NULL COMMENT '租户id',
    `tenant_id`        varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
    `tenant_code`      varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户编码',
    `tenant_name`      varchar(100) COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '租户名字',

    `tenant_type`      varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'common' COMMENT '租户类型(common-普通租户 admin-管理员租户 test-测试租户)',
    `expire_time`      datetime                                        DEFAULT NULL COMMENT '租户过期时间',
    `parent_tenant_id` varchar(32)                                     DEFAULT NULL COMMENT '上级租户ID',
    -- 业务扩展字段
    `tenant_contact`   varchar(64) COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '租户联系人',
    `tenant_phone`     varchar(20) COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '联系人电话',
    `tenant_email`     varchar(128) COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '联系人邮箱',
    `isolation_level`  varchar(32) COLLATE utf8mb4_general_ci          DEFAULT 'shared' COMMENT '数据隔离级别(shared-共享表 separate-独立表 db-独立库)',
    `max_user_num`     int(11)                                DEFAULT 100 COMMENT '租户最大用户数限制',
    `audit_status`     tinyint(1)                             DEFAULT 0 COMMENT '审核状态(0-未审核 1-审核通过 2-审核驳回)',
    `audit_by`         varchar(64) COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '审核人ID',
    `audit_time`       datetime                                        DEFAULT NULL COMMENT '审核时间',

    `create_by`        varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`      datetime                               NOT NULL COMMENT '创建时间',
    `update_by`        varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`      datetime                               NOT NULL COMMENT '修改时间',
    `deleted`          tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tdd_tenant_id` (`tenant_id`) USING BTREE COMMENT '租户id编码',
    INDEX              `idx__tdd_tenant_code` (`tenant_code`) COMMENT '租户code编码',
    INDEX              `idx_tdd_parent_tenant_id` (`parent_tenant_id`) COMMENT '上级租户索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统服务_租户信息表';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`           bigint(11) NOT NULL COMMENT '用户ID',
    `tenant_id`    varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户id',
    `user_name`    varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
    `nick_name`    varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
    `user_type`    varchar(8) COLLATE utf8mb4_general_ci NULL   DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email`        varchar(50) COLLATE utf8mb4_general_ci NULL  DEFAULT '' COMMENT '用户邮箱',
    `phone_number` varchar(11) COLLATE utf8mb4_general_ci NULL  DEFAULT '' COMMENT '手机号码',
    `sex`          char(1) COLLATE utf8mb4_general_ci NULL          DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar`       varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
    `password`     varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
    `status`       char(1) COLLATE utf8mb4_general_ci NULL          DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag`     char(1) COLLATE utf8mb4_general_ci NULL          DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip`     varchar(128) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
    `login_date`   datetime NULL DEFAULT NULL COMMENT '最后登录时间',
    `remark`       varchar(500) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `ding_id`      varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '钉钉ID',
    `wx_id`        varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信ID',
    `qq_id`        varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企鹅ID',

    `create_by`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`  varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`  datetime                               NOT NULL COMMENT '创建时间',
    `update_by`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`  varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`  datetime                               NOT NULL COMMENT '修改时间',
    `deleted`      tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_用户信息表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`                  bigint(11) NOT NULL COMMENT '角色ID',
    `role_name`           varchar(30) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色名称',
    `role_key`            varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort`           int(11) NOT NULL COMMENT '显示顺序',
    `data_scope`          char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
    `status`              char(1) COLLATE utf8mb4_general_ci      NOT NULL COMMENT '角色状态（0正常 1停用）',
    `remark`              varchar(500) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',

    `create_by`           varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人',
    `create_name`         varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '创建人名称',
    `create_time`         datetime                                NOT NULL COMMENT '创建时间',
    `update_by`           varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '修改人',
    `update_name`         varchar(64) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '更新人名称',
    `update_time`         datetime                                NOT NULL COMMENT '修改时间',
    `deleted`             tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_角色信息表';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint(11) NOT NULL COMMENT '菜单ID',
    `menu_name`   varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int(11) NULL DEFAULT 0 COMMENT '显示顺序',
    `path`        varchar(200) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `query`       varchar(255) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
    `is_frame`       int(11) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    `is_cache`       int(11) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type`   char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `remark`      varchar(500) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',

    `create_by`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time` datetime                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time` datetime                               NOT NULL COMMENT '修改时间',
    `deleted`     tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_菜单权限表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(11) NOT NULL COMMENT '用户ID',
    `role_id` bigint(11) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_用户和角色关联表';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_角色和菜单关联表';


DROP TABLE IF EXISTS `sys_lookup_type`;
CREATE TABLE `sys_lookup_type`
(
    `id`               bigint(11) NOT NULL COMMENT '主键，请使用BIGINT类型',
    `parent_id`        bigint(11) NOT NULL COMMENT '父id',
    `lookup_type`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '快码类型',
    `lookup_type_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务领域',
    `status`           char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `remark`           varchar(500) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',

    `create_by`        varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`      datetime                               NOT NULL COMMENT '创建时间',
    `update_by`        varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`      datetime                               NOT NULL COMMENT '修改时间',
    `deleted`          tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `uk_lt_lookup_type`(`lookup_type`, `deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_快码类型表';

DROP TABLE IF EXISTS `sys_lookup_data`;
CREATE TABLE `sys_lookup_data`
(
    `id`             bigint(11) NOT NULL COMMENT '快码id',
    `lookup_sort`    int(11) NULL DEFAULT 0 COMMENT '快码排序',
    `lookup_code`    varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '快码code',
    `lookup_name`    varchar(64) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快码名称',
    `lookup_type_id` varchar(30) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '快码类型id',
    `lookup_type`    varchar(64) COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '快码类型',
    `sta_date`       datetime NULL DEFAULT NULL COMMENT '生效日期',
    `end_date`       datetime NULL DEFAULT NULL COMMENT '失效日期',
    `lookup_tag`     varchar(100) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快码标记',
    `is_default`        char(1) COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`         char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `remark`         varchar(500) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',

    `create_by`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`    datetime                               NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`    datetime                               NOT NULL COMMENT '修改时间',
    `deleted`        tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `idx_ld_lookup_type_id`(`lookup_type_id`) USING BTREE,
    INDEX            `idx_ld_lookup_code_type`(`lookup_code`, `lookup_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_快码数据表';

DROP TABLE IF EXISTS `sys_lookup_attribute`;
CREATE TABLE `sys_lookup_attribute`
(
    `id`             bigint(11) NOT NULL COMMENT '主键',
    `lookup_data_id` bigint(11) NOT NULL COMMENT '快码id',
    `tbl_code`       varchar(64) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段编码',
    `tbl_value`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段属性',
    `tag_name`       varchar(64) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名称',
    `require_flag`   char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否必填（0必填 1非必填）',
    `status`         char(1) COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `remark`         varchar(500) COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',

    `create_by`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_name`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
    `create_time`    datetime                               NOT NULL COMMENT '创建时间',
    `update_by`      varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_name`    varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人名称',
    `update_time`    datetime                               NOT NULL COMMENT '修改时间',
    `deleted`        tinyint(1) NOT NULL COMMENT '是否已删除(0否-1是)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `idx_la_lookup_data_id`(`lookup_data_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC  COMMENT='系统服务_快码属性设置表';