package com.whm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.domain.po.SysMenu;
import com.whm.system.mapper.SysMenuMapper;
import com.whm.system.mapper.SysRoleMenuMapper;
import com.whm.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统服务_菜单权限表 表服务实现类
 *
 * @author : 吴华明
 * @since : 2025-12-12 13:29:54
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 分页查询
     *
     * @param sysMenu   筛选条件
     * @param pageQuery 分页查询
     * @return 分页数据
     */
    @Override
    public TableDataInfo<SysMenu> pageQuery(SysMenu sysMenu, PageQuery pageQuery) {
        return TableDataInfo.build(sysMenuMapper.pageQuery(pageQuery.build(), sysMenu));
    }

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectButtonByUserId(Long userId) {
        // 根据用户查询菜单
        // 默认保存所有id，保存时候做手脚
        return sysMenuMapper.selectBatchIds(sysRoleMenuMapper.selectMenuTreeByUserId(userId));
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        //收集非顶级菜单的父级ID

        //查询按钮权限

        //补充缺失的上级菜单

        //去重并排序
        List<SysMenu> sysMenuList = new ArrayList<>();

        //构建父子级菜单树
        return getChildPerms(sysMenuList, 0);
    }


    /**
     * 递归查询菜单的父级菜单
     *
     * @param menuByProjectId 项目下的所有菜单列表
     * @param menus           存储查询到的父级菜单的集合
     * @param parentId        要查询的父级菜单ID
     */
    public void queryParentMenu(List<SysMenu> menuByProjectId, List<SysMenu> menus, Long parentId) {
        // 遍历菜单列表，查找指定父ID的菜单项
        menuByProjectId.forEach(value -> {
                    if (value.getId().equals(parentId)) {
                        menus.add(value);
                        // 如果当前菜单还有父级菜单，则递归查询
                        if (value.getParentId() != null && value.getParentId() != 0) {
                            queryParentMenu(menuByProjectId, menus, value.getParentId());
                        }
                    }
                }
        );
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }


    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist.stream().sorted(Comparator.comparingLong(SysMenu::getOrderNum)
                .thenComparingLong(SysMenu::getId)).collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }

}