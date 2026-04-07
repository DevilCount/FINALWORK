package com.lis.statistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.statistics.entity.StatEquipmentDailyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatEquipmentDailyMapper extends BaseMapper<StatEquipmentDailyDO> {

    @Select("<script>" +
            "SELECT stat_date, " +
            "       SUM(test_count) as test_count, " +
            "       SUM(test_item_count) as test_item_count, " +
            "       SUM(qc_count) as qc_count, " +
            "       SUM(qc_pass_count) as qc_pass_count, " +
            "       SUM(qc_fail_count) as qc_fail_count, " +
            "       AVG(qc_pass_rate) as qc_pass_rate, " +
            "       SUM(fault_count) as fault_count, " +
            "       AVG(uptime_rate) as uptime_rate " +
            "FROM stat_equipment_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY stat_date " +
            "ORDER BY stat_date" +
            "</script>")
    List<Map<String, Object>> selectDailyStat(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Select("<script>" +
            "SELECT equipment_id, equipment_code, equipment_name, " +
            "       SUM(test_count) as test_count, " +
            "       SUM(test_item_count) as test_item_count, " +
            "       SUM(qc_count) as qc_count, " +
            "       AVG(qc_pass_rate) as qc_pass_rate, " +
            "       SUM(fault_count) as fault_count, " +
            "       AVG(uptime_rate) as uptime_rate " +
            "FROM stat_equipment_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='equipmentId != null'> AND equipment_id = #{equipmentId}</if> " +
            "GROUP BY equipment_id, equipment_code, equipment_name " +
            "ORDER BY test_count DESC" +
            "</script>")
    List<Map<String, Object>> selectEquipmentStat(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate,
                                                   @Param("equipmentId") Long equipmentId);

    @Select("<script>" +
            "SELECT stat_date, equipment_id, equipment_name, " +
            "       test_count, qc_pass_rate, uptime_rate " +
            "FROM stat_equipment_daily " +
            "WHERE stat_date BETWEEN #{startDate} AND #{endDate} " +
            "<if test='equipmentId != null'> AND equipment_id = #{equipmentId}</if> " +
            "ORDER BY stat_date, equipment_id" +
            "</script>")
    List<Map<String, Object>> selectEquipmentTrend(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate,
                                                    @Param("equipmentId") Long equipmentId);
}
