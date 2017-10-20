package com.sanerzone.smscenter.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sanerzone.smscenter.biz.cache.GatewayCacheHelper;
import com.sanerzone.smscenter.biz.cache.GatewayGroupHelper;
import com.sanerzone.smscenter.biz.cache.GatewayQueueHelper;
import com.sanerzone.smscenter.biz.cache.GatewayRelHelper;
import com.sanerzone.smscenter.biz.cache.KeywordsHelper;
import com.sanerzone.smscenter.biz.cache.PhoneHelper;
import com.sanerzone.smscenter.biz.cache.SysBlacklistHelper;
import com.sanerzone.smscenter.biz.cache.TemplateCacheHelper;
import com.sanerzone.smscenter.biz.cache.UserBlacklistHelper;
import com.sanerzone.smscenter.biz.cache.WhitelistHelper;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroup;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel;
import com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid;
import com.sanerzone.smscenter.biz.entity.SmsPhoneInfo;
import com.sanerzone.smscenter.biz.entity.SmsTask;
import com.sanerzone.smscenter.biz.entity.SmsTemplate;
import com.sanerzone.smscenter.biz.mapper.SmsInitMapper;
import com.sanerzone.smscenter.biz.service.ISmsInitService;

@Service
public class SmsInitServiceImpl extends ServiceImpl<SmsInitMapper, SmsTask> implements ISmsInitService{
	
	private Logger logger = LoggerFactory.getLogger(SmsInitServiceImpl.class);
	
	//初始化模板
	public void initTemplate(){
		TemplateCacheHelper.clearAll();
		List<SmsTemplate> list = baseMapper.findTemplateList();
		TemplateCacheHelper.init(list);
		logger.info("初始化短信模板完成,短信模板个数:"+list.size());
	}
	
	//初始化号段
	public void initPhoneInfo(){
		PhoneHelper.clearAll();
		List<SmsPhoneInfo> list = baseMapper.findPhoneInfoList();
		PhoneHelper.init(list);
		logger.info("初始化号段完成,号段个数:"+list.size());
	}
	
	//初始化敏感词
	public void initKeyword(){
		KeywordsHelper.clearAll();
		
		int pageNo = 0;
		int pageSize = 10000;
		
		int count = 0;
		
		int index = 0;
		while(true){
			pageNo = index*pageSize;
			List<String> list = baseMapper.findKeywordList(pageNo);
			if(list != null && list.size() >0){
				count=count+list.size();
				KeywordsHelper.init(list);
				index++;
			}else{
				break;
			}
			if(list.size() <pageSize){
				break;
			}
		}
		
		logger.info("初始化敏感词完成,敏感词个数:"+count);
	}
	
	//初始化系统黑名单
	public void initSysBlacklist(){
		SysBlacklistHelper.clearAll();
		
		int pageNo = 0;
		int pageSize = 100000;
		int count = 0;
		int index = 0;
		while(true){
			pageNo = index*pageSize;
			List<String> list = baseMapper.findSysBlacklistList(pageNo);
			if(list != null && list.size() >0){
				count = count+list.size();
				SysBlacklistHelper.init(list);
				index++;
			}else{
				break;
			}
			if(list.size() <pageSize){
				break;
			}
		}
		
		logger.info("初始化系统黑名单完成,系统黑名单个数:"+count);
	}
	
	//初始化用户黑名单
	public void initUserBlacklist(){
		UserBlacklistHelper.clearAll();
		
		int pageNo = 0;
		int pageSize = 100000;
		int count = 0;
		int index = 0;
		while(true){
			pageNo = index*pageSize;
			List<String> list = baseMapper.findUserBlacklistList(pageNo);
			if(list != null && list.size() >0){
				count = count+list.size();
				UserBlacklistHelper.init(list);
				index++;
			}else{
				break;
			}
			if(list.size() <pageSize){
				break;
			}
		}
		logger.info("初始化用户黑名单完成,用户黑名单个数:"+count);
	}
	
	//初始化白名单
	public void initWhitelist(){
		WhitelistHelper.clearAll();
		
		int pageNo = 0;
		int pageSize = 100000;
		int count = 0;
		int index = 0;
		while(true){
			pageNo = index*pageSize;
			List<String> list = baseMapper.findWhitelistList(pageNo);
			if(list != null && list.size() >0){
				count = count+list.size();
				WhitelistHelper.init(list);
				index++;
			}else{
				break;
			}
			if(list.size() <pageSize){
				break;
			}
		}
		logger.info("初始化白名单完成,白名单个数:"+count);
	}
	
	//初始化网关信息
	public void initGatewayInfo(){
		GatewayCacheHelper.clearAll();
		List<SmsGateway> list = baseMapper.findGatewayInfoList();
		GatewayCacheHelper.init(list);
		logger.info("初始化网关信息完成,网关个数:"+list.size());
	}
	
	//初始化网关分组
	public void initGatewayGroup(){
		GatewayGroupHelper.clearAll();
		List<SmsGatewayGroup> list = baseMapper.findGatewayGroupList();
		GatewayGroupHelper.init(list);
		logger.info("初始化网关分组完成,网关分组个数:"+list.size());
	}
	
	//初始化网关分组关系
	public void initGatewayGroupRel(){
		List<SmsGatewayGroupRel> list = baseMapper.findGatewayGroupRelList();
		GatewayRelHelper.init(list);
		logger.info("初始化网关分组关系完成,网关分组关系个数:"+list.size());
	}
	
	//初始化网关队列
	public void initGatewayQueue(){
		List<SmsGatewayQueueid> list = baseMapper.findGatewayQueueidList();
		GatewayQueueHelper.init(list);
		logger.info("初始化网关队列完成,网关队列个数:"+list.size());
	}
	

}
