package com.lis.specimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.specimen.entity.SpecimenTestItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SpecimenTestItemMapper extends BaseMapper<SpecimenTestItemDO> {

    @Select("SELECT COUNT(*) FROM specimen_test_item WHERE specimen_id = #{specimenId}")
    Integer countBySpecimenId(@Param("specimenId") Long specimenId);

    @Select("SELECT COUNT(*) FROM specimen_test_item WHERE is_panic = 1")
    Long countPanic();

    @Select("SELECT COUNT(*) FROM specimen_test_item WHERE is_abnormal = 1")
    Long countAbnormal();

    @Select("SELECT * FROM specimen_test_item WHERE specimen_id = #{specimenId}")
    List<SpecimenTestItemDO> selectBySpecimenId(@Param("specimenId") Long specimenId);

    @Select("SELECT * FROM specimen_test_item WHERE specimen_no = #{specimenNo}")
    List<SpecimenTestItemDO> selectBySpecimenNo(@Param("specimenNo") String specimenNo);
}
