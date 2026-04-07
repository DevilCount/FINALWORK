package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lis.common.exception.BusinessException;
import com.lis.user.dto.*;
import com.lis.user.entity.MenuDO;
import com.lis.user.mapper.MenuMapper;
import com.lis.user.service.MenuService;
import com.lis.user.vo.MenuTreeVO;
import com.lis.user.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public List<MenuVO> getMenuList(MenuQueryDTO queryDTO) {
        List<MenuDO> menuList = menuMapper.selectMenuList(
                queryDTO.getMenuName(),
                queryDTO.getStatus(),
                queryDTO.getParentId()
        );
        return menuList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public MenuVO getMenuById(Long id) {
        MenuDO menuDO = menuMapper.selectById(id);
        if (menuDO == null) {
            throw new BusinessException("菜单不存在");
        }
        return convertToVO(menuDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMenu(MenuCreateDTO createDTO) {
        MenuDO menuDO = new MenuDO();
        BeanUtil.copyProperties(createDTO, menuDO);
        menuDO.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 0);
        menuDO.setCreateTime(LocalDateTime.now());
        menuDO.setUpdateTime(LocalDateTime.now());

        menuMapper.insert(menuDO);

        log.info("创建菜单成功: menuId={}, menuName={}", menuDO.getId(), menuDO.getMenuName());
        return menuDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuUpdateDTO updateDTO) {
        MenuDO existMenu = menuMapper.selectById(updateDTO.getId());
        if (existMenu == null) {
            throw new BusinessException("菜单不存在");
        }

        MenuDO menuDO = new MenuDO();
        BeanUtil.copyProperties(updateDTO, menuDO);
        menuDO.setUpdateTime(LocalDateTime.now());

        menuMapper.updateById(menuDO);

        log.info("更新菜单成功: menuId={}", updateDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long id) {
        MenuDO menuDO = menuMapper.selectById(id);
        if (menuDO == null) {
            throw new BusinessException("菜单不存在");
        }

        List<MenuDO> children = menuMapper.selectMenuList(null, null, id);
        if (!children.isEmpty()) {
            throw new BusinessException("存在子菜单，不能删除");
        }

        menuMapper.deleteById(id);

        log.info("删除菜单成功: menuId={}", id);
    }

    @Override
    public List<MenuVO> getMenuTree() {
        List<MenuDO> allMenus = menuMapper.selectAllMenus();
        List<MenuVO> menuVOList = allMenus.stream().map(this::convertToVO).collect(Collectors.toList());
        return buildMenuTree(menuVOList, 0L);
    }

    @Override
    public List<MenuTreeVO> getMenuTreeNodes() {
        List<MenuDO> allMenus = menuMapper.selectAllMenus();
        List<MenuTreeVO> treeVOList = allMenus.stream()
                .map(menu -> {
                    MenuTreeVO treeVO = new MenuTreeVO();
                    treeVO.setId(menu.getId());
                    treeVO.setParentId(menu.getParentId());
                    treeVO.setLabel(menu.getMenuName());
                    treeVO.setMenuType(menu.getMenuType());
                    return treeVO;
                }).collect(Collectors.toList());
        return buildTreeNodes(treeVOList, 0L);
    }

    @Override
    public List<MenuVO> getMenusByUserId(Long userId) {
        List<MenuDO> menuList = menuMapper.selectMenusByUserId(userId);
        List<MenuVO> menuVOList = menuList.stream().map(this::convertToVO).collect(Collectors.toList());
        return buildMenuTree(menuVOList, 0L);
    }

    @Override
    public List<MenuVO> getMenusByRoleId(Long roleId) {
        List<MenuDO> menuList = menuMapper.selectMenusByRoleId(roleId);
        return menuList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        return menuMapper.selectPermsByUserId(userId);
    }

    private MenuVO convertToVO(MenuDO menuDO) {
        MenuVO menuVO = new MenuVO();
        BeanUtil.copyProperties(menuDO, menuVO);
        return menuVO;
    }

    private List<MenuVO> buildMenuTree(List<MenuVO> menuList, Long parentId) {
        List<MenuVO> result = new ArrayList<>();
        Map<Long, List<MenuVO>> menuMap = menuList.stream()
                .collect(Collectors.groupingBy(MenuVO::getParentId));

        for (MenuVO menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                result.add(menu);
            }
        }
        return result;
    }

    private List<MenuTreeVO> buildTreeNodes(List<MenuTreeVO> nodeList, Long parentId) {
        List<MenuTreeVO> result = new ArrayList<>();
        for (MenuTreeVO node : nodeList) {
            if (node.getParentId().equals(parentId)) {
                node.setChildren(buildTreeNodes(nodeList, node.getId()));
                result.add(node);
            }
        }
        return result;
    }
}
