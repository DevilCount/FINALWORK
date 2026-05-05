package com.lis.specimen.service;

import com.lis.common.result.PageResult;
import com.lis.specimen.dto.*;
import com.lis.specimen.vo.*;

import java.util.List;

public interface SpecimenService {

    SpecimenDetailVO register(SpecimenRegisterDTO dto);

    SpecimenDetailVO getByBarcode(String barcode);

    SpecimenDetailVO getBySpecimenNo(String specimenNo);

    SpecimenDetailVO getById(Long id);

    PageResult<SpecimenListVO> queryList(SpecimenQueryDTO dto);

    void receive(SpecimenReceiveDTO dto);

    void storage(SpecimenStorageDTO dto);

    void updateStatus(SpecimenStatusDTO dto);

    void reject(SpecimenReceiveDTO dto);

    List<SpecimenTraceVO> getTraceList(Long specimenId);

    List<SpecimenTraceVO> getTraceListByNo(String specimenNo);

    SpecimenStatisticsVO getStatistics(SpecimenStatisticsDTO dto);

    void addition(SpecimenAdditionDTO dto);

    void printBarcode(Long specimenId);

    List<SpecimenTypeVO> listSpecimenTypes();

    List<TestItemVO> listTestItems();
}
