package com.whm.content.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.content.domain.po.ContentNoteComment;
import com.whm.content.service.ContentNoteCommentService;
/**
 * 内容管理服务_笔记评论表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 11:31:53
 */
@Api(tags = "内容管理服务_笔记评论表")
@RestController
@RequestMapping("/contentNoteComment")
public class ContentNoteCommentController{
    @Autowired
    private ContentNoteCommentService contentNoteCommentService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<ContentNoteComment> queryById(@PathVariable("id") long id){
        return R.ok(contentNoteCommentService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param contentNoteComment 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<ContentNoteComment> pageQuery(ContentNoteComment contentNoteComment, PageQuery pageQuery){
        return contentNoteCommentService.pageQuery(contentNoteComment, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param contentNoteComment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody ContentNoteComment contentNoteComment){
        return R.ok(contentNoteCommentService.save(contentNoteComment));
    }

    /**
     * 更新数据
     *
     * @param contentNoteComment 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody ContentNoteComment contentNoteComment){
        return R.ok(contentNoteCommentService.updateById(contentNoteComment));
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
        return R.ok(contentNoteCommentService.lambdaUpdate().eq(ContentNoteComment::getId, id).set(ContentNoteComment::getDeleted, 1).update());
    }
}