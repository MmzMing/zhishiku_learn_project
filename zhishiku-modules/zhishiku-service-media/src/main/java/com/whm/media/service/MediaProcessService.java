package com.whm.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.media.domain.po.MediaProcess;

import java.util.List;


/**
 * minio上传信息 表服务接口
 *
 * @author : 吴华明
 * @since : 2025-09-18 15:16:43
 */
public interface MediaProcessService extends IService<MediaProcess> {

    /**
     * 视频转码
     *
     * @param index 当前序号
     * @param total 总分片数
     * @return void
     */
    void mediaProcessProcessCode(int index, int total);

    /**
     * 修改任务状态
     *
     * @param taskId     任务id
     * @param status     任务状态
     * @param fileId     文件id
     * @param url        文件url
     * @param errorMsg   错误信息
     * @return void
     */
    void updateMediaProcessStatus(Long taskId, String status, String fileId, String url, String errorMsg);


    /**
     * 分页查询
     *
     * @param mediaProcess 筛选条件
     * @param pageQuery    分页查询
     * @return
     */
    TableDataInfo<MediaProcess> pageQuery(MediaProcess mediaProcess, PageQuery pageQuery);

}