/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.Office;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 日报表Entity
 * @author zhukc
 * @version 2017-06-26
 */
public class SmsDayReport extends DataEntity<SmsDayReport> {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private Date day;					// 日期
	private String accId;				// 账户ID
	private String accName;				// 账户名称
	private String gatewayId;			// 通道ID
	private String gatewayName;			// 通道名称;
	private String phoneType;			// 运营商
	private String userid;				// 用户ID
	private int sendCount;				// 日总发送量
	private int sendFailCount;			// 发送失败
	private int failCount;				// 系统处理失败量
	private int successCount;			// 发送成功量 暂时不用置0
	private int submitSuccessCount;		// 网关成功量
	private int submitFailCount;		// 网关失败量
	private int reportSuccessCount;		// 状态成功量
	private int reportNullCount;		// 无状态返回量
	private int reportFailCount;		// 状态报告失败量
	private int userCount;				// 用户扣费条数
	private int backCount;				// 返充代理条数
	private int userBackCount;			// 返充用户条数
	private String backFlag;			// 返充状态 1:已返充 0:未返充
	private Date backDatetime;			// back_datetime
	private Date updateDatetime;		// 更新时间
	private int pushSuccessCount;		// 推送成功
	private int pushFailCount;			// 推送失败
	private int pushUnkownCount;		// 推送未知
	
	private Date dayQ;		// 日期
	private Date dayZ;		// 日期
	private String queryType;
	private String tableName;
	private Office company;
    private String companyName;//公司名称
    private String companyId;
    
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public Office getCompany() {
		return company;
	}
	public void setCompany(Office company) {
		this.company = company;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public int getSendFailCount() {
		return sendFailCount;
	}
	public void setSendFailCount(int sendFailCount) {
		this.sendFailCount = sendFailCount;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getGatewayName() {
		return gatewayName;
	}
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
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
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getBackCount() {
		return backCount;
	}
	public void setBackCount(int backCount) {
		this.backCount = backCount;
	}
	public int getUserBackCount() {
		return userBackCount;
	}
	public void setUserBackCount(int userBackCount) {
		this.userBackCount = userBackCount;
	}
	public String getBackFlag() {
		return backFlag;
	}
	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}
	public Date getBackDatetime() {
		return backDatetime;
	}
	public void setBackDatetime(Date backDatetime) {
		this.backDatetime = backDatetime;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
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
	public Date getDayQ() {
		return dayQ;
	}
	public void setDayQ(Date dayQ) {
		this.dayQ = dayQ;
	}
	public Date getDayZ() {
		return dayZ;
	}
	public void setDayZ(Date dayZ) {
		this.dayZ = dayZ;
	}
	
}