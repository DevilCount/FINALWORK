package com.lis.hl7.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lis.hl7.dto.InterfaceConfigDTO;
import com.lis.hl7.dto.InterfaceConfigQueryDTO;
import com.lis.hl7.entity.InterfaceConfigDO;
import com.lis.hl7.vo.InterfaceConfigVO;

public interface InterfaceConfigService extends IService<InterfaceConfigDO> {

    InterfaceConfigDO getByCode(String interfaceCode);

    Page<InterfaceConfigVO> queryPage(InterfaceConfigQueryDTO queryDTO);

    InterfaceConfigVO getDetail(Long id);

    Long create(InterfaceConfigDTO dto);

    void update(InterfaceConfigDTO dto);

    void delete(Long id);

    void updateStatus(Long id, String status);

    void incrementReceivedCount(Long id);

    void incrementSentCount(Long id);

    void incrementErrorCount(Long id, String errorMsg);

    boolean testConnection(Long id);

    void startInterface(Long id);

    void stopInterface(Long id);

    void restartInterface(Long id);
}
