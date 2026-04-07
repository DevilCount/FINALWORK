package com.lis.statistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.statistics.entity.StatTestItemDailyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatTestItemDailyMapper extends BaseMapper<StatTestItemDailyDO> {

    @Select("<script>" +
            "SELECT stat_date, " +
            "       SUM(total_count) as total_count, " +
            "       SUM(normal_count) as normal_count, " +
            "       SUM(abnormal_count) as abnormal_count, " +
            "       SUM(panic_count) as panic_count, " +
            "       SUM(positive_count) as positive_count, " +
            "       SUM(negative_count) as negative_count, " +
            "       AVG(abnormal_rate) as abnormal_rate, " +
            "       AVG(positive_rate) as positive_rate " +
            "FROM stat_test_item_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if> " +
            "GROUP BY stat_date " +
            "ORDER BY stat_date" +
            "</script>")
    List<Map<String, Object>> selectDailyStat(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate,
                                               @Param("deptId") Long deptId);

    @Select("<script>" +
            "SELECT test_item_id, item_code, item_name, " +
            "       SUM(total_count) as total_count, " +
            "       SUM(normal_count) as normal_count, " +
            "       SUM(abnormal_count) as abnormal_count, " +
            "       SUM(panic_count) as panic_count, " +
            "       AVG(abnormal_rate) as abnormal_rate, " +
            "       AVG(positive_rate) as positive_rate " +
            "FROM stat_test_item_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='deptId != null'> AND dept_id = #{deptId}</if> " +
            "<if test='testItemId != null'> AND test_item_id = #{testItemId}</if> " +
            "GROUP BY test_item_id, item_code, item_name " +
            "ORDER BY total_count DESC" +
            "</script>")
    List<Map<String, Object>> selectItemStat(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate,
                                              @Param("deptId") Long deptId,
                                              @Param("testItemId") Long testItemId);

    @Select("<script>" +
            "SELECT dept_id, dept_name, " +
            "       SUM(total_count) as total_count, " +
            "       SUM(abnormal_count) as abnormal_count, " +
            "       SUM(panic_count) as panic_count " +
            "FROM stat_test_item_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY dept_id, dept_name " +
            "ORDER BY total_count DESC" +
            "</script>")
    List<Map<String, Object>> selectDeptStat(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);
}
