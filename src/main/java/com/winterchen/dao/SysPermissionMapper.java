package com.winterchen.dao;

import com.winterchen.model.SysPermission;
import com.winterchen.model.SysPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    long countByExample(SysPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int deleteByExample(SysPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int insert(SysPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int insertSelective(SysPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    List<SysPermission> selectByExample(SysPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    SysPermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysPermission record);
}