package com.whm.system.controller;

import com.whm.common.core.domain.R;
import com.whm.common.core.utils.StringUtils;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.system.api.domain.vo.LoginUserVo;
import com.whm.system.api.domain.vo.SysUserVo;
import com.whm.system.domain.po.SysMenu;
import com.whm.system.domain.po.SysUser;
import com.whm.system.service.SysMenuService;
import com.whm.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统服务_用户信息表 表控制层
 *
 * @author : 吴华明
 * @since : 2025-12-12 12:28:29
 */
@Api(tags = "系统服务_用户信息表")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
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
    public R<SysUser> queryById(@PathVariable("id") long id) {
        return R.ok(sysUserService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param sysUser   筛选条件
     * @param pageQuery 分页对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    public TableDataInfo<SysUser> pageQuery(SysUser sysUser, PageQuery pageQuery) {
        return sysUserService.pageQuery(sysUser, pageQuery);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "新增数据")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.save(sysUser));
    }

    /**
     * 更新数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @ApiOperation(value = "更新数据")
    @PutMapping("/update")
    public R<Boolean> edit(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.updateById(sysUser));
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
        return R.ok(sysUserService.lambdaUpdate().eq(SysUser::getId, id).set(SysUser::getDeleted, 1).update());
    }


    /**
     * 根据用户名获取用户信息接口 TODO需要加入用户校验 InnerAuth
     *
     * @param username 用户名
     * @return 返回封装了用户信息和权限的LoginUserVo对象，如果用户不存在则返回失败信息
     */
    @GetMapping("/info/{username}")
    public R<LoginUserVo> info(@PathVariable("username") String username) {
        // 查询用户信息
        SysUserVo sysUser = sysUserService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            return R.fail("用户名或密码错误");
        }

        // 构建登录用户信息对象
        LoginUserVo sysUserVo = new LoginUserVo();
        sysUserVo.setSysUser(sysUser);

        // 获取用户权限按钮集合
        List<SysMenu> sysMenus = sysMenuService.selectButtonByUserId(sysUser.getId());
        Set<String> collect = sysMenus.stream().map(SysMenu::getComponent).collect(Collectors.toSet());
        sysUserVo.setPermissions(collect);

        return R.ok(sysUserVo);
    }


    /**
     * 用户注册接口
     * <p>
     * 接收用户提交的注册信息，包括手机号、短信验证码、用户名、密码和邮箱等，
     * 并完成用户注册逻辑。若未提供用户名则使用手机号作为用户名；若未提供密码，
     * 则系统将自动生成一个8位随机密码。
     * </p>
     *
     * @param user SysUser实体对象，用于封装用户基本信息
     * @return 注册结果，包含操作是否成功的状态及提示信息
     */
    @PostMapping("/register")
    public R<Boolean> userRegister(@RequestBody SysUser user) {
        //用户名已注册

        //该手机号已注册

        //邮箱已被注册

        //redis根据手机号获取code

        //手机验证码失效，请重新获取

        //手机验证码错误


        //设置加盐密码


        return R.ok(true);
    }

}