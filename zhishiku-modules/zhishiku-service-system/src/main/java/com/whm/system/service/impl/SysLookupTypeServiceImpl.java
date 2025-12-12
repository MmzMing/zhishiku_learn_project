package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysLookupType;
import com.whm.system.mapper.SysLookupTypeMapper;
import com.whm.system.service.SysLookupTypeService;

/**
 * 系统服务_快码类型表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:31:13
 */
@Service
public class SysLookupTypeServiceImpl extends ServiceImpl<SysLookupTypeMapper, SysLookupType> implements SysLookupTypeService{
    @Autowired
    private SysLookupTypeMapper sysLookupTypeMapper;

    /**
     * 分页查询
     *
     * @param sysLookupType 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysLookupType> pageQuery(SysLookupType sysLookupType, PageQuery pageQuery){
        return TableDataInfo.build(sysLookupTypeMapper.pageQuery(pageQuery.build(), sysLookupType));
    }

}