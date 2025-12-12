package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysDictData;
import com.whm.system.service.SysDictDataService;
/**
 * 系统服务_字典数据表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:05:55
 */
@Api(tags = "系统服务_字典数据表")
@RestController
@RequestMapping("/sysDictData")
public class SysDictDataController{
    @Autowired
    private SysDictDataService sysDictDataService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysDictData> queryById(@PathVariable("id") long id){
        return R.ok(sysDictDataService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysDictData 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysDictData> pageQuery(SysDictData sysDictData, PageQuery pageQuery){
        return sysDictDataService.pageQuery(sysDictData, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysDictData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysDictData sysDictData){
        return R.ok(sysDictDataService.save(sysDictData));
    }

    /**
     * 更新数据
     *
     * @param sysDictData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysDictData sysDictData){
        return R.ok(sysDictDataService.updateById(sysDictData));
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
        return R.ok(sysDictDataService.lambdaUpdate().eq(SysDictData::getId, id).set(SysDictData::getDeleted, 1).update());
    }
}