package com.whm.common.core.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 树对象
 *
 * @author 吴华明
 */
@ApiModel(value = "树对象", description = "树对象")
@Data
public class TreeVO implements Serializable {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("分类标识")
    private String segment;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("父级名称")
    private String fatherName;
    @ApiModelProperty("父级标识")
    private String fatherSegment;
    @ApiModelProperty("子树")
    private List<TreeVO> child;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeVO treeVO = (TreeVO) o;
        return Objects.equals(segment, treeVO.segment) && Objects.equals(name, treeVO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(segment, name);
    }
}
