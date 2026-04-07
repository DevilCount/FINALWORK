package com.lis.specimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.specimen.entity.SpecimenDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface SpecimenMapper extends BaseMapper<SpecimenDO> {

    @Select("SELECT COUNT(*) FROM specimen WHERE DATE(create_time) = CURDATE()")
    Long countToday();

    @Select("SELECT COUNT(*) FROM specimen WHERE status = #{status}")
    Long countByStatus(@Param("status") String status);

    @Select("SELECT COUNT(*) FROM specimen WHERE DATE(collect_time) = CURDATE()")
    Long countTodayCollected();

    @Select("SELECT COUNT(*) FROM specimen WHERE DATE(receive_time) = CURDATE()")
    Long countTodayReceived();

    @Select("SELECT COUNT(*) FROM specimen WHERE is_stat = 1")
    Long countStat();

    @Select("SELECT COUNT(*) FROM specimen WHERE DATE(create_time) = CURDATE() AND dept_id = #{deptId}")
    Long countTodayByDept(@Param("deptId") Long deptId);

    @Select("SELECT COUNT(*) FROM specimen WHERE create_time BETWEEN #{startTime} AND #{endTime}")
    Long countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
