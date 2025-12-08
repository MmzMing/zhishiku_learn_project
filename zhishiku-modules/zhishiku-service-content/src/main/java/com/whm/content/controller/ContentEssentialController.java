package com.whm.content.controller;

import com.whm.common.core.domain.R;
import com.whm.common.core.exception.service.ServiceException;
import com.whm.common.mybatis.page.PageQuery;
import com.whm.common.mybatis.page.TableDataInfo;
import com.whm.content.domain.po.ContentEssential;
import com.whm.content.service.ContentEssentialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * content_essential表控制层
 *
 * @author : 吴华明
 * @date : 2025-09-05 16:31:53
 */
@RestController
@Api(tags = "知识库")
@RequestMapping("/contentEssential")
public class ContentEssentialController {
    @Autowired
    private ContentEssentialService contentEssentialService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation(value = "根据id查询")
    @GetMapping("/{id}")
    public R<ContentEssential> queryById(@PathVariable long id) {
        return R.ok(contentEssentialService.queryById(id));
    }
    @GetMapping("/test")
    public R<ContentEssential> queryById() {
        throw new ServiceException("异常");
    }
    /**
     * 分页查询
     *
     * @param contentEssential 查询条件
     * @param pageQuery        分页对象
     * @return 分页查询
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/contentEssentialPaginQuery")
    public TableDataInfo<ContentEssential> contentEssentialPaginQuery(ContentEssential contentEssential, PageQuery pageQuery) {
        return contentEssentialService.contentEssentialPaginQuery(contentEssential, pageQuery);
    }
}