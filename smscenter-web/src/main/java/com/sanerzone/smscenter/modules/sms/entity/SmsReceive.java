/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 上行短信记录Entity
 * @author zhukc
 * @version 2017-06-26
 */
public class SmsReceive extends DataEntity<SmsReceive> {
	
	private static final long serialVersionUID = 1L;
	private String accId;			// 账号ID
	private String userid;			// 用户ID
	private String msgid;			// 消息流水号
	private String spnumber;		// 接入号
	private String phone;			// 上行手机号
	private String smsContent;		// 短信内容
	private String gatewayId;		// gateway_id
	private Date createtime;		// 创建时间
	private String result;			// 结果
	private String pushFlag;		// 推送标识(1:推送 0:不用推送)
	
	private Date createtimeQ;		// 创建时间
	private Date createtimeZ;		// 创建时间
	
	private String tableName;		//表名
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getCreatetimeQ() {
		return createtimeQ;
	}

	public void setCreatetimeQ(Date createtimeQ) {
		this.createtimeQ = createtimeQ;
	}

	public Date getCreatetimeZ() {
		return createtimeZ;
	}

	public void setCreatetimeZ(Date createtimeZ) {
		this.createtimeZ = createtimeZ;
	}

	public SmsReceive() {
		super();
	}

	public SmsReceive(String id){
		super(id);
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
	
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
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
	
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}
	
}