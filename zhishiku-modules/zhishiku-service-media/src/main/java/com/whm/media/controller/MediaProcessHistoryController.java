package com.whm.media.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.media.domain.po.MediaProcessHistory;
import com.whm.media.service.MediaProcessHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 媒资服务_文件处理历史表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:48:20
 */
@Api(tags = "媒资服务_文件处理历史表")
@RestController
@RequestMapping("/mediaProcessHistory")
public class MediaProcessHistoryController {
    @Autowired
    private MediaProcessHistoryService mediaProcessHistoryService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<MediaProcessHistory> queryById(@PathVariable("id") long id) {
        return R.ok(mediaProcessHistoryService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param mediaProcessHistory 筛选条件
     * @param pageQuery           分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<MediaProcessHistory> pageQuery(MediaProcessHistory mediaProcessHistory, PageQuery pageQuery) {
        return mediaProcessHistoryService.pageQuery(mediaProcessHistory, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param mediaProcessHistory 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody MediaProcessHistory mediaProcessHistory) {
        return R.ok(mediaProcessHistoryService.save(mediaProcessHistory));
    }

    /**
     * 更新数据
     *
     * @param mediaProcessHistory 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody MediaProcessHistory mediaProcessHistory) {
        return R.ok(mediaProcessHistoryService.updateById(mediaProcessHistory));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "非物理删除数据")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> deleteById(@PathVariable("id") long id) {
        return R.ok(mediaProcessHistoryService.lambdaUpdate().eq(MediaProcessHistory::getId, id).set(MediaProcessHistory::getDeleted, 1).update());
    }
}