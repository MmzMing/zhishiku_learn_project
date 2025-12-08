package com.whm.system.service.impl;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupAttribute;
import com.whm.system.mapper.SysLookupAttributeMapper;
import com.whm.system.service.SysLookupAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 快码属性设置 表服务实现类
 *
 * @author : 吴华明
 * @date : 2025-09-09 12:06:27
 */
@Service
public class SysLookupAttributeServiceImpl extends ServiceImpl<SysLookupAttributeMapper, SysLookupAttribute> implements SysLookupAttributeService {
    @Autowired
    private SysLookupAttributeMapper sysLookupAttributeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysLookupAttribute queryById(long id){
        return sysLookupAttributeMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param sysLookupAttribute 筛选条件
     * @param pageQuery 分页查询
     * @return
     */
    @Override
    public TableDataInfo<SysLookupAttribute> pageQuery(SysLookupAttribute sysLookupAttribute, PageQuery pageQuery){
        return TableDataInfo.build(sysLookupAttributeMapper.pageQuery(pageQuery.build(), sysLookupAttribute));
    }

    /**
     * 新增数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    @Override
    public SysLookupAttribute insert(SysLookupAttribute sysLookupAttribute){
        sysLookupAttributeMapper.insert(sysLookupAttribute);
        return sysLookupAttribute;
    }

    /**
     * 更新数据
     *
     * @param sysLookupAttribute 实例对象
     * @return 实例对象
     */
    @Override
    public SysLookupAttribute update(SysLookupAttribute sysLookupAttribute){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<SysLookupAttribute> chainWrapper = new LambdaUpdateChainWrapper<SysLookupAttribute>(sysLookupAttributeMapper);

        if(!StringUtils.isEmpty(sysLookupAttribute.getTblValue())){
            chainWrapper.eq(SysLookupAttribute::getTblValue, sysLookupAttribute.getTblValue());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getTblAttribute())){
            chainWrapper.eq(SysLookupAttribute::getTblAttribute, sysLookupAttribute.getTblAttribute());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getLabel())){
            chainWrapper.eq(SysLookupAttribute::getLabel, sysLookupAttribute.getLabel());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getRequireFlag())){
            chainWrapper.eq(SysLookupAttribute::getRequireFlag, sysLookupAttribute.getRequireFlag());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getStatus())){
            chainWrapper.eq(SysLookupAttribute::getStatus, sysLookupAttribute.getStatus());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getCreateBy())){
            chainWrapper.eq(SysLookupAttribute::getCreateBy, sysLookupAttribute.getCreateBy());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getCreateName())){
            chainWrapper.eq(SysLookupAttribute::getCreateName, sysLookupAttribute.getCreateName());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getUpdateBy())){
            chainWrapper.eq(SysLookupAttribute::getUpdateBy, sysLookupAttribute.getUpdateBy());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getUpdateName())){
            chainWrapper.eq(SysLookupAttribute::getUpdateName, sysLookupAttribute.getUpdateName());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getRemark())){
            chainWrapper.eq(SysLookupAttribute::getRemark, sysLookupAttribute.getRemark());
        }
        if(!StringUtils.isEmpty(sysLookupAttribute.getDeleted())){
            chainWrapper.eq(SysLookupAttribute::getDeleted, sysLookupAttribute.getDeleted());
        }
        //2. 设置主键，并更新
        chainWrapper.set(SysLookupAttribute::getId,sysLookupAttribute.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(sysLookupAttribute.getId());
        }else{
            return sysLookupAttribute;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(long id){
        int total = sysLookupAttributeMapper.deleteById(id);
        return total > 0;
    }
}