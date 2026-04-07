package com.lis.statistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.statistics.entity.StatWorkloadDailyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatWorkloadDailyMapper extends BaseMapper<StatWorkloadDailyDO> {

    @Select("<script>" +
            "SELECT stat_date, " +
            "       SUM(specimen_receive_count) as specimen_receive_count, " +
            "       SUM(test_count) as test_count, " +
            "       SUM(audit_count) as audit_count, " +
            "       SUM(report_count) as report_count, " +
            "       SUM(work_hours) as work_hours " +
            "FROM stat_workload_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if> " +
            "GROUP BY stat_date " +
            "ORDER BY stat_date" +
            "</script>")
    List<Map<String, Object>> selectDailyWorkload(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate,
                                                   @Param("deptId") Long deptId);

    @Select("<script>" +
            "SELECT user_id, user_name, dept_id, dept_name, " +
            "       SUM(specimen_receive_count) as specimen_receive_count, " +
            "       SUM(test_count) as test_count, " +
            "       SUM(audit_count) as audit_count, " +
            "       SUM(report_count) as report_count, " +
            "       SUM(specimen_receive_count + test_count + audit_count + report_count) as total_workload " +
            "FROM stat_workload_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if> " +
            "<if test='userId != null'> AND user_id = #{userId}</if> " +
            "GROUP BY user_id, user_name, dept_id, dept_name " +
            "ORDER BY total_workload DESC" +
            "</script>")
    List<Map<String, Object>> selectUserWorkload(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("deptId") Long deptId,
                                                  @Param("userId") Long userId);

    @Select("<script>" +
            "SELECT dept_id, dept_name, " +
            "       SUM(specimen_receive_count) as specimen_receive_count, " +
            "       SUM(test_count) as test_count, " +
            "       SUM(audit_count) as audit_count, " +
            "       SUM(report_count) as report_count " +
            "FROM stat_workload_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY dept_id, dept_name " +
            "ORDER BY specimen_receive_count + test_count + audit_count + report_count DESC" +
            "</script>")
    List<Map<String, Object>> selectDeptWorkload(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);
}
