package com.whm.media.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.media.domain.po.MediaProcess;
import com.whm.media.service.MediaProcessService;
/**
 * 媒资服务_文件类型转换表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:49:26
 */
@Api(tags = "媒资服务_文件类型转换表")
@RestController
@RequestMapping("/mediaProcess")
public class MediaProcessController{
    @Autowired
    private MediaProcessService mediaProcessService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<MediaProcess> queryById(@PathVariable("id") long id){
        return R.ok(mediaProcessService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param mediaProcess 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<MediaProcess> pageQuery(MediaProcess mediaProcess, PageQuery pageQuery){
        return mediaProcessService.pageQuery(mediaProcess, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param mediaProcess 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody MediaProcess mediaProcess){
        return R.ok(mediaProcessService.save(mediaProcess));
    }

    /**
     * 更新数据
     *
     * @param mediaProcess 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody MediaProcess mediaProcess){
        return R.ok(mediaProcessService.updateById(mediaProcess));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "非物理删除数据")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> deleteById(@PathVariable("id") long id){
        return R.ok(mediaProcessService.lambdaUpdate().eq(MediaProcess::getId, id).set(MediaProcess::getDeleted, 1).update());
    }
}