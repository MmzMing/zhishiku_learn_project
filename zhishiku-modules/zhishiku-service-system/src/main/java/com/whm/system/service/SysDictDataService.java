package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysDictData;

/**
 * 系统服务_字典数据表 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 14:05:33
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 分页查询
     *
     * @param sysDictData 筛选条件
     * @param pageQuery        分页查询
     * @return 查询结果
     */
    TableDataInfo<SysDictData> pageQuery( SysDictData sysDictData, PageQuery pageQuery);

}