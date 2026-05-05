package com.lis.user.service;

import com.lis.user.dto.*;
import com.lis.user.vo.DeptTreeVO;
import com.lis.user.vo.DeptVO;

import java.util.List;

public interface DeptService {

    List<DeptVO> getDeptList(DeptQueryDTO queryDTO);

    DeptVO getDeptById(Long id);

    Long createDept(DeptCreateDTO createDTO);

    void updateDept(DeptUpdateDTO updateDTO);

    void deleteDept(Long id);

    List<DeptVO> getDeptTree();

    List<DeptTreeVO> getDeptTreeNodes();

    List<Long> getDeptIdsByRoleId(Long roleId);
}
