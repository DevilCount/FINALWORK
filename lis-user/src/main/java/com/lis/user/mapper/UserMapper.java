package com.lis.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lis.user.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    UserDO selectByUserName(@Param("userName") String userName);

    List<UserDO> selectUserList(@Param("deptId") Long deptId,
                                @Param("userName") String userName,
                                @Param("nickName") String nickName,
                                @Param("phone") String phone,
                                @Param("status") Integer status,
                                @Param("userType") String userType);

    int updatePassword(@Param("userId") Long userId, @Param("password") String password);

    int updateStatus(@Param("userId") Long userId, @Param("status") Integer status);

    int updateLoginInfo(@Param("userId") Long userId, @Param("loginIp") String loginIp);
}
