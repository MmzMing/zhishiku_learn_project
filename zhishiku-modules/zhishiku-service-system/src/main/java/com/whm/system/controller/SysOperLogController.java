package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.api.domain.dto.SysOperLogDto;
import com.whm.system.domain.po.SysOperLog;
import com.whm.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统服务_操作日志记录 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:22:37
 */
@Api(tags = "系统服务_操作日志记录")
@RestController
@RequestMapping("/sysOperLog")
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<SysOperLog> queryById(@PathVariable("id") long id) {
        return R.ok(sysOperLogService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysOperLog 筛选条件
     * @param pageQuery  分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysOperLog> pageQuery(SysOperLog sysOperLog, PageQuery pageQuery) {
        return sysOperLogService.pageQuery(sysOperLog, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysOperLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysOperLog sysOperLog) {
        return R.ok(sysOperLogService.save(sysOperLog));
    }

    /**
     * 新增数据
     *
     * @param sysOperLogDto 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/saveLog")
    public R<Boolean> saveLog(@RequestBody SysOperLogDto sysOperLogDto) {
        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(sysOperLogDto, sysOperLog);
        return R.ok(sysOperLogService.save(sysOperLog));
    }

    /**
     * 更新数据
     *
     * @param sysOperLog 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysOperLog sysOperLog) {
        return R.ok(sysOperLogService.updateById(sysOperLog));
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
        return R.ok(sysOperLogService.removeById(id));
    }
}