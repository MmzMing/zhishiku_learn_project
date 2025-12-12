package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysTenantData;
import com.whm.system.service.SysTenantDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统服务_租户信息表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:09:24
 */
@Api(tags = "系统服务_租户信息表")
@RestController
@RequestMapping("/sysTenantData")
public class SysTenantDataController {
    @Autowired
    private SysTenantDataService sysTenantDataService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysTenantData> queryById(@PathVariable("id") long id) {
        return R.ok(sysTenantDataService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysTenantData 筛选条件
     * @param pageQuery     分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysTenantData> pageQuery(SysTenantData sysTenantData, PageQuery pageQuery) {
        return sysTenantDataService.pageQuery(sysTenantData, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysTenantData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysTenantData sysTenantData) {
        return R.ok(sysTenantDataService.save(sysTenantData));
    }

    /**
     * 更新数据
     *
     * @param sysTenantData 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysTenantData sysTenantData) {
        return R.ok(sysTenantDataService.updateById(sysTenantData));
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
        return R.ok(sysTenantDataService.lambdaUpdate().eq(SysTenantData::getId, id).set(SysTenantData::getDeleted, 1).update());
    }
}