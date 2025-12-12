package com.whm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysNotice;

/**
 * 系统服务_通知公告表 表服务接口
 * @author : 吴华明
 * @since : 2025-12-12 13:26:32
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 分页查询
     *
     * @param sysNotice 筛选条件
     * @param pageQuery        分页查询
     * @return 查询结果
     */
    TableDataInfo<SysNotice> pageQuery( SysNotice sysNotice, PageQuery pageQuery);

}