package com.lis.user.controller;

import com.lis.common.result.Result;
import com.lis.user.dto.*;
import com.lis.user.service.DeptService;
import com.lis.user.vo.DeptTreeVO;
import com.lis.user.vo.DeptVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @ApiOperation("查询部门列表")
    @GetMapping("/list")
    public Result<List<DeptVO>> getDeptList(DeptQueryDTO queryDTO) {
        List<DeptVO> result = deptService.getDeptList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询部门")
    @GetMapping("/{id}")
    public Result<DeptVO> getDeptById(@PathVariable Long id) {
        DeptVO deptVO = deptService.getDeptById(id);
        return Result.success(deptVO);
    }

    @ApiOperation("创建部门")
    @PostMapping
    public Result<Long> createDept(@Validated @RequestBody DeptCreateDTO createDTO) {
        Long deptId = deptService.createDept(createDTO);
        return Result.success("创建成功", deptId);
    }

    @ApiOperation("更新部门")
    @PutMapping
    public Result<Void> updateDept(@Validated @RequestBody DeptUpdateDTO updateDTO) {
        deptService.updateDept(updateDTO);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    public Result<Void> deleteDept(@PathVariable Long id) {
        deptService.deleteDept(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("获取部门树")
    @GetMapping("/tree")
    public Result<List<DeptVO>> getDeptTree() {
        List<DeptVO> tree = deptService.getDeptTree();
        return Result.success(tree);
    }

    @ApiOperation("获取部门树节点（用于选择器）")
    @GetMapping("/tree-nodes")
    public Result<List<DeptTreeVO>> getDeptTreeNodes() {
        List<DeptTreeVO> nodes = deptService.getDeptTreeNodes();
        return Result.success(nodes);
    }

    @ApiOperation("获取角色部门ID列表")
    @GetMapping("/role/{roleId}")
    public Result<List<Long>> getDeptIdsByRoleId(@PathVariable Long roleId) {
        List<Long> deptIds = deptService.getDeptIdsByRoleId(roleId);
        return Result.success(deptIds);
    }
}
