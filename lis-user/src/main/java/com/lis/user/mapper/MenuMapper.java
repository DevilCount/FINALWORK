package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {

    List<MenuDO> selectMenuList(@Param("menuName") String menuName,
                                @Param("status") Integer status,
                                @Param("parentId") Long parentId);

    List<MenuDO> selectMenusByUserId(@Param("userId") Long userId);

    List<MenuDO> selectMenusByRoleId(@Param("roleId") Long roleId);

    List<String> selectPermsByUserId(@Param("userId") Long userId);

    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    List<MenuDO> selectAllMenus();
}
