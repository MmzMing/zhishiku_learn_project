package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysLookupData;
import com.whm.system.service.SysLookupDataService;
/**
 * 系统服务_快码数据表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:57:06
 */
@Api(tags = "系统服务_快码数据表")
@RestController
@RequestMapping("/sysLookupData")
public class SysLookupDataController{
    @Autowired
    private SysLookupDataService sysLookupDataService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysLookupData> queryById(@PathVariable("id") long id){
        return R.ok(sysLookupDataService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysLookupData 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysLookupData> pageQuery(SysLookupData sysLookupData, PageQuery pageQuery){
        return sysLookupDataService.pageQuery(sysLookupData, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysLookupData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysLookupData sysLookupData){
        return R.ok(sysLookupDataService.save(sysLookupData));
    }

    /**
     * 更新数据
     *
     * @param sysLookupData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysLookupData sysLookupData){
        return R.ok(sysLookupDataService.updateById(sysLookupData));
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
        return R.ok(sysLookupDataService.lambdaUpdate().eq(SysLookupData::getId, id).set(SysLookupData::getDeleted, 1).update());
    }
}