package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysDictData;
import com.whm.system.mapper.SysDictDataMapper;
import com.whm.system.service.SysDictDataService;

/**
 * 系统服务_字典数据表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:05:43
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService{
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 分页查询
     *
     * @param sysDictData 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysDictData> pageQuery(SysDictData sysDictData, PageQuery pageQuery){
        return TableDataInfo.build(sysDictDataMapper.pageQuery(pageQuery.build(), sysDictData));
    }

}