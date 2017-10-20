/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 用户发票明细Entity
 * @author zhukc
 * @version 2017-08-16
 */
public class BaseUserInvoiceLogs extends DataEntity<BaseUserInvoiceLogs> {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private String userid;				// 用户ID
	private Long amount;				// 开具发票金额
	private String invoiceNumber;		// 发票号
	private String headerInfo;			// 发票抬头信息
	private String invoiceType;			// 发票类型
	private String invoiceContent;		// 发票内容
	private String nsrsbh;				// 纳税人识别号
	private String bankNumber;			// 银行账号
	private String depositBank;			// 开户行
	private String regAddress;			// 注册地址
	private String phone;				// 企业电话
	private String collectAddress;		// 收取地址
	private String addressee;			// 收件人
	private String mobile;				// 手机号
	private String status;				// 状态(-1:索取发票 0:不开票 1:已处理)
	private String expressCompany;		// 快递公司
	private String expressNumber;		// 快递单号
	private String checkBy;				// 审核人
	private Date createTime;			// 申请开票时间
	private Date createTimeQ;			// 申请开票时间
	private Date createTimeZ;			// 申请开票时间
	private Date checkTime;				// 审核时间
	private String remark;				// 备注
	
	private String queryType;			//查询类型 history历史信息
	
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

	public BaseUserInvoiceLogs() {
		super();
	}

	public BaseUserInvoiceLogs(String id){
		super(id);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	public String getHeaderInfo() {
		return headerInfo;
	}

	public void setHeaderInfo(String headerInfo) {
		this.headerInfo = headerInfo;
	}
	
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	
	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}
	
	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	
	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCollectAddress() {
		return collectAddress;
	}

	public void setCollectAddress(String collectAddress) {
		this.collectAddress = collectAddress;
	}
	
	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	
	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}