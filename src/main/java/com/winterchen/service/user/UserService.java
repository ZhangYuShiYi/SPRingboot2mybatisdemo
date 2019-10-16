package com.winterchen.service.user;

import com.github.pagehelper.PageInfo;
import com.winterchen.model.SysUser;
import com.winterchen.model.UserDomain;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by zy on 2019/8/2.
 */
public interface UserService {

    int addUser(UserDomain user);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);

    UserDomain findUserById(Integer userId);

    SysUser findByUserName(String userName);

    Set<String> getUserRolesSet(String userName);

    Set<String> getUserPermissionsSet(String userName);

    LinkedList<String> getUserApiList(String userName);

    List<SysUser> getUserList();

    int saveSysUser(SysUser sysUser);

}
