package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysMenu;
import com.whm.system.service.SysMenuService;
/**
 * 系统服务_菜单权限表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:30:06
 */
@Api(tags = "系统服务_菜单权限表")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController{
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysMenu> queryById(@PathVariable("id") long id){
        return R.ok(sysMenuService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysMenu 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysMenu> pageQuery(SysMenu sysMenu, PageQuery pageQuery){
        return sysMenuService.pageQuery(sysMenu, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysMenu sysMenu){
        return R.ok(sysMenuService.save(sysMenu));
    }

    /**
     * 更新数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysMenu sysMenu){
        return R.ok(sysMenuService.updateById(sysMenu));
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
        return R.ok(sysMenuService.lambdaUpdate().eq(SysMenu::getId, id).set(SysMenu::getDeleted, 1).update());
    }
}