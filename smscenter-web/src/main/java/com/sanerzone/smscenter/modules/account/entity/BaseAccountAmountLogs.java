/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.Office;
import com.sanerzone.jeebase.modules.sys.entity.User;
/**
 * 账务变动日志Entity
 * @author zhukc
 * @version 2017-03-03
 */
public class BaseAccountAmountLogs extends DataEntity<BaseAccountAmountLogs> {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private String accId;		// acc_id
	private String userid;		// userid
	private String accType;		// acc_type
	private String accAmount;	// 变动前余额
	private String amount;		// 变动额度
	private String changeType;	// change_type
	private String payment;		// 支付方式
	private String payid;		// payid
	private String remark;		// 备注
	private Date createTime;	// create_time
	private Date createTimeQ;
	private Date createTimeZ;
	private String reserve;		// 保留字段
	private String serviceId;	// 业务类型
	private String transId;		// 交易ID
	
	private Office company;
	
	public BaseAccountAmountLogs() {
		super();
	}

	public BaseAccountAmountLogs(String id){
		super(id);
	}
	
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateTimeQ() {
		return createTimeQ;
	}

	public void setCreateTimeQ(Date createTimeQ) {
		this.createTimeQ = createTimeQ;
	}

	public Date getCreateTimeZ() {
		return createTimeZ;
	}

	public void setCreateTimeZ(Date createTimeZ) {
		this.createTimeZ = createTimeZ;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(String accAmount) {
		this.accAmount = accAmount;
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
	
	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
}