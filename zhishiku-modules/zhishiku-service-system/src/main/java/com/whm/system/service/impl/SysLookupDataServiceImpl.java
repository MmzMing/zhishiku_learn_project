package com.whm.system.service.impl;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysLookupData;
import com.whm.system.mapper.SysLookupDataMapper;
import com.whm.system.service.SysLookupDataService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 快码数据表 表服务实现类
 *
 * @author : 吴华明
 * @date : 2025-09-09 12:27:35
 */
@Service
public class SysLookupDataServiceImpl extends ServiceImpl<SysLookupDataMapper, SysLookupData> implements SysLookupDataService {
    @Autowired
    private SysLookupDataMapper sysLookupDataMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysLookupData queryById(long id){
        return sysLookupDataMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param sysLookupData 筛选条件
     * @param pageQuery 分页查询
     * @return
     */
    @Override
    public TableDataInfo<SysLookupData> pageQuery(SysLookupData sysLookupData, PageQuery pageQuery){
        return TableDataInfo.build(sysLookupDataMapper.pageQuery(pageQuery.build(), sysLookupData));
    }

    /**
     * 新增数据
     *
     * @param sysLookupData 实例对象
     * @return 实例对象
     */
    @Override
    public SysLookupData insert(SysLookupData sysLookupData){
        sysLookupDataMapper.insert(sysLookupData);
        return sysLookupData;
    }

    /**
     * 更新数据
     *
     * @param sysLookupData 实例对象
     * @return 实例对象
     */
    @Override
    public SysLookupData update(SysLookupData sysLookupData){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<SysLookupData> chainWrapper = new LambdaUpdateChainWrapper<SysLookupData>(sysLookupDataMapper);

        if(!StringUtils.isEmpty(sysLookupData.getLookupValue())){
            chainWrapper.eq(SysLookupData::getLookupValue, sysLookupData.getLookupValue());
        }
        if(!StringUtils.isEmpty(sysLookupData.getLookupCode())){
            chainWrapper.eq(SysLookupData::getLookupCode, sysLookupData.getLookupCode());
        }
        if(!StringUtils.isEmpty(sysLookupData.getLookupTypeId())){
            chainWrapper.eq(SysLookupData::getLookupTypeId, sysLookupData.getLookupTypeId());
        }
        if(!StringUtils.isEmpty(sysLookupData.getLookupType())){
            chainWrapper.eq(SysLookupData::getLookupType, sysLookupData.getLookupType());
        }
        if(!StringUtils.isEmpty(sysLookupData.getLookupTag())){
            chainWrapper.eq(SysLookupData::getLookupTag, sysLookupData.getLookupTag());
        }
        if(!StringUtils.isEmpty(sysLookupData.getIsDefault())){
            chainWrapper.eq(SysLookupData::getIsDefault, sysLookupData.getIsDefault());
        }
        if(!StringUtils.isEmpty(sysLookupData.getStatus())){
            chainWrapper.eq(SysLookupData::getStatus, sysLookupData.getStatus());
        }
        if(!StringUtils.isEmpty(sysLookupData.getCreateBy())){
            chainWrapper.eq(SysLookupData::getCreateBy, sysLookupData.getCreateBy());
        }
        if(!StringUtils.isEmpty(sysLookupData.getCreateName())){
            chainWrapper.eq(SysLookupData::getCreateName, sysLookupData.getCreateName());
        }
        if(!StringUtils.isEmpty(sysLookupData.getUpdateBy())){
            chainWrapper.eq(SysLookupData::getUpdateBy, sysLookupData.getUpdateBy());
        }
        if(!StringUtils.isEmpty(sysLookupData.getUpdateName())){
            chainWrapper.eq(SysLookupData::getUpdateName, sysLookupData.getUpdateName());
        }
        if(!StringUtils.isEmpty(sysLookupData.getRemark())){
            chainWrapper.eq(SysLookupData::getRemark, sysLookupData.getRemark());
        }
        if(!ObjectUtils.isEmpty(sysLookupData.getDeleted())){
            chainWrapper.eq(SysLookupData::getDeleted, sysLookupData.getDeleted());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute1())){
            chainWrapper.eq(SysLookupData::getAttribute1, sysLookupData.getAttribute1());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute2())){
            chainWrapper.eq(SysLookupData::getAttribute2, sysLookupData.getAttribute2());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute3())){
            chainWrapper.eq(SysLookupData::getAttribute3, sysLookupData.getAttribute3());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute4())){
            chainWrapper.eq(SysLookupData::getAttribute4, sysLookupData.getAttribute4());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute5())){
            chainWrapper.eq(SysLookupData::getAttribute5, sysLookupData.getAttribute5());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute6())){
            chainWrapper.eq(SysLookupData::getAttribute6, sysLookupData.getAttribute6());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute7())){
            chainWrapper.eq(SysLookupData::getAttribute7, sysLookupData.getAttribute7());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute8())){
            chainWrapper.eq(SysLookupData::getAttribute8, sysLookupData.getAttribute8());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute9())){
            chainWrapper.eq(SysLookupData::getAttribute9, sysLookupData.getAttribute9());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute10())){
            chainWrapper.eq(SysLookupData::getAttribute10, sysLookupData.getAttribute10());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute11())){
            chainWrapper.eq(SysLookupData::getAttribute11, sysLookupData.getAttribute11());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute12())){
            chainWrapper.eq(SysLookupData::getAttribute12, sysLookupData.getAttribute12());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute13())){
            chainWrapper.eq(SysLookupData::getAttribute13, sysLookupData.getAttribute13());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute14())){
            chainWrapper.eq(SysLookupData::getAttribute14, sysLookupData.getAttribute14());
        }
        if(!StringUtils.isEmpty(sysLookupData.getAttribute15())){
            chainWrapper.eq(SysLookupData::getAttribute15, sysLookupData.getAttribute15());
        }
        if(!StringUtils.isEmpty(sysLookupData.getLookupName())){
            chainWrapper.eq(SysLookupData::getLookupName, sysLookupData.getLookupName());
        }
        //2. 设置主键，并更新
        chainWrapper.set(SysLookupData::getId,sysLookupData.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(sysLookupData.getId());
        }else{
            return sysLookupData;
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
        int total = sysLookupDataMapper.deleteById(id);
        return total > 0;
    }
}