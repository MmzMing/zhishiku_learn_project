package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysDictType;

/**
 * 系统服务_字典类型表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:03:57
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysDictType 查询条件
     * @return 分页查询
     */
    IPage<SysDictType> pageQuery(Page<SysDictType> page, @Param("param") SysDictType sysDictType);
}