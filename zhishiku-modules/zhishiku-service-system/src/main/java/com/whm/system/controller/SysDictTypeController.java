package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysDictType;
import com.whm.system.service.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统服务_字典类型表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:04:30
 */
@Api(tags = "系统服务_字典类型表")
@RestController
@RequestMapping("/sysDictType")
public class SysDictTypeController {
    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysDictType> queryById(@PathVariable("id") long id) {
        return R.ok(sysDictTypeService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysDictType 筛选条件
     * @param pageQuery   分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysDictType> pageQuery(SysDictType sysDictType, PageQuery pageQuery) {
        return sysDictTypeService.pageQuery(sysDictType, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysDictType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysDictType sysDictType) {
        return R.ok(sysDictTypeService.save(sysDictType));
    }

    /**
     * 更新数据
     *
     * @param sysDictType 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysDictType sysDictType) {
        return R.ok(sysDictTypeService.updateById(sysDictType));
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
        return R.ok(sysDictTypeService.lambdaUpdate().eq(SysDictType::getId, id).set(SysDictType::getDeleted, 1).update());
    }
}