package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysDictData;

/**
 * 系统服务_字典数据表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:05:27
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysDictData 查询条件
     * @return 分页查询
     */
    IPage<SysDictData> pageQuery(Page<SysDictData> page, @Param("param") SysDictData sysDictData);
}