package com.whm.common.mybatis.config;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * oa 配置参数
 *
 * @author : 吴华明
 * @since 2025-11-27  19:43
 */
@Data
//@Configuration
//@RefreshScope
//@ConfigurationProperties(prefix = "ignoretable")
public class IgnoreTableConfig {
    // 随便设置一些值，放到tables里面
    private List<String> tables = new ArrayList<>();

}

