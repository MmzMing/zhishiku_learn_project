package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysLoginInfor;

/**
 * 系统服务_系统访问记录 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:02:07
 */
public interface SysLoginInforMapper extends BaseMapper<SysLoginInfor>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysLoginInfor 查询条件
     * @return 分页查询
     */
    IPage<SysLoginInfor> pageQuery(Page<SysLoginInfor> page, @Param("param") SysLoginInfor sysLoginInfor);
}