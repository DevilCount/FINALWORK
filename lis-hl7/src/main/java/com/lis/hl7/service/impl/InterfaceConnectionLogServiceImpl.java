package com.lis.hl7.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lis.hl7.entity.InterfaceConnectionLogDO;
import com.lis.hl7.mapper.InterfaceConnectionLogMapper;
import com.lis.hl7.service.InterfaceConnectionLogService;
import org.springframework.stereotype.Service;

@Service
public class InterfaceConnectionLogServiceImpl extends ServiceImpl<InterfaceConnectionLogMapper, InterfaceConnectionLogDO> implements InterfaceConnectionLogService {}
