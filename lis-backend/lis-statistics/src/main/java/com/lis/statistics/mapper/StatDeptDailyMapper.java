package com.lis.statistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.statistics.entity.StatDeptDailyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatDeptDailyMapper extends BaseMapper<StatDeptDailyDO> {

    @Select("<script>" +
            "SELECT stat_date, " +
            "       SUM(specimen_count) as specimen_count, " +
            "       SUM(test_item_count) as test_item_count, " +
            "       SUM(report_count) as report_count, " +
            "       SUM(panic_count) as panic_count, " +
            "       AVG(tat_avg) as tat_avg, " +
            "       AVG(tat_within_rate) as tat_within_rate " +
            "FROM stat_dept_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY stat_date " +
            "ORDER BY stat_date" +
            "</script>")
    List<Map<String, Object>> selectDailyStat(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Select("<script>" +
            "SELECT dept_id, dept_name, " +
            "       SUM(specimen_count) as specimen_count, " +
            "       SUM(test_item_count) as test_item_count, " +
            "       SUM(report_count) as report_count, " +
            "       SUM(panic_count) as panic_count, " +
            "       AVG(tat_avg) as tat_avg, " +
            "       AVG(tat_within_rate) as tat_within_rate " +
            "FROM stat_dept_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if> " +
            "GROUP BY dept_id, dept_name " +
            "ORDER BY specimen_count DESC" +
            "</script>")
    List<Map<String, Object>> selectDeptStat(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate,
                                              @Param("deptId") Long deptId);
}
