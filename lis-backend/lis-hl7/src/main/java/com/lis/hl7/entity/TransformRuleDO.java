package com.lis.hl7.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("transform_rule")
public class TransformRuleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String ruleCode;

    private String ruleName;

    private String ruleType;

    private String sourceType;

    private String targetType;

    private String sourcePath;

    private String targetPath;

    private String transformExpr;

    private String defaultValue;

    private String valueMapping;

    private Integer isRequired;

    private Integer status;

    private Integer sort;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
