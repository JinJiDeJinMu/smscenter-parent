package com.sanerzone.smscenter.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroup;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel;
import com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid;
import com.sanerzone.smscenter.biz.entity.SmsPhoneInfo;
import com.sanerzone.smscenter.biz.entity.SmsRuleGroup;
import com.sanerzone.smscenter.biz.entity.SmsRuleInfo;
import com.sanerzone.smscenter.biz.entity.SmsRuleRelation;
import com.sanerzone.smscenter.biz.entity.SmsTask;
import com.sanerzone.smscenter.biz.entity.SmsTemplate;

public interface SmsInitMapper extends BaseMapper<SmsTask>{
	public List<SmsPhoneInfo> findPhoneInfoList();
	public List<SmsTemplate> findTemplateList();
	public List<String> findKeywordList(Integer pageNo);
	public List<String> findSysBlacklistList(Integer pageNo);
	public List<String> findUserBlacklistList(Integer pageNo);
	public List<String> findWhitelistList(Integer pageNo);
	public List<SmsGateway> findGatewayInfoList();
	public List<SmsGatewayGroup> findGatewayGroupList();
	public List<SmsGatewayGroupRel> findGatewayGroupRelList();
	public List<SmsGatewayQueueid> findGatewayQueueidList();
	public List<SmsRuleInfo> findRuleInfoList();
	public List<SmsRuleRelation> findRuleRelationList();
	public List<SmsRuleGroup> findRuleGroupList();
//	public List<SmsSign> findSmsSign(Integer pageNo);
//	public List<SmsUserSpnumber> findUserSpnumber();
}
