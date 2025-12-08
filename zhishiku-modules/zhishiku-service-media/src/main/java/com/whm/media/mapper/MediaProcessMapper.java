package com.whm.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whm.media.domain.po.MediaProcess;
import org.apache.ibatis.annotations.Param;

/**
 * minio上传信息 表数据库访问层
 *
 * @author : 吴华明
 * @date : 2025-09-18 15:15:48
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {
    /**
     * 分页查询
     *
     * @param page         分页对象
     * @param mediaProcess 查询条件
     * @return 分页查询
     */
    IPage<MediaProcess> pageQuery(Page<MediaProcess> page, @Param("param") MediaProcess mediaProcess);
}
