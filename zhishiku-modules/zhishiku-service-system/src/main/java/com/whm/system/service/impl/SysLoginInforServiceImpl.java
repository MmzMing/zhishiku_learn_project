package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whm.system.domain.po.SysLoginInfor;
import com.whm.system.mapper.SysLoginInforMapper;
import com.whm.system.service.SysLoginInforService;

/**
 * 系统服务_系统访问记录 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:02:24
 */
@Service
public class SysLoginInforServiceImpl extends ServiceImpl<SysLoginInforMapper, SysLoginInfor> implements SysLoginInforService{
    @Autowired
    private SysLoginInforMapper sysLoginInforMapper;

    /**
     * 分页查询
     *
     * @param sysLoginInfor 筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysLoginInfor> pageQuery(SysLoginInfor sysLoginInfor, PageQuery pageQuery){
        return TableDataInfo.build(sysLoginInforMapper.pageQuery(pageQuery.build(), sysLoginInfor));
    }

}