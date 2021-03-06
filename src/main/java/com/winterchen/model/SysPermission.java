package com.winterchen.model;

import java.io.Serializable;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table sys_permission
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class SysPermission implements Serializable {
    /**
     * Database Column Remarks:
     *   菜单权限id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   父菜单权限id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.parent_id
     *
     * @mbg.generated
     */
    private Long parentId;

    /**
     * Database Column Remarks:
     *   菜单名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   菜单路径
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     * Database Column Remarks:
     *   是否叶子节点: 0:是 1:不是
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.is_leaf
     *
     * @mbg.generated
     */
    private Integer isLeaf;

    /**
     * Database Column Remarks:
     *   删除状态 0正常 1已删除
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_permission.del_flag
     *
     * @mbg.generated
     */
    private Integer delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_permission
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.id
     *
     * @return the value of sys_permission.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.id
     *
     * @param id the value for sys_permission.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.parent_id
     *
     * @return the value of sys_permission.parent_id
     *
     * @mbg.generated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.parent_id
     *
     * @param parentId the value for sys_permission.parent_id
     *
     * @mbg.generated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.name
     *
     * @return the value of sys_permission.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.name
     *
     * @param name the value for sys_permission.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.url
     *
     * @return the value of sys_permission.url
     *
     * @mbg.generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.url
     *
     * @param url the value for sys_permission.url
     *
     * @mbg.generated
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.is_leaf
     *
     * @return the value of sys_permission.is_leaf
     *
     * @mbg.generated
     */
    public Integer getIsLeaf() {
        return isLeaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.is_leaf
     *
     * @param isLeaf the value for sys_permission.is_leaf
     *
     * @mbg.generated
     */
    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_permission.del_flag
     *
     * @return the value of sys_permission.del_flag
     *
     * @mbg.generated
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_permission.del_flag
     *
     * @param delFlag the value for sys_permission.del_flag
     *
     * @mbg.generated
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}