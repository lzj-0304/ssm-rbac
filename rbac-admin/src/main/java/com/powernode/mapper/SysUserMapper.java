package com.powernode.mapper;

import com.powernode.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date:   2023/3/7
 */
public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);


    SysUser login(@Param("username") String username,@Param("password") String password);

    List<String> queryUserHasPermissions(@Param("userId") Long userId);

    List<SysUser> getByPage(@Param("params") Map<String,Object> params);

    SysUser queryUserByUserName(@Param("userName") String userName);
}