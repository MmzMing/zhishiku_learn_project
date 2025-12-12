package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.system.domain.po.SysOperLog;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务_操作日志记录 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:21:40
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    /**
     * 分页查询
     *
     * @param page       分页对象
     * @param sysOperLog 查询条件
     * @return 分页查询
     */
    IPage<SysOperLog> pageQuery(Page<SysOperLog> page, @Param("param") SysOperLog sysOperLog);
}