package com.whm.common.core.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 时间
 *
 * @author 吴华明
 */
@Data
public class DateDTO implements Serializable {

    @ApiModelProperty("时间")
    private Date date;

    @ApiModelProperty("标识（1 表示星期天，2 表示星期一，以此类推）")
    private Integer dayOfWeek;

    @ApiModelProperty("yyMM + W+ 周数")
    private String week;
}

