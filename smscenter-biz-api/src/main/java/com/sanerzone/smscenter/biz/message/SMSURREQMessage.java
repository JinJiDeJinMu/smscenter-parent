package com.sanerzone.smscenter.biz.message;

import java.io.Serializable;

public class SMSURREQMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String bizid;
	private String taskid;
	private String messageid;
	private String msgid;
	private String gateWayId;
	private String sourceGateWayId;
	private String spnumber;
	private String phone;
	private String stat;
	private String userid;
	private String accid;
	private String submitTime;
	private String doneTime;
	
	private String result;
	private String reserve;
	
	public SMSURREQMessage(){}

	public String getBizid() {
		return bizid;
	}

	public void setBizid(String bizid) {
		this.bizid = bizid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}

	public String getSourceGateWayId() {
		return sourceGateWayId;
	}

	public void setSourceGateWayId(String sourceGateWayId) {
		this.sourceGateWayId = sourceGateWayId;
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

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(String doneTime) {
		this.doneTime = doneTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	
}
