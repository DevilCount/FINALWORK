package com.lis.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.report.entity.PatientDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper extends BaseMapper<PatientDO> {
}
