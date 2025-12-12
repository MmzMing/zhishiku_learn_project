package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysOperLog;

/**
 * 系统服务_操作日志记录 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:21:50
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 分页查询
     *
     * @param sysOperLog 筛选条件
     * @param pageQuery  分页查询
     * @return 查询结果
     */
    TableDataInfo<SysOperLog> pageQuery(SysOperLog sysOperLog, PageQuery pageQuery);

}