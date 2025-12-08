package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysLookupData;

/**
 * 快码数据表 表数据库访问层
 *
 * @author : 吴华明
 * @date : 2025-09-09 12:24:34
 */
public interface SysLookupDataMapper extends BaseMapper<SysLookupData>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysLookupData 查询条件
     * @return 分页查询
     */
    IPage<SysLookupData> pageQuery(Page<SysLookupData> page, @Param("param") SysLookupData sysLookupData);
}