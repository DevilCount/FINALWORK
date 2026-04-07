package com.lis.user.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.user.dto.*;
import com.lis.user.service.DictService;
import com.lis.user.vo.DictDataVO;
import com.lis.user.vo.DictTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @ApiOperation("分页查询字典类型列表")
    @GetMapping("/type/list")
    public Result<PageResult<DictTypeVO>> getDictTypeList(DictTypeQueryDTO queryDTO) {
        PageResult<DictTypeVO> result = dictService.getDictTypeList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询字典类型")
    @GetMapping("/type/{id}")
    public Result<DictTypeVO> getDictTypeById(@PathVariable Long id) {
        DictTypeVO dictTypeVO = dictService.getDictTypeById(id);
        return Result.success(dictTypeVO);
    }

    @ApiOperation("根据字典类型查询")
    @GetMapping("/type/code/{dictType}")
    public Result<DictTypeVO> getDictTypeByType(@PathVariable String dictType) {
        DictTypeVO dictTypeVO = dictService.getDictTypeByType(dictType);
        return Result.success(dictTypeVO);
    }

    @ApiOperation("创建字典类型")
    @PostMapping("/type")
    public Result<Long> createDictType(@Validated @RequestBody DictTypeCreateDTO createDTO) {
        Long dictTypeId = dictService.createDictType(createDTO);
        return Result.success("创建成功", dictTypeId);
    }

    @ApiOperation("更新字典类型")
    @PutMapping("/type")
    public Result<Void> updateDictType(@Validated @RequestBody DictTypeUpdateDTO updateDTO) {
        dictService.updateDictType(updateDTO);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除字典类型")
    @DeleteMapping("/type/{id}")
    public Result<Void> deleteDictType(@PathVariable Long id) {
        dictService.deleteDictType(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除字典类型")
    @DeleteMapping("/type/batch")
    public Result<Void> batchDeleteDictTypes(@RequestBody List<Long> ids) {
        dictService.batchDeleteDictTypes(ids);
        return Result.success("删除成功", null);
    }

    @ApiOperation("分页查询字典数据列表")
    @GetMapping("/data/list")
    public Result<PageResult<DictDataVO>> getDictDataList(DictDataQueryDTO queryDTO) {
        PageResult<DictDataVO> result = dictService.getDictDataList(queryDTO);
        return Result.success(result);
    }

    @ApiOperation("根据ID查询字典数据")
    @GetMapping("/data/{id}")
    public Result<DictDataVO> getDictDataById(@PathVariable Long id) {
        DictDataVO dictDataVO = dictService.getDictDataById(id);
        return Result.success(dictDataVO);
    }

    @ApiOperation("创建字典数据")
    @PostMapping("/data")
    public Result<Long> createDictData(@Validated @RequestBody DictDataCreateDTO createDTO) {
        Long dictDataId = dictService.createDictData(createDTO);
        return Result.success("创建成功", dictDataId);
    }

    @ApiOperation("更新字典数据")
    @PutMapping("/data")
    public Result<Void> updateDictData(@Validated @RequestBody DictDataUpdateDTO updateDTO) {
        dictService.updateDictData(updateDTO);
        return Result.success("更新成功", null);
    }

    @ApiOperation("删除字典数据")
    @DeleteMapping("/data/{id}")
    public Result<Void> deleteDictData(@PathVariable Long id) {
        dictService.deleteDictData(id);
        return Result.success("删除成功", null);
    }

    @ApiOperation("批量删除字典数据")
    @DeleteMapping("/data/batch")
    public Result<Void> batchDeleteDictData(@RequestBody List<Long> ids) {
        dictService.batchDeleteDictData(ids);
        return Result.success("删除成功", null);
    }

    @ApiOperation("根据字典类型获取字典数据")
    @GetMapping("/data/type/{dictType}")
    public Result<List<DictDataVO>> getDictDataByType(@PathVariable String dictType) {
        List<DictDataVO> dictDataList = dictService.getDictDataByType(dictType);
        return Result.success(dictDataList);
    }
}
