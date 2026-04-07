package com.lis.specimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.specimen.entity.SpecimenTraceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecimenTraceMapper extends BaseMapper<SpecimenTraceDO> {

    @Select("SELECT * FROM specimen_trace WHERE specimen_id = #{specimenId} ORDER BY operate_time DESC")
    List<SpecimenTraceDO> selectBySpecimenId(@Param("specimenId") Long specimenId);

    @Select("SELECT * FROM specimen_trace WHERE specimen_no = #{specimenNo} ORDER BY operate_time DESC")
    List<SpecimenTraceDO> selectBySpecimenNo(@Param("specimenNo") String specimenNo);
}
