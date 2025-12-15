package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.api.domain.dto.SysLoginInforDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.whm.system.domain.po.SysLoginInfor;
import com.whm.system.service.SysLoginInforService;
/**
 * 系统服务_系统访问记录 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 14:02:34
 */
@Api(tags = "系统服务_系统访问记录")
@RestController
@RequestMapping("/sysLoginInfor")
public class SysLoginInforController{
    @Autowired
    private SysLoginInforService sysLoginInforService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysLoginInfor> queryById(@PathVariable("id") long id){
        return R.ok(sysLoginInforService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysLoginInfor 筛选条件
     * @param pageQuery        分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysLoginInfor> pageQuery(SysLoginInfor sysLoginInfor, PageQuery pageQuery){
        return sysLoginInforService.pageQuery(sysLoginInfor, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysLoginInfor 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysLoginInfor sysLoginInfor){
        return R.ok(sysLoginInforService.save(sysLoginInfor));
    }

    /**
     * 新增数据
     *
     * @param sysLoginInforDto 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "API-新增数据")
    @PostMapping("/saveLoginInfor")
    public R<Boolean> saveLoginInfor(@RequestBody SysLoginInforDto sysLoginInforDto){
        SysLoginInfor sysLoginInfor = new SysLoginInfor();
        BeanUtils.copyProperties(sysLoginInforDto, sysLoginInfor);
        return R.ok(sysLoginInforService.save(sysLoginInfor));
    }
    /**
     * 更新数据
     *
     * @param sysLoginInfor 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysLoginInfor sysLoginInfor){
        return R.ok(sysLoginInforService.updateById(sysLoginInfor));
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
        return R.ok(sysLoginInforService.removeById(id));
    }
}