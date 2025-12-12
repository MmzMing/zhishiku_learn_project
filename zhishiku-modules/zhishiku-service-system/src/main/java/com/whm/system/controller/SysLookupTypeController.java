package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysLookupType;
import com.whm.system.service.SysLookupTypeService;
/**
 * 系统服务_快码类型表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:31:24
 */
@Api(tags = "系统服务_快码类型表")
@RestController
@RequestMapping("/sysLookupType")
public class SysLookupTypeController{
    @Autowired
    private SysLookupTypeService sysLookupTypeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysLookupType> queryById(@PathVariable("id") long id){
        return R.ok(sysLookupTypeService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysLookupType 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysLookupType> pageQuery(SysLookupType sysLookupType, PageQuery pageQuery){
        return sysLookupTypeService.pageQuery(sysLookupType, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysLookupType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysLookupType sysLookupType){
        return R.ok(sysLookupTypeService.save(sysLookupType));
    }

    /**
     * 更新数据
     *
     * @param sysLookupType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysLookupType sysLookupType){
        return R.ok(sysLookupTypeService.updateById(sysLookupType));
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
        return R.ok(sysLookupTypeService.lambdaUpdate().eq(SysLookupType::getId, id).set(SysLookupType::getDeleted, 1).update());
    }
}