package com.lis.statistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.statistics.entity.StatSpecimenDailyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatSpecimenDailyMapper extends BaseMapper<StatSpecimenDailyDO> {

    @Select("<script>" +
            "SELECT stat_date, " +
            "       SUM(total_count) as total_count, " +
            "       SUM(received_count) as received_count, " +
            "       SUM(completed_count) as completed_count, " +
            "       SUM(rejected_count) as rejected_count, " +
            "       SUM(stat_count) as stat_count, " +
            "       AVG(tat_avg) as tat_avg, " +
            "       AVG(tat_within_rate) as tat_within_rate " +
            "FROM stat_specimen_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if> " +
            "<if test='specimenTypeId != null'> AND specimen_type_id = #{specimenTypeId}</if> " +
            "GROUP BY stat_date " +
            "ORDER BY stat_date" +
            "</script>")
    List<Map<String, Object>> selectDailyStat(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate,
                                               @Param("deptId") Long deptId,
                                               @Param("specimenTypeId") Long specimenTypeId);

    @Select("<script>" +
            "SELECT dept_id, dept_name, " +
            "       SUM(total_count) as total_count, " +
            "       SUM(received_count) as received_count, " +
            "       SUM(completed_count) as completed_count, " +
            "       SUM(rejected_count) as rejected_count " +
            "FROM stat_specimen_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='specimenTypeId != null'> AND specimen_type_id = #{specimenTypeId}</if> " +
            "GROUP BY dept_id, dept_name " +
            "ORDER BY total_count DESC" +
            "</script>")
    List<Map<String, Object>> selectDeptStat(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate,
                                              @Param("specimenTypeId") Long specimenTypeId);

    @Select("SELECT specimen_type_id, specimen_type_name, SUM(total_count) as total_count " +
            "FROM stat_specimen_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY specimen_type_id, specimen_type_name " +
            "ORDER BY total_count DESC")
    List<Map<String, Object>> selectTypeStat(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);
}
