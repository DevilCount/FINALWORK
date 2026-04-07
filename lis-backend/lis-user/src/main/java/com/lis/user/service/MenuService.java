package com.lis.user.service;

import com.lis.user.dto.*;
import com.lis.user.vo.MenuTreeVO;
import com.lis.user.vo.MenuVO;

import java.util.List;

public interface MenuService {

    List<MenuVO> getMenuList(MenuQueryDTO queryDTO);

    MenuVO getMenuById(Long id);

    Long createMenu(MenuCreateDTO createDTO);

    void updateMenu(MenuUpdateDTO updateDTO);

    void deleteMenu(Long id);

    List<MenuVO> getMenuTree();

    List<MenuTreeVO> getMenuTreeNodes();

    List<MenuVO> getMenusByUserId(Long userId);

    List<MenuVO> getMenusByRoleId(Long roleId);

    List<String> getPermissionsByUserId(Long userId);
}
