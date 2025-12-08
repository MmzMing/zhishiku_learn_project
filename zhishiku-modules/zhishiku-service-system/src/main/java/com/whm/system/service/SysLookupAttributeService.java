package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupAttribute;


/**
 * 快码属性设置 表服务接口
 * @author : 吴华明
 * @date : 2025-09-09 12:04:09
 */
public interface SysLookupAttributeService extends IService<SysLookupAttribute> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLookupAttribute queryById(long id);

    /**
     * 分页查询
     *
     * @param sysLookupAttribute 筛选条件
     * @param pageQuery        分页查询
     * @return
     */
    TableDataInfo<SysLookupAttribute> pageQuery( SysLookupAttribute sysLookupAttribute, PageQuery pageQuery);

    /**
     * 新增数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    SysLookupAttribute insert(SysLookupAttribute sysLookupAttribute);

    /**
     * 更新数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    SysLookupAttribute update(SysLookupAttribute sysLookupAttribute);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(long id);
}