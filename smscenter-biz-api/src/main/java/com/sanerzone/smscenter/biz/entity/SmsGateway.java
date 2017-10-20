/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 通道信息Entity
 * @author zhukc
 * @version 2017-06-29
 */
public class SmsGateway implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;					// ID
	private String gwCode;				// 网关编号
	private String appCode;				// 应用编号
	private String gwName;				// 网关名称
	private String gwRemark;			// 备注
	private int gwStatus;			    // 网关状态(0停止1运行中2禁用)
	private String gwType;				// 网关类型(全网:QW, 移动:YD, 联通:LT, 电信:DX)
	private String gwProto;				// 网关协议(CMPP,SMGP,SGIP,HTTP)
	private String gwProtoVersion;		// 协议版本(30,20)
	private String gwProtoClass;		// 协议实现类
	private String gwProtoExtparam;		// 协议扩展参数
	private String gwServerIp;			// 服务器IP
	private String gwServerPort;		// 服务器端口
	private String gwLocalIp;			// 本地IP
	private String gwLocalPort;			// 本地端口
	private String gwUsername;			// 网关用户名
	private String gwPassword;			// 网关密码
	private String gwSpNumber;			// 网关接入号
	private String gwCorpId;			// 网关企业代码
	private String gwServiceId;			// 网关服务代码
	private String gwReceiveModel;		// 状态报告获取模式(0被动1主动)
	private String smsSignModel;		// 短信签名模式(0自定义、1强制签名)
	private String smsSignNose;			// 短信签名端（0本地、1网关）
	private String gwLongsms;			// 是否支持长短信(0支持1不支持)
	private String gwOnceSubmit;		// 一次提交数量
	private String gwSendRate;			// 发送速率
	private String gwReceiveRate;		// 接收速率
	private String gwConnections;		// 网关连接数
	private Date createTime;			// 创建时间
	private Date modifyTime;			// 更新时间
	private String modifyBy;			// 更新人
	private String modifyRemark;		// 更新备注
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGwCode() {
		return gwCode;
	}

	public void setGwCode(String gwCode) {
		this.gwCode = gwCode;
	}
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	public String getGwName() {
		return gwName;
	}

	public void setGwName(String gwName) {
		this.gwName = gwName;
	}
	
	public String getGwRemark() {
		return gwRemark;
	}

	public void setGwRemark(String gwRemark) {
		this.gwRemark = gwRemark;
	}
	
	public int getGwStatus() {
		return gwStatus;
	}

	public void setGwStatus(int gwStatus) {
		this.gwStatus = gwStatus;
	}
	
	public String getGwType() {
		return gwType;
	}

	public void setGwType(String gwType) {
		this.gwType = gwType;
	}
	
	public String getGwProto() {
		return gwProto;
	}

	public void setGwProto(String gwProto) {
		this.gwProto = gwProto;
	}
	
	public String getGwProtoVersion() {
		return gwProtoVersion;
	}

	public void setGwProtoVersion(String gwProtoVersion) {
		this.gwProtoVersion = gwProtoVersion;
	}
	
	public String getGwProtoClass() {
		return gwProtoClass;
	}

	public void setGwProtoClass(String gwProtoClass) {
		this.gwProtoClass = gwProtoClass;
	}
	
	public String getGwProtoExtparam() {
		return gwProtoExtparam;
	}

	public void setGwProtoExtparam(String gwProtoExtparam) {
		this.gwProtoExtparam = gwProtoExtparam;
	}
	
	public String getGwServerIp() {
		return gwServerIp;
	}

	public void setGwServerIp(String gwServerIp) {
		this.gwServerIp = gwServerIp;
	}
	
	public String getGwServerPort() {
		return gwServerPort;
	}

	public void setGwServerPort(String gwServerPort) {
		this.gwServerPort = gwServerPort;
	}
	
	public String getGwLocalIp() {
		return gwLocalIp;
	}

	public void setGwLocalIp(String gwLocalIp) {
		this.gwLocalIp = gwLocalIp;
	}
	
	public String getGwLocalPort() {
		return gwLocalPort;
	}

	public void setGwLocalPort(String gwLocalPort) {
		this.gwLocalPort = gwLocalPort;
	}
	
	public String getGwUsername() {
		return gwUsername;
	}

	public void setGwUsername(String gwUsername) {
		this.gwUsername = gwUsername;
	}
	
	public String getGwPassword() {
		return gwPassword;
	}

	public void setGwPassword(String gwPassword) {
		this.gwPassword = gwPassword;
	}
	
	public String getGwSpNumber() {
		return gwSpNumber;
	}

	public void setGwSpNumber(String gwSpNumber) {
		this.gwSpNumber = gwSpNumber;
	}
	
	public String getGwCorpId() {
		return gwCorpId;
	}

	public void setGwCorpId(String gwCorpId) {
		this.gwCorpId = gwCorpId;
	}
	
	public String getGwServiceId() {
		return gwServiceId;
	}

	public void setGwServiceId(String gwServiceId) {
		this.gwServiceId = gwServiceId;
	}
	
	public String getGwReceiveModel() {
		return gwReceiveModel;
	}

	public void setGwReceiveModel(String gwReceiveModel) {
		this.gwReceiveModel = gwReceiveModel;
	}
	
	public String getSmsSignModel() {
		return smsSignModel;
	}

	public void setSmsSignModel(String smsSignModel) {
		this.smsSignModel = smsSignModel;
	}
	
	public String getSmsSignNose() {
		return smsSignNose;
	}

	public void setSmsSignNose(String smsSignNose) {
		this.smsSignNose = smsSignNose;
	}
	
	public String getGwLongsms() {
		return gwLongsms;
	}

	public void setGwLongsms(String gwLongsms) {
		this.gwLongsms = gwLongsms;
	}
	
	public String getGwOnceSubmit() {
		return gwOnceSubmit;
	}

	public void setGwOnceSubmit(String gwOnceSubmit) {
		this.gwOnceSubmit = gwOnceSubmit;
	}
	
	public String getGwSendRate() {
		return gwSendRate;
	}

	public void setGwSendRate(String gwSendRate) {
		this.gwSendRate = gwSendRate;
	}
	
	public String getGwReceiveRate() {
		return gwReceiveRate;
	}

	public void setGwReceiveRate(String gwReceiveRate) {
		this.gwReceiveRate = gwReceiveRate;
	}
	
	public String getGwConnections() {
		return gwConnections;
	}

	public void setGwConnections(String gwConnections) {
		this.gwConnections = gwConnections;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	
	public String getModifyRemark() {
		return modifyRemark;
	}

	public void setModifyRemark(String modifyRemark) {
		this.modifyRemark = modifyRemark;
	}
	
}