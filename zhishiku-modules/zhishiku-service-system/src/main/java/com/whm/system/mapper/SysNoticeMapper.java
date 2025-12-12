package com.whm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.whm.system.domain.po.SysNotice;

/**
 * 系统服务_通知公告表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:26:19
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice>{
    /**
     * 分页查询
     *
     * @param page             分页对象
     * @param sysNotice 查询条件
     * @return 分页查询
     */
    IPage<SysNotice> pageQuery(Page<SysNotice> page, @Param("param") SysNotice sysNotice);
}