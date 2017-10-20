package com.sanerzone.smscenter.biz.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XuRui
 * @since 2017-07-14
 */
@TableName("sms_task_data")
public class SmsTaskData extends Model<SmsTaskData> {

    private static final long serialVersionUID = 1L;

	private String taskid;
	@TableField("task_data")
	private String taskData;
	@TableField("send_time")
	private Date sendTime;
	@TableField("create_time")
	private Date createTime;


	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskData() {
		return taskData;
	}

	public void setTaskData(String taskData) {
		this.taskData = taskData;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.taskid;
	}

}
