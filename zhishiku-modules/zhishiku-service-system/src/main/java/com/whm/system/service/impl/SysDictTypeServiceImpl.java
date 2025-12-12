package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysDictType;
import com.whm.system.mapper.SysDictTypeMapper;
import com.whm.system.service.SysDictTypeService;

/**
 * 系统服务_字典类型表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:04:14
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService{
    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    /**
     * 分页查询
     *
     * @param sysDictType 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysDictType> pageQuery(SysDictType sysDictType, PageQuery pageQuery){
        return TableDataInfo.build(sysDictTypeMapper.pageQuery(pageQuery.build(), sysDictType));
    }

}