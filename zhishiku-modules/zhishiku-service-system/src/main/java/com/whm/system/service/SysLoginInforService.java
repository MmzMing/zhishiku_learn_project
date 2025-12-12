package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLoginInfor;

/**
 * 系统服务_系统访问记录 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 14:02:14
 */
public interface SysLoginInforService extends IService<SysLoginInfor> {

    /**
     * 分页查询
     *
     * @param sysLoginInfor 筛选条件
     * @param pageQuery        分页查询
     * @return 查询结果
     */
    TableDataInfo<SysLoginInfor> pageQuery( SysLoginInfor sysLoginInfor, PageQuery pageQuery);

}