package com.winterchen.easemob.bussiness.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.winterchen.utilCommon.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = TeamModel.TABLE_NAME)
@ApiModel("组队信息")
public class TeamModel {
    protected static final String TABLE_NAME = "team";
    @Id
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("店家ID")
    @Column(name = "shop_id")
    private Long shopId;

    @ApiModelProperty("发起人ID")
    private Long owner;

    /**
     * 活动开始时间
     */
    @ApiModelProperty("活动开始时间")
    @Column(name = "start_time")
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN, timezone = "GMT+8")
    @Excel(name="活动开始时间",exportFormat = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * 组队类型：:0：组队店家;1:体验店家
     */
    @ApiModelProperty(" 组队类型：:0：组队店家;1:体验店家")
    private Integer type;

    /**
     * 组队状态;0：有效，1：无效
     */
    @ApiModelProperty("组队状态;0：有效，1：无效")
    private Integer status;

    /**
     * 0：组队中；1：组队成功；2：组队失败
     */
    @ApiModelProperty("0：组队中；1：组队成功；2：组队失败")
    private Integer result;

    /**
     * 参加人数限制
     */
    @Excel(name="限制人数")
    @ApiModelProperty("限制人数")
    private Integer number;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN, timezone = "GMT+8")
    private Date updateTime;

    /**
     * 支付方式：0：AA，1：组队发起者请客 2：女生免费；3：赢家免费 4:女生半价 5：赢家半价
     */
    @Excel(name="支付类型",replace = {"AA_0","邀约者请客_1","女生免费_2","赢家免费_3","女生半价_4","赢家半价_5"})
    @ApiModelProperty("支付方式 0：AA，1：组队发起者请客 2：女生免费；3：赢家免费 4:女生半价 5：赢家半价")
    @Column(name = "pay_way")
    private Integer payWay;

    /**
     * 参加人数限制
     */
    @Excel(name="希望组队成员性别比例：0：约1:1   1：女>0  2随便")
    @ApiModelProperty("希望组队成员性别比例：0：约1:1   1：女>0  2随便")
    @Column(name = "sex_want")
    private Integer sexWant;
    
    @ApiModelProperty("组队时自动创建聊天群")
    @Column(name = "chat_group_id")
    private String chatGroupId;

	/**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return shop_id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * @param shopId
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * @return owner
     */
    public Long getOwner() {
        return owner;
    }

    /**
     * @param owner
     */
    public void setOwner(Long owner) {
        this.owner = owner;
    }

    /**
     * 获取活动开始时间
     *
     * @return start_time - 活动开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置活动开始时间
     *
     * @param startTime 活动开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取组队类型：:0：组队店家;1:体验店家
     *
     * @return type - 组队类型：:0：组队店家;1:体验店家
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置组队类型：:0：组队店家;1:体验店家
     *
     * @param type 组队类型：:0：组队店家;1:体验店家
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取组队状态;0：有效，1：无效
     *
     * @return status - 组队状态;0：有效，1：无效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置组队状态;0：有效，1：无效
     *
     * @param status 组队状态;0：有效，1：无效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取0：组队中；1：组队成功；2：组队失败
     *
     * @return result - 0：组队中；1：组队成功；2：组队失败
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 设置0：组队中；1：组队成功；2：组队失败
     *
     * @param result 0：组队中；1：组队成功；2：组队失败
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Integer getSexWant() {
        return sexWant;
    }

    public void setSexWant(Integer sexWant) {
        this.sexWant = sexWant;
    }
    
    public String getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(String chatGroupId) {
		this.chatGroupId = chatGroupId;
	}
}