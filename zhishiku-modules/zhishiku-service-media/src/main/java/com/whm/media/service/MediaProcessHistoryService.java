package com.whm.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.media.domain.po.MediaProcessHistory;


/**
 * minio上传历史处理信息 表服务接口
 * @author : 吴华明
 * @since : 2025-09-18 15:20:44
 */
public interface MediaProcessHistoryService extends IService<MediaProcessHistory> {

    /**
     * 分页查询
     *
     * @param mediaProcessHistory 筛选条件
     * @param pageQuery        分页查询
     * @return
     */
    TableDataInfo<MediaProcessHistory> pageQuery(MediaProcessHistory mediaProcessHistory, PageQuery pageQuery);

}