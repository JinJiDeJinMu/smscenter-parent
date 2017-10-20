/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 网关队列Entity
 * @author zhukc
 * @version 2017-07-13
 */
public class SmsGatewayQueueid implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String gwCode;			// 网关编号
	private String serviceid;		// 业务模型
	private String topic;			// 队列组
	private int queueId;			// 队列编号
	private String queueName;		// 队列名称
	private String queueStatus;		// 队列状态
	private String threadNum;		// 队列执行线程数
	private Long offset;			// 位置
	private String tps;				// 当前速率
	private Date createtime;		// 创建时间
	private String remark;			// 备注
	private int weight;				// 权重
	

	public String getGwCode() {
		return gwCode;
	}

	public void setGwCode(String gwCode) {
		this.gwCode = gwCode;
	}
	
	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public String getQueueStatus() {
		return queueStatus;
	}

	public void setQueueStatus(String queueStatus) {
		this.queueStatus = queueStatus;
	}
	
	public String getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(String threadNum) {
		this.threadNum = threadNum;
	}
	
	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}
	
	public String getTps() {
		return tps;
	}

	public void setTps(String tps) {
		this.tps = tps;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}