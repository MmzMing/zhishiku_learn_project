package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysNotice;
import com.whm.system.service.SysNoticeService;
/**
 * 系统服务_通知公告表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:26:59
 */
@Api(tags = "系统服务_通知公告表")
@RestController
@RequestMapping("/sysNotice")
public class SysNoticeController{
    @Autowired
    private SysNoticeService sysNoticeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysNotice> queryById(@PathVariable("id") long id){
        return R.ok(sysNoticeService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysNotice 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysNotice> pageQuery(SysNotice sysNotice, PageQuery pageQuery){
        return sysNoticeService.pageQuery(sysNotice, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysNotice 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysNotice sysNotice){
        return R.ok(sysNoticeService.save(sysNotice));
    }

    /**
     * 更新数据
     *
     * @param sysNotice 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysNotice sysNotice){
        return R.ok(sysNoticeService.updateById(sysNotice));
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
        return R.ok(sysNoticeService.lambdaUpdate().eq(SysNotice::getId, id).set(SysNotice::getDeleted, 1).update());
    }
}