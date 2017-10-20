/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 网关状态Entity
 * @author zhukc
 * @version 2017-06-26
 */
public class SmsReport extends DataEntity<SmsReport> {
	
	private static final long serialVersionUID = 1L;
	private String gatewayId;		// gateway_id
	private String accId;		// acc_id
	private String userid;		// 用户ID
	private String taskid;		// 任务ID（对应sms_task中的taskid）
	private String bizid;		// 业务ID（对应sms_send中的id）
	private String msgid;		// 网关ID
	private String stat;		// 接收状态
	private String spnumber;		// 发送号码
	private String phone;		// 接收号码
	private Long submitTime;		// 提交时间
	private Long doneTime;		// 接收时间
	private String gatewaySequence;		// 网关侧序号
	private Date createtime;		// 创建时间
	private Date reportReceiveTime;		// report_receive_time
	
	public SmsReport() {
		super();
	}

	public SmsReport(String id){
		super(id);
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
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
	
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	
	public String getBizid() {
		return bizid;
	}

	public void setBizid(String bizid) {
		this.bizid = bizid;
	}
	
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	public String getSpnumber() {
		return spnumber;
	}

	public void setSpnumber(String spnumber) {
		this.spnumber = spnumber;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Long getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Long submitTime) {
		this.submitTime = submitTime;
	}
	
	public Long getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Long doneTime) {
		this.doneTime = doneTime;
	}
	
	public String getGatewaySequence() {
		return gatewaySequence;
	}

	public void setGatewaySequence(String gatewaySequence) {
		this.gatewaySequence = gatewaySequence;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReportReceiveTime() {
		return reportReceiveTime;
	}

	public void setReportReceiveTime(Date reportReceiveTime) {
		this.reportReceiveTime = reportReceiveTime;
	}
	
}