package com.whm.xxljob.common.model;


import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 任务分组
 *
 * @author : 吴华明
 * @since 2025-12-04  13:32
 */
@Data
public class XxlJobGroup {
    private int id;
    private String appname;
    private String title;
    private int addressType;
    private String addressList;
    private Date updateTime;
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (CollectionUtil.isNotEmpty(this.registryList)) {
            this.registryList = new ArrayList<>(Arrays.asList(this.addressList.split(",")));
        }

        return this.registryList;
    }
}
