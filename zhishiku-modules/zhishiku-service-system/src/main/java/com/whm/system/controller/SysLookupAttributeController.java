package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupAttribute;
import com.whm.system.service.SysLookupAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 快码属性设置 表控制层
 *
 * @author : 吴华明
 * @date : 2025-09-09 12:08:10
 */
@Api(tags = "快码属性设置")
@RestController
@RequestMapping("/sysLookupAttribute")
public class SysLookupAttributeController {
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
    public R<SysLookupAttribute> queryById(@PathVariable long id) {
        return R.ok(sysLookupAttributeService.queryById(id));
    }

    /**
     * 分页查询
     *
     * @param sysLookupAttribute 筛选条件
     * @param pageQuery          分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("pageQuery")
    public TableDataInfo<SysLookupAttribute> pageQuery(SysLookupAttribute sysLookupAttribute, PageQuery pageQuery) {
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
    public R<SysLookupAttribute> add(@RequestBody SysLookupAttribute sysLookupAttribute) {
        return R.ok(sysLookupAttributeService.insert(sysLookupAttribute));
    }

    /**
     * 更新数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<SysLookupAttribute> edit(@RequestBody SysLookupAttribute sysLookupAttribute) {
        return R.ok(sysLookupAttributeService.update(sysLookupAttribute));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation(value = "非物理删除数据")
    @DeleteMapping("/delete")
    public R<Boolean> deleteById(long id) {
        return R.ok(sysLookupAttributeService.lambdaUpdate().eq(SysLookupAttribute::getId, id).set(SysLookupAttribute::getDeleted, 1).update());
    }
}