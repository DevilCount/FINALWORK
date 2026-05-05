package com.lis.hl7.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.hl7.entity.Hl7MessageDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Hl7MessageMapper extends BaseMapper<Hl7MessageDO> {
}
