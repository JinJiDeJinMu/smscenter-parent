package com.sanerzone.smscenter.biz.cache;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;
import com.sanerzone.smscenter.biz.entity.SmsTemplate;

//短信模板工具类
public class TemplateCacheHelper {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateCacheHelper.class);
	
	private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("sms_template", "template_info");//模板信息
	
	public static void clearAll(){
		map.clear();
	}
	
	//初始化
	public static void init(List<SmsTemplate> list){
		int size = 0;
		if(list != null && list.size() >0){
			for (SmsTemplate smsTemplate : list) {
				map.put(smsTemplate.getId(), smsTemplate);
			}
			size = list.size();
		}
		logger.info("短信模板缓存加载完成，模板个数："+size);
	}
	
	/**
	 * status -2:审核失败 -1:待审核 0:禁用 1:启用  ""不存在
	 * @param templateid
	 * @param userid
	 * @return
	 */
	public static String getStatus(String templateid,String userid){
		SmsTemplate entity = get(templateid);
		if(entity == null)return "";
		
		if(userid.equals(entity.getUserId()) || "0".equals(entity.getScope())){
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
	 * 获取源模板编号
	 * @param templateid
	 * @return
	 */
	public static String getTemplateId(String templateid){
		SmsTemplate entity = get(templateid);
		if(entity == null)return "";
		return entity.getTemplateId();
	}
	
	/**
	 * 缓存模板信息
	 * @param templateid
	 * @param entity
	 */
	public static void put(String templateid,SmsTemplate entity){
		logger.info("{}, update template cache: {}", templateid, entity);
		map.put(templateid, entity);
	}
	
	/**
	 * 获取模板信息
	 * @param templateid
	 * @return
	 */
	public static SmsTemplate get(String templateid){
		SmsTemplate entity = (SmsTemplate)map.get(templateid);
		return entity;
	}
	
	
	/**
	 * 删除模板
	 * @param templateid
	 */
	public static void del(String templateid){
		map.remove(templateid);
	}
	
}
