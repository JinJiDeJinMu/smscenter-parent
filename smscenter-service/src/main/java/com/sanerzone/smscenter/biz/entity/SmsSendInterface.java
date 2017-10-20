package com.sanerzone.smscenter.biz.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhukc
 * @since 2017-07-26
 */
@TableName("sms_send_interface")
public class SmsSendInterface extends Model<SmsSendInterface> {

    private static final long serialVersionUID = 1L;
    
    private Long maxId;//最大的发送ID

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户ID
     */
	private String userid;
    /**
     * 接收号码
     */
	private String phone;
    /**
     * 发送号码
     */
	@TableField("send_phone")
	private String sendPhone;
    /**
     * 短信内容
     */
	@TableField("sms_content")
	private String smsContent;
    /**
     * 业务ID(催缴 账单通知 …)
     */
	private String serviceid;
    /**
     * 发送状态
     */
	@TableField("send_status")
	private Integer sendStatus;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	
	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
