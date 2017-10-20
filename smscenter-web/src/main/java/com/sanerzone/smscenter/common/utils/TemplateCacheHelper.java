package com.sanerzone.smscenter.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sanerzone.jeebase.common.utils.EhCacheUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsTemplate;

//短信模板工具类
public class TemplateCacheHelper {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateCacheHelper.class);
	
	
	/**
	 * status -2:审核失败 -1:待审核 0:禁用 1:启用  ""不存在
	 * @param templateid
	 * @param userid
	 * @return
	 */
	public static String getStatus(String templateid,String userid){
		SmsTemplate entity = get(templateid);
		if(entity == null)return "";
		
		if(userid.equals(entity.getCreateBy().getId()) || "0".equals(entity.getScope())){
			return entity.getStatus();
		}
		return "";
	}
	
	/**
	 * 获取模板内容
	 * @param templateid
	 * @return
	 */
	public static String getTemplate(String templateid){
		SmsTemplate entity = get(templateid);
		if(entity == null)return "";
		return entity.getContent();
	}
	
	/**
	 * 缓存模板信息
	 * @param templateid
	 * @param entity
	 */
	public static void put(String templateid,Object entity){
		logger.info("{}, update template cache: {}", templateid, entity);
		EhCacheUtils.put(EhCacheUtils.TEMPLATE_CACHE, templateid, entity);
	}
	
	/**
	 * 获取模板信息
	 * @param templateid
	 * @return
	 */
	public static SmsTemplate get(String templateid){
		Object obj = EhCacheUtils.get(EhCacheUtils.TEMPLATE_CACHE, templateid);
		if(obj == null)return null;
		return (SmsTemplate)obj;
	}
	
	
	/**
	 * 删除模板
	 * @param templateid
	 */
	public static void del(String templateid){
		EhCacheUtils.remove(templateid);
	}
	
}
