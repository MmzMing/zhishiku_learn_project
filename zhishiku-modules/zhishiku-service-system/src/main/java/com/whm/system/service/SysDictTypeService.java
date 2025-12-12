package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysDictType;

/**
 * 系统服务_字典类型表 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 14:04:03
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 分页查询
     *
     * @param sysDictType 筛选条件
     * @param pageQuery        分页查询
     * @return 查询结果
     */
    TableDataInfo<SysDictType> pageQuery( SysDictType sysDictType, PageQuery pageQuery);

}