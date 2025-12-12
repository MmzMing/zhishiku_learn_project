package com.whm.content.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.content.domain.po.ContentNote;
import com.whm.content.service.ContentNoteService;
/**
 * 内容管理服务_笔记信息表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 10:57:19
 */
@Api(tags = "内容管理服务_笔记信息表")
@RestController
@RequestMapping("/contentNote")
public class ContentNoteController{
    @Autowired
    private ContentNoteService contentNoteService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<ContentNote> queryById(@PathVariable("id") long id){
        return R.ok(contentNoteService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param contentNote 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<ContentNote> pageQuery(ContentNote contentNote, PageQuery pageQuery){
        return contentNoteService.pageQuery(contentNote, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param contentNote 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody ContentNote contentNote){
        return R.ok(contentNoteService.save(contentNote));
    }

    /**
     * 更新数据
     *
     * @param contentNote 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody ContentNote contentNote){
        return R.ok(contentNoteService.updateById(contentNote));
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
        return R.ok(contentNoteService.lambdaUpdate().eq(ContentNote::getId, id).set(ContentNote::getDeleted, 1).update());
    }
}