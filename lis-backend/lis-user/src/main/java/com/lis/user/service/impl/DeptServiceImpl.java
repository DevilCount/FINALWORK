package com.lis.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lis.common.exception.BusinessException;
import com.lis.common.result.PageResult;
import com.lis.common.result.ResultCode;
import com.lis.user.dto.*;
import com.lis.user.entity.DeptDO;
import com.lis.user.mapper.DeptMapper;
import com.lis.user.service.DeptService;
import com.lis.user.vo.DeptTreeVO;
import com.lis.user.vo.DeptVO;
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
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;

    @Override
    public List<DeptVO> getDeptList(DeptQueryDTO queryDTO) {
        List<DeptDO> deptList = deptMapper.selectDeptList(
                queryDTO.getDeptName(),
                queryDTO.getDeptCode(),
                queryDTO.getStatus(),
                queryDTO.getParentId()
        );
        return deptList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public DeptVO getDeptById(Long id) {
        DeptDO deptDO = deptMapper.selectById(id);
        if (deptDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "部门不存在");
        }
        return convertToVO(deptDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDept(DeptCreateDTO createDTO) {
        if (createDTO.getDeptCode() != null && !createDTO.getDeptCode().isEmpty()) {
            DeptDO existDept = deptMapper.selectByDeptCode(createDTO.getDeptCode());
            if (existDept != null) {
                throw new BusinessException(ResultCode.DATA_ALREADY_EXISTS, "部门编码已存在");
            }
        }

        DeptDO deptDO = new DeptDO();
        BeanUtil.copyProperties(createDTO, deptDO);
        deptDO.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 0);
        deptDO.setDelFlag(0);
        deptDO.setCreateTime(LocalDateTime.now());
        deptDO.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(deptDO);

        log.info("创建部门成功: deptId={}, deptName={}", deptDO.getId(), deptDO.getDeptName());
        return deptDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(DeptUpdateDTO updateDTO) {
        DeptDO existDept = deptMapper.selectById(updateDTO.getId());
        if (existDept == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "部门不存在");
        }

        if (updateDTO.getParentId() != null && updateDTO.getParentId().equals(updateDTO.getId())) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "上级部门不能是自己");
        }

        DeptDO deptDO = new DeptDO();
        BeanUtil.copyProperties(updateDTO, deptDO);
        deptDO.setUpdateTime(LocalDateTime.now());

        deptMapper.updateById(deptDO);

        log.info("更新部门成功: deptId={}", updateDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Long id) {
        DeptDO deptDO = deptMapper.selectById(id);
        if (deptDO == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "部门不存在");
        }

        int childCount = deptMapper.selectChildrenCountByParentId(id);
        if (childCount > 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "存在子部门，不能删除");
        }

        deptMapper.deleteById(id);

        log.info("删除部门成功: deptId={}", id);
    }

    @Override
    public List<DeptVO> getDeptTree() {
        List<DeptDO> allDepts = deptMapper.selectAllDepts();
        List<DeptVO> deptVOList = allDepts.stream().map(this::convertToVO).collect(Collectors.toList());
        return buildDeptTree(deptVOList, 0L);
    }

    @Override
    public List<DeptTreeVO> getDeptTreeNodes() {
        List<DeptDO> allDepts = deptMapper.selectAllDepts();
        List<DeptTreeVO> treeVOList = allDepts.stream()
                .map(dept -> {
                    DeptTreeVO treeVO = new DeptTreeVO();
                    treeVO.setId(dept.getId());
                    treeVO.setParentId(dept.getParentId());
                    treeVO.setLabel(dept.getDeptName());
                    return treeVO;
                }).collect(Collectors.toList());
        return buildTreeNodes(treeVOList, 0L);
    }

    @Override
    public List<Long> getDeptIdsByRoleId(Long roleId) {
        return deptMapper.selectDeptIdsByRoleId(roleId);
    }

    private DeptVO convertToVO(DeptDO deptDO) {
        DeptVO deptVO = new DeptVO();
        BeanUtil.copyProperties(deptDO, deptVO);
        return deptVO;
    }

    private List<DeptVO> buildDeptTree(List<DeptVO> deptList, Long parentId) {
        List<DeptVO> result = new ArrayList<>();
        Map<Long, List<DeptVO>> deptMap = deptList.stream()
                .collect(Collectors.groupingBy(DeptVO::getParentId));

        for (DeptVO dept : deptList) {
            if (dept.getParentId().equals(parentId)) {
                dept.setChildren(buildDeptTree(deptList, dept.getId()));
                result.add(dept);
            }
        }
        return result;
    }

    private List<DeptTreeVO> buildTreeNodes(List<DeptTreeVO> nodeList, Long parentId) {
        List<DeptTreeVO> result = new ArrayList<>();
        for (DeptTreeVO node : nodeList) {
            if (node.getParentId().equals(parentId)) {
                node.setChildren(buildTreeNodes(nodeList, node.getId()));
                result.add(node);
            }
        }
        return result;
    }
}
