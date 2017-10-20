package com.sanerzone.smscenter.common.utils;

import java.util.List;

import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.smscenter.modules.sms.dao.SmsGatewayDao;
import com.sanerzone.smscenter.modules.sms.dao.SmsGatewayGroupDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsGateway;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroup;

//通道tag工具类
public class GatewayTagUtils {

	private static SmsGatewayGroupDao smsGatewayGroupDao = SpringContextHolder.getBean(SmsGatewayGroupDao.class);
	
	private static SmsGatewayDao smsGatewayDao = SpringContextHolder.getBean(SmsGatewayDao.class);
	
	//获取通道分组
	public static List<SmsGatewayGroup> getGatewayGroupList(){
		SmsGatewayGroup entity = new SmsGatewayGroup();
		entity.setStatus("1");
		return smsGatewayGroupDao.findList(entity);
	}
	
	//获取通道信息
	public static List<SmsGateway> getGatewayList(){
		SmsGateway entity = new SmsGateway();
		entity.setGwStatus("1");
		return smsGatewayDao.findList(entity);
	}
}

