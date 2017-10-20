/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.Office;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 账号信息Entity
 * @author zhukc
 * @version 2017-06-25
 */
public class BaseAccount extends DataEntity<BaseAccount> {
	
	private static final long serialVersionUID = 1L;
	private String userid;					// 用户ID
	private String accType;					// 帐户类型
	private String apikey;					// key
	private String serviceId;				// 业务类型(营销短信、行业短信)
	private String accName;					// 账号名称
	private String accStatus;				// 账户状态(0 禁用 1启用)
	private String accAmount;				// 账号额度
	private String accAmountVersion;		// 额度版本号(修改额度时+1)
	private String accFreeze;				// 冻结额度
	private String remark;					// 备注
	private String companyId;				// 公司ID
	private Date createTime;				// 创建时间
	private String opt_feeType;				// 计费方式
	private String changeType;				// 变动类型
	private String userType;				// 用户类型 0:系统 1:业务
	private Office company;
	private User user;
	
	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	private String callBackUrl;				//回调地址

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getOpt_feeType() {
		return opt_feeType;
	}

	public void setOpt_feeType(String opt_feeType) {
		this.opt_feeType = opt_feeType;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BaseAccount() {
		super();
	}

	public BaseAccount(String id){
		super(id);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}
	
	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}
	
	public String getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(String accAmount) {
		this.accAmount = accAmount;
	}
	
	public String getAccAmountVersion() {
		return accAmountVersion;
	}

	public void setAccAmountVersion(String accAmountVersion) {
		this.accAmountVersion = accAmountVersion;
	}
	
	public String getAccFreeze() {
		return accFreeze;
	}

	public void setAccFreeze(String accFreeze) {
		this.accFreeze = accFreeze;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}