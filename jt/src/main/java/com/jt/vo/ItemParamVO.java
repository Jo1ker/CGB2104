package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ItemParamVO {
    private Integer paramId;
    private String paramVals;
}
