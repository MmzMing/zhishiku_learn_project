package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysLookupAttribute;
import com.whm.system.mapper.SysLookupAttributeMapper;
import com.whm.system.service.SysLookupAttributeService;

/**
 * 系统服务_快码属性设置表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:58:59
 */
@Service
public class SysLookupAttributeServiceImpl extends ServiceImpl<SysLookupAttributeMapper, SysLookupAttribute> implements SysLookupAttributeService{
    @Autowired
    private SysLookupAttributeMapper sysLookupAttributeMapper;

    /**
     * 分页查询
     *
     * @param sysLookupAttribute 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysLookupAttribute> pageQuery(SysLookupAttribute sysLookupAttribute, PageQuery pageQuery){
        return TableDataInfo.build(sysLookupAttributeMapper.pageQuery(pageQuery.build(), sysLookupAttribute));
    }

}