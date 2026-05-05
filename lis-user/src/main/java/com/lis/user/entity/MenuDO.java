package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class MenuDO extends BaseEntity {

    private String menuName;

    private Long parentId;

    private Integer orderNum;

    private String path;

    private String component;

    private String query;

    private String routeName;

    private Integer isFrame;

    private Integer isCache;

    private String menuType;

    private Integer visible;

    private Integer status;

    private String perms;

    private String icon;
}
