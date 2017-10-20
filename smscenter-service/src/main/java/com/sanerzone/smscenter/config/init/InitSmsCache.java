package com.sanerzone.smscenter.config.init;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sanerzone.smscenter.account.service.impl.AccountServiceImpl;
import com.sanerzone.smscenter.biz.listener.MQStartup;
import com.sanerzone.smscenter.biz.service.impl.SmsInitServiceImpl;

@Service
public class InitSmsCache {
	
	@Autowired
	private SmsInitServiceImpl smsInitService;
	
	@Autowired
	private AccountServiceImpl accountService;
	
	@Autowired
	private MQStartup startup;
	
	@PostConstruct
	public void init(){
		
		accountService.initAccountInfo();		//初始化账号属性
		accountService.initPubAccountInfo();    //初始化用户属性
//		accountService.initAccountInfoV2(); 	//初始化用户属性
		smsInitService.initTemplate();			//初始化短信模板
		smsInitService.initPhoneInfo();			//初始化号段
		smsInitService.initKeyword();			//初始化敏感词
		smsInitService.initSysBlacklist();		//初始化系统黑名单
		smsInitService.initUserBlacklist();		//初始化用户名单
		smsInitService.initWhitelist();			//初始化白名单
		smsInitService.initGatewayInfo();		//初始化网关
		smsInitService.initGatewayGroup();		//初始化网关分组
		smsInitService.initGatewayGroupRel();	//初始化网关分组关系
		smsInitService.initGatewayQueue();		//初始化网关队列
		
		startup.init();
		
	}
}
