package com.whm.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.media.domain.po.MediaProcessHistory;


/**
 * minio上传历史处理信息 表服务接口
 * @author : 吴华明
 * @date : 2025-09-18 15:20:44
 */
public interface MediaProcessHistoryService extends IService<MediaProcessHistory> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MediaProcessHistory queryById(long id);

    /**
     * 分页查询
     *
     * @param mediaProcessHistory 筛选条件
     * @param pageQuery        分页查询
     * @return
     */
    TableDataInfo<MediaProcessHistory> pageQuery(MediaProcessHistory mediaProcessHistory, PageQuery pageQuery);

    /**
     * 新增数据
     *
     * @param mediaProcessHistory 实例对象
     * @return 实例对象
     */
    MediaProcessHistory insert(MediaProcessHistory mediaProcessHistory);

    /**
     * 更新数据
     *
     * @param mediaProcessHistory 实例对象
     * @return 实例对象
     */
    MediaProcessHistory update(MediaProcessHistory mediaProcessHistory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(long id);
}