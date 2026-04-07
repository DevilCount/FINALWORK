package com.lis.report.service;

import com.lis.common.result.PageResult;
import com.lis.report.dto.PanicValueHandleDTO;
import com.lis.report.dto.PanicValueQueryDTO;
import com.lis.report.entity.ReportItemDO;
import com.lis.report.vo.PanicValueVO;

public interface PanicValueService {

    void checkAndCreatePanicValue(Long reportId, ReportItemDO itemDO);

    void notifyPanicValue(Long panicValueId, String notifyMethod, String receiveUserName);

    void handlePanicValue(PanicValueHandleDTO dto);

    void confirmPanicValue(Long panicValueId);

    PanicValueVO getPanicValueById(Long panicValueId);

    PageResult<PanicValueVO> queryPanicValues(PanicValueQueryDTO dto);
}
