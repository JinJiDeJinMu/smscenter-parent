/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.Dict;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 短信任务Entity
 * @author zhukc
 * @version 2017-06-26
 */
public class SmsTask extends DataEntity<SmsTask> {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private String taskid;				// taskid
	private String accId;				// 账户ID
	private String userid;				// 用户ID
	private String smsContent;			// 短信内容
	private int smsSize;				// 短信条数
	private String customTaskid;		// 客户批次ID
	private String customSpnumber;		// 客户扩展号
	private String taskType;			// 任务类型(0:普通、1:模板)
	private int phoneCount;				// 号码数量
	private Date sendDatetime;			// 发送时间
	private Date endDatetime;			// 结束时间
	private String sendStatus;			// 状态：-2审核不通过 -1审核中,1:待发送,2:发送中,3:发送完成,5:暂停,8:继续发送,9:停止发送
	private int sendCount;				// 发送总量
	private int successCount;			// 成功量
	private int failCount;				// 失败量
	private Date createDatetime;		// 创建时间
	private Date updateDatetime;		// 修改时间
	private int version;				// 版本号
	private int rowNumber;				// 暂停时发送的行数
	private String recheckUserId;		// 审核人
	private Date recheckTime;			// 审核时间
	private String recheckRemarks;		// 审核说明
	
	private Date sendDatetimeQ;			// 发送时间起
	private Date sendDatetimeZ;			// 发送时间止
	private String phones;				// 手机号
	
	private List<Dict> unsubType;
	
	public List<Dict> getUnsubType() {
		return unsubType;
	}

	public void setUnsubType(List<Dict> unsubType) {
		this.unsubType = unsubType;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getSendDatetimeQ() {
		return sendDatetimeQ;
	}

	public void setSendDatetimeQ(Date sendDatetimeQ) {
		this.sendDatetimeQ = sendDatetimeQ;
	}

	public Date getSendDatetimeZ() {
		return sendDatetimeZ;
	}

	public void setSendDatetimeZ(Date sendDatetimeZ) {
		this.sendDatetimeZ = sendDatetimeZ;
	}

	public SmsTask() {
		super();
	}

	public SmsTask(String id){
		super(id);
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
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
	
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	public int getSmsSize() {
		return smsSize;
	}

	public void setSmsSize(int smsSize) {
		this.smsSize = smsSize;
	}
	
	public String getCustomTaskid() {
		return customTaskid;
	}

	public void setCustomTaskid(String customTaskid) {
		this.customTaskid = customTaskid;
	}
	
	public String getCustomSpnumber() {
		return customSpnumber;
	}

	public void setCustomSpnumber(String customSpnumber) {
		this.customSpnumber = customSpnumber;
	}
	
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public int getPhoneCount() {
		return phoneCount;
	}

	public void setPhoneCount(int phoneCount) {
		this.phoneCount = phoneCount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendDatetime() {
		return sendDatetime;
	}

	public void setSendDatetime(Date sendDatetime) {
		this.sendDatetime = sendDatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
	
	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public String getRecheckUserId() {
		return recheckUserId;
	}

	public void setRecheckUserId(String recheckUserId) {
		this.recheckUserId = recheckUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRecheckTime() {
		return recheckTime;
	}

	public void setRecheckTime(Date recheckTime) {
		this.recheckTime = recheckTime;
	}
	
	public String getRecheckRemarks() {
		return recheckRemarks;
	}

	public void setRecheckRemarks(String recheckRemarks) {
		this.recheckRemarks = recheckRemarks;
	}
	
}