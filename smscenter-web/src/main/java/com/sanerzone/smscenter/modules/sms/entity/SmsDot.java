package com.sanerzone.smscenter.modules.sms.entity;

//短信普通点对点
public class SmsDot {
	
	private String phone;//手机号
	
	private String smsContent;//发送内容
	
	private String content;//发送预览
	
	private int count;//发送数量
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
	
}
