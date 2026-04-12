package com.lis.specimen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("test_item_category")
public class TestItemCategoryDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long parentId;

    private String categoryCode;

    private String categoryName;

    private String categoryDesc;

    private Integer status;

    private Integer sort;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String remark;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}