package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysLookupData;
import com.whm.system.mapper.SysLookupDataMapper;
import com.whm.system.service.SysLookupDataService;

/**
 * 系统服务_快码数据表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:56:50
 */
@Service
public class SysLookupDataServiceImpl extends ServiceImpl<SysLookupDataMapper, SysLookupData> implements SysLookupDataService{
    @Autowired
    private SysLookupDataMapper sysLookupDataMapper;

    /**
     * 分页查询
     *
     * @param sysLookupData 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysLookupData> pageQuery(SysLookupData sysLookupData, PageQuery pageQuery){
        return TableDataInfo.build(sysLookupDataMapper.pageQuery(pageQuery.build(), sysLookupData));
    }

}