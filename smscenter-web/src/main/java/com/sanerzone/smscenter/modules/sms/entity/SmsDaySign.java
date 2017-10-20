/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 日签名报表Entity
 * @author huangjie
 * @version 2017-08-13
 */
public class SmsDaySign extends DataEntity<SmsDaySign> {
	
	private static final long serialVersionUID = 1L;
	private Date day;		// 日期
	private String accId;		// 账户ID
	private String sign;		// 签名
	private String userid;		// 用户ID
	private int sendCount;		// 日总发送量
	private int sendFailCount;	//发送失败量
	private int failCount;		// 系统处理失败量
	private int successCount;		// 发送成功量 暂时不用置0
	private int submitSuccessCount;		// 网关成功量
	private int submitFailCount;		// 网关失败量
	private int reportSuccessCount;		// 状态成功量
	private int reportNullCount;		// 无状态返回量
	private int reportFailCount;		// 状态报告失败量
	private int pushSuccessCount;		// 推送成功
	private int pushFailCount;		// 推送失败
	private int pushUnkownCount;		// 推送未知
	private Date updateDatetime;		// 更新时间
	private Date beginDay;		// 开始 日期
	private Date endDay;		// 结束 日期
	private String tableName;   //表名
	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
	
	public SmsDaySign() {
		super();
	}

	public SmsDaySign(String id){
		super(id);
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getSubmitSuccessCount() {
		return submitSuccessCount;
	}

	public void setSubmitSuccessCount(int submitSuccessCount) {
		this.submitSuccessCount = submitSuccessCount;
	}

	public int getSubmitFailCount() {
		return submitFailCount;
	}

	public void setSubmitFailCount(int submitFailCount) {
		this.submitFailCount = submitFailCount;
	}

	public int getReportSuccessCount() {
		return reportSuccessCount;
	}

	public void setReportSuccessCount(int reportSuccessCount) {
		this.reportSuccessCount = reportSuccessCount;
	}

	public int getReportNullCount() {
		return reportNullCount;
	}

	public void setReportNullCount(int reportNullCount) {
		this.reportNullCount = reportNullCount;
	}

	public int getReportFailCount() {
		return reportFailCount;
	}

	public void setReportFailCount(int reportFailCount) {
		this.reportFailCount = reportFailCount;
	}

	public int getPushSuccessCount() {
		return pushSuccessCount;
	}

	public void setPushSuccessCount(int pushSuccessCount) {
		this.pushSuccessCount = pushSuccessCount;
	}

	public int getPushFailCount() {
		return pushFailCount;
	}

	public void setPushFailCount(int pushFailCount) {
		this.pushFailCount = pushFailCount;
	}

	public int getPushUnkownCount() {
		return pushUnkownCount;
	}

	public void setPushUnkownCount(int pushUnkownCount) {
		this.pushUnkownCount = pushUnkownCount;
	}

	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public Date getBeginDay() {
		return beginDay;
	}

	public void setBeginDay(Date beginDay) {
		this.beginDay = beginDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getSendFailCount() {
		return sendFailCount;
	}

	public void setSendFailCount(int sendFailCount) {
		this.sendFailCount = sendFailCount;
	}

		
}