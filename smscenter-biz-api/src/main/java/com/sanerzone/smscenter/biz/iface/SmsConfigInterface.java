package com.sanerzone.smscenter.biz.iface;

/**
 * 缓存工具类
 * @param type 1:新增 2:删除
 * @param object
 * @author Administrator
 *
 */
public interface SmsConfigInterface {
	//短信模板
	public boolean configTemplate(int type, String templateid, Object entity);
	//网关信息
	public boolean configGatewayInfo(int type, Object object,String gwCode);
	//网关分组
	public boolean configGatewayGroup(int type,Object object,String groupId);
	//网关分组关系
	public boolean configGatewayRel(int type,String groupId,Object object);
	//网关队列
	public boolean configGatewayQueue(int type,String gwCode,Object object);
	//关键字
	public boolean configKeywords(int type,String value);
	
	public boolean configRule(int type, Object object);
	public boolean configRuleGroup(int type, String groupId, Object object);
}
