/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 短信发送Entity
 * @author zhukc
 * @version 2017-06-26
 */
public class SmsSend implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;					// 主键ID
	private String accId;				// 账户ID
	private String userid;				// 用户ID
	private String feeType;				// 计费类型(提交、状态)
	private String feePayment;			// 计费方式(预付、后付)
	private String taskid;				// 任务ID
	private String customTaskid;		// 客户批次ID(折分短信以-进行拼接)
	private String customServiceid;		// custom_serviceid
	private String phone;				// 手机号码
	private String phoneType;			// 运营商
	private String phoneArea;			// 省市代码
	private String spnumber;			// 发送号码
	private String smsContent;			// 短信内容
	private String smsContentId;		// 短信素材ID
	private String smsContentSign;		// 短信签名
	private String smsType;				// 短信类型 1行业短信、2验证码、3、营销短信、4群发短信
	private int smsSize;				// 扣费条数
	private String sendStatus;			// 发送状态T成功 F失败 P处理中 R审核
	private String gatewayId;			// 网关编号
	private String gatewayGroup;		// 网关分组
	private String gatewayStatus;		// 网关状态
	private String sourceGatewayId;		// source_gateway_id
	private Date sendTime;				// 发送时间
	private Date submitTime;			// 提交时间 暂时不用
	private Date reportTime;			// report_time
	private Date createTime;			// create_time
	private String receiveTime;			// receive_time
	private String userNotifyStatus;	// 推送状态(0:无推,1:待推,2:成功,3:失败)
	private Date userNotifyTime;		// 推送时间
	
	private String tableName;
	private String historyName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHistoryName() {
		return historyName;
	}

	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SmsSend() {
		super();
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeePayment() {
		return feePayment;
	}

	public void setFeePayment(String feePayment) {
		this.feePayment = feePayment;
	}
	
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	public String getCustomTaskid() {
		return customTaskid;
	}

	public void setCustomTaskid(String customTaskid) {
		this.customTaskid = customTaskid;
	}
	
	public String getCustomServiceid() {
		return customServiceid;
	}

	public void setCustomServiceid(String customServiceid) {
		this.customServiceid = customServiceid;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	public String getPhoneArea() {
		return phoneArea;
	}

	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}
	
	public String getSpnumber() {
		return spnumber;
	}

	public void setSpnumber(String spnumber) {
		this.spnumber = spnumber;
	}
	
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	public String getSmsContentId() {
		return smsContentId;
	}

	public void setSmsContentId(String smsContentId) {
		this.smsContentId = smsContentId;
	}
	
	public String getSmsContentSign() {
		return smsContentSign;
	}

	public void setSmsContentSign(String smsContentSign) {
		this.smsContentSign = smsContentSign;
	}
	
	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	
	public int getSmsSize() {
		return smsSize;
	}

	public void setSmsSize(int smsSize) {
		this.smsSize = smsSize;
	}
	
	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	
	public String getGatewayGroup() {
		return gatewayGroup;
	}

	public void setGatewayGroup(String gatewayGroup) {
		this.gatewayGroup = gatewayGroup;
	}
	
	public String getGatewayStatus() {
		return gatewayStatus;
	}

	public void setGatewayStatus(String gatewayStatus) {
		this.gatewayStatus = gatewayStatus;
	}
	
	public String getSourceGatewayId() {
		return sourceGatewayId;
	}

	public void setSourceGatewayId(String sourceGatewayId) {
		this.sourceGatewayId = sourceGatewayId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public String getUserNotifyStatus() {
		return userNotifyStatus;
	}

	public void setUserNotifyStatus(String userNotifyStatus) {
		this.userNotifyStatus = userNotifyStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUserNotifyTime() {
		return userNotifyTime;
	}

	public void setUserNotifyTime(Date userNotifyTime) {
		this.userNotifyTime = userNotifyTime;
	}
	
}