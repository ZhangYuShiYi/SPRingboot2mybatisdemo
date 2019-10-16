package com.winterchen.dao;

import com.winterchen.model.SysUser;
import com.winterchen.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by zy on 2019/8/2.
 */

@Mapper
public interface UserDao {

    int insert(UserDomain record);

    List<UserDomain> selectUsers();

    UserDomain findUserById(@Param("userId") Integer userId);

    SysUser findByUserName(@Param("userName") String userName);

    List<String> getUserRolesSet(@Param("userName") String userName);

    List<String> getUserPermissionsSet(@Param("userName") String userName);

    LinkedList<String> getUserApiList(@Param("userName") String userName);

    List<SysUser> getUserList();

    int saveSysUser(SysUser sysUser);
}
