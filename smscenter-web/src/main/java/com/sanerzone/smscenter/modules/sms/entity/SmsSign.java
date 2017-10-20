/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import java.util.List;

import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.common.utils.excel.annotation.ExcelField;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 通道签名Entity
 * @author zhukc
 * @version 2016-07-29
 */
public class SmsSign extends DataEntity<SmsSign> {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private String userId;          // 用户ID
	private String gwCode;		//
	private String gatewayName;		// 通道
	private String sign;			// 签名
	private String spNumber;		// 接入号
	private String extNumber;       // 扩展号
	private Date createTime;
	private Date createTimeQ;
	private Date createTimeZ;	
	private List<SmsSign> gatewaySignList;
	private String note;
	public List<SmsSign> getGatewaySignList() {
		return gatewaySignList;
	}

	public void setGatewaySignList(List<SmsSign> gatewaySignList) {
		this.gatewaySignList = gatewaySignList;
	}

	public Date getCreateTimeQ()
    {
        return createTimeQ;
    }

    public void setCreateTimeQ(Date createTimeQ)
    {
        this.createTimeQ = createTimeQ;
    }

    public Date getCreateTimeZ()
    {
        return createTimeZ;
    }

    public void setCreateTimeZ(Date createTimeZ)
    {
        this.createTimeZ = createTimeZ;
    }

    @ExcelField(title="用户ID", align=2, sort=1)
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getCreateTime()
    {
        return createTime;
    }
    
    @ExcelField(title="通道代码", align=2, sort=20)
    public String getGwCode() {
		return gwCode;
	}

	public void setGwCode(String gwCode) {
		this.gwCode = gwCode;
	}

	public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @ExcelField(title="备注", align=2, sort=45)
    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getExtNumber()
    {
        return extNumber;
    }

    public void setExtNumber(String extNumber)
    {
        this.extNumber = extNumber;
    }

    public SmsSign() {
		super();
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public SmsSign(String id){
		super(id);
	}

	
	@ExcelField(title="签名", align=2, sort=40)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@ExcelField(title="扩展号", align=2, sort=25)
	public String getSpNumber() {
		return spNumber;
	}

	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}
	
}