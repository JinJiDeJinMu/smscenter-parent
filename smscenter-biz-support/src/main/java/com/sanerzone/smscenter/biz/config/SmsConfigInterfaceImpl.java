package com.sanerzone.smscenter.biz.config;

import java.util.List;

import com.sanerzone.smscenter.biz.cache.GatewayCacheHelper;
import com.sanerzone.smscenter.biz.cache.GatewayGroupHelper;
import com.sanerzone.smscenter.biz.cache.GatewayQueueHelper;
import com.sanerzone.smscenter.biz.cache.GatewayRelHelper;
import com.sanerzone.smscenter.biz.cache.KeywordsHelper;
import com.sanerzone.smscenter.biz.cache.TemplateCacheHelper;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroup;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel;
import com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid;
import com.sanerzone.smscenter.biz.entity.SmsRuleInfo;
import com.sanerzone.smscenter.biz.entity.SmsRuleRelation;
import com.sanerzone.smscenter.biz.entity.SmsTemplate;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.biz.utils.RuleHelper;

public class SmsConfigInterfaceImpl implements SmsConfigInterface{

	@Override
	public boolean configTemplate(int type, String templateid, Object entity) {
		switch (type) {
		case 1:
			TemplateCacheHelper.put(templateid, (SmsTemplate)entity);
			break;
		case 2:
			TemplateCacheHelper.del(templateid);
			break;
		default:
			break;
		}
		return false;
	}
	
	@Override
	public boolean configGatewayInfo(int type, Object object, String gwCode) {
		switch (type) {
		case 1:
			GatewayCacheHelper.put(gwCode, (SmsGateway)object);
			break;
		case 2:
			GatewayCacheHelper.del(gwCode);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean configGatewayGroup(int type, Object object, String groupId) {
		switch (type) {
		case 1:
			GatewayGroupHelper.put(groupId, (SmsGatewayGroup)object);
			break;
		case 2:
			GatewayGroupHelper.del(groupId);	
			break;
		default:
			break;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean configGatewayRel(int type, String groupId, Object object) {
		switch (type) {
		case 1:
			GatewayRelHelper.initGatewayGroupRel(groupId, (List<SmsGatewayGroupRel>)object);
			break;
		case 2:
			GatewayRelHelper.del(groupId);
			break;
		case 3:
			GatewayRelHelper.init((List<SmsGatewayGroupRel>) object);
			break;
		default:
			break;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean configGatewayQueue(int type, String gwCode, Object object) {
		switch (type) {
		case 1:
			GatewayQueueHelper.initGatewayQueue(gwCode, (List<SmsGatewayQueueid>) object);
			break;
		case 2:
			GatewayQueueHelper.del(gwCode);	
			break;
		case 3:
			GatewayQueueHelper.init((List<SmsGatewayQueueid>) object);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean configKeywords(int type, String value) {
		switch (type) {
		case 1:
			KeywordsHelper.put(value);
			break;
		case 2:
			KeywordsHelper.del(value);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean configRule(int type, Object object) {
		switch(type) {
		case 1:
			RuleHelper.put((SmsRuleInfo)object);
			break;
		case 2:
			RuleHelper.del((SmsRuleInfo)object);
		default:
			break;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean configRuleGroup(int type, String groupId ,Object object) {
		switch(type) {
		case 1:
			RuleHelper.put(groupId, (List<SmsRuleRelation>)object);
			break;
		case 2:
			RuleHelper.del((SmsRuleRelation)object);
		default:
			break;
		}
		return false;
	}
}
