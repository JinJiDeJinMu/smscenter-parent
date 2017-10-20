package com.sanerzone.smscenter.biz.message;

import java.io.Serializable;
import java.util.Date;

public class SMSMTMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SMSREQMessage smsREQMessage;
	private String id;
	private String spnumber;        // 发送号码
	private String phone;           // phone
    private String phoneType;       // 运营商
    private String phoneArea;       // 运营商
    private int    smsSize;         // 扣费条数
    private String smsContent;		// 短信内容
    private String smsContentSign;  // 短信签名
    private String contentType;		// 内容类型
    private String sendStatus;      // 发送状态
    private String gatewayId;       // 目标网关编号
    private String gatewayAppPort;  // 目标网关应用  8989、8988
    private String gatewayGroup;    // 目标网关分组  行业、验证码
    
    private String sendTopic;		//发送队列组
    private int    sendQueue;		//发送队列编号
    
    private Date bizTime;           // 业务处理时间，匹配号段网关花的时间
    private Date submitTime;        // 提交时间
    private Date reportTime;        // report_time
	
	private String serviceId;          //保留

	public SMSREQMessage getSmsREQMessage() {
		return smsREQMessage;
	}

	public void setSmsREQMessage(SMSREQMessage smsREQMessage) {
		this.smsREQMessage = smsREQMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSmsContentSign() {
		return smsContentSign;
	}

	public void setSmsContentSign(String smsContentSign) {
		this.smsContentSign = smsContentSign;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public String getGatewayAppPort() {
		return gatewayAppPort;
	}

	public void setGatewayAppPort(String gatewayAppPort) {
		this.gatewayAppPort = gatewayAppPort;
	}

	public String getGatewayGroup() {
		return gatewayGroup;
	}

	public void setGatewayGroup(String gatewayGroup) {
		this.gatewayGroup = gatewayGroup;
	}

	public Date getBizTime() {
		return bizTime;
	}

	public void setBizTime(Date bizTime) {
		this.bizTime = bizTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSpnumber() {
		return spnumber;
	}

	public void setSpnumber(String spnumber) {
		this.spnumber = spnumber;
	}
	
	public int getSmsSize() {
		return smsSize;
	}
	public void setSmsSize(int smsSize) {
		this.smsSize = smsSize;
	}

	public String getSendTopic() {
		return sendTopic;
	}

	public void setSendTopic(String sendTopic) {
		this.sendTopic = sendTopic;
	}

	public int getSendQueue() {
		return sendQueue;
	}

	public void setSendQueue(int sendQueue) {
		this.sendQueue = sendQueue;
	}

	
}
