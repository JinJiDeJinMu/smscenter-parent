package com.sanerzone.smscenter.biz.message;

import java.io.Serializable;

public class SMSMOMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uuid;		//随机ID	
	private String msgid; 		//消息流水号
	private String phone; 	    //上行手机号
	private String spnumber; 	//spnumber
	private String msgContent; 	//上行内容
	private String gateWayID; 	//网关编号

	public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getSpnumber()
    {
        return spnumber;
    }

    public void setSpnumber(String spnumber)
    {
        this.spnumber = spnumber;
    }

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGateWayID() {
		return gateWayID;
	}

	public void setGateWayID(String gateWayID) {
		this.gateWayID = gateWayID;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

}
