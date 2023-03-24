package com.powernode.mapper;

import com.powernode.domain.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/14
 */
public interface SysUserRoleMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    Long countUserRolesByUserId(@Param("userId") Long userId);

    int deleteUserRolesByUserId(@Param("userId")Long userId);

    int saveUserRolesBatch(List<SysUserRole> list);

    List<Long> getRoleIdsByUserId(@Param("userId") Long userId);
}