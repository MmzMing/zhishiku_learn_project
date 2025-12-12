package com.whm.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.content.domain.po.ContentNotePic;
import org.apache.ibatis.annotations.Param;

/**
 * 内容管理服务_笔记多图关联表 表数据库访问层
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:38:54
 */
public interface ContentNotePicMapper extends BaseMapper<ContentNotePic> {
    /**
     * 分页查询
     *
     * @param page           分页对象
     * @param contentNotePic 查询条件
     * @return 分页查询
     */
    IPage<ContentNotePic> pageQuery(Page<ContentNotePic> page, @Param("param") ContentNotePic contentNotePic);
}