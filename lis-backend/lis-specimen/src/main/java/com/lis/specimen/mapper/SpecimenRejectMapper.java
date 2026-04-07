package com.lis.specimen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.specimen.entity.SpecimenRejectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SpecimenRejectMapper extends BaseMapper<SpecimenRejectDO> {

    @Select("SELECT COUNT(*) FROM specimen_reject WHERE DATE(reject_time) = CURDATE()")
    Long countTodayRejected();
}
