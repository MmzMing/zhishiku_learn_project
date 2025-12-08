package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupData;


/**
 * 快码数据表 表服务接口
 * @author : 吴华明
 * @date : 2025-09-09 12:27:06
 */
public interface SysLookupDataService extends IService<SysLookupData> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLookupData queryById(long id);

    /**
     * 分页查询
     *
     * @param sysLookupData 筛选条件
     * @param pageQuery        分页查询
     * @return
     */
    TableDataInfo<SysLookupData> pageQuery( SysLookupData sysLookupData, PageQuery pageQuery);

    /**
     * 新增数据
     *
     * @param sysLookupData 实例对象
     * @return 实例对象
     */
    SysLookupData insert(SysLookupData sysLookupData);

    /**
     * 更新数据
     *
     * @param sysLookupData 实例对象
     * @return 实例对象
     */
    SysLookupData update(SysLookupData sysLookupData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(long id);
}