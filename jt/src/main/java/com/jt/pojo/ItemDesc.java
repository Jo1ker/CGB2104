package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


@TableName("item_desc")
@Data
@Accessors(chain = true)
public class ItemDesc extends BasePojo{

    private Integer id;
    private String itemDesc;

}
