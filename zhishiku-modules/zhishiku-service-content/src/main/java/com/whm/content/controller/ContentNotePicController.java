package com.whm.content.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentNotePic;
import com.whm.content.service.ContentNotePicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 内容管理服务_笔记多图关联表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:39:39
 */
@Api(tags = "内容管理服务_笔记多图关联表")
@RestController
@RequestMapping("/contentNotePic")
public class ContentNotePicController {
    @Autowired
    private ContentNotePicService contentNotePicService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<ContentNotePic> queryById(@PathVariable("id") long id) {
        return R.ok(contentNotePicService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param contentNotePic 筛选条件
     * @param pageQuery      分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<ContentNotePic> pageQuery(ContentNotePic contentNotePic, PageQuery pageQuery) {
        return contentNotePicService.pageQuery(contentNotePic, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param contentNotePic 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody ContentNotePic contentNotePic) {
        return R.ok(contentNotePicService.save(contentNotePic));
    }

    /**
     * 更新数据
     *
     * @param contentNotePic 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody ContentNotePic contentNotePic) {
        return R.ok(contentNotePicService.updateById(contentNotePic));
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
        return R.ok(contentNotePicService.lambdaUpdate().eq(ContentNotePic::getId, id).set(ContentNotePic::getDeleted, 1).update());
    }
}