package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysLookupAttribute;
import com.whm.system.service.SysLookupAttributeService;
/**
 * 系统服务_快码属性设置表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:59:09
 */
@Api(tags = "系统服务_快码属性设置表")
@RestController
@RequestMapping("/sysLookupAttribute")
public class SysLookupAttributeController{
    @Autowired
    private SysLookupAttributeService sysLookupAttributeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysLookupAttribute> queryById(@PathVariable("id") long id){
        return R.ok(sysLookupAttributeService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysLookupAttribute 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysLookupAttribute> pageQuery(SysLookupAttribute sysLookupAttribute, PageQuery pageQuery){
        return sysLookupAttributeService.pageQuery(sysLookupAttribute, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysLookupAttribute sysLookupAttribute){
        return R.ok(sysLookupAttributeService.save(sysLookupAttribute));
    }

    /**
     * 更新数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysLookupAttribute sysLookupAttribute){
        return R.ok(sysLookupAttributeService.updateById(sysLookupAttribute));
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
        return R.ok(sysLookupAttributeService.lambdaUpdate().eq(SysLookupAttribute::getId, id).set(SysLookupAttribute::getDeleted, 1).update());
    }
}