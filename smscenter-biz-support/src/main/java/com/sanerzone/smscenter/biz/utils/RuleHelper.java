package com.sanerzone.smscenter.biz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.entity.SmsRuleInfo;
import com.sanerzone.smscenter.biz.entity.SmsRuleRelation;

/**
 * 规则工具类
 * @author zhangjie
 */
public class RuleHelper {
	public static Map<String, SmsRuleInfo> ruleMap = new HashMap<String, SmsRuleInfo>();
	
	public static Map<String, List<SmsRuleRelation>> ruleGroupMap = new HashMap<String, List<SmsRuleRelation>>();
	
	public static Set<String> urlSet = new HashSet<String>();
	public static Set<String> phoneSet = new HashSet<String>();
	public static Set<String> keySet = new HashSet<String>();
	
	//电话（7位及以上连续数字）
	private static String numRegex = "\\d{7,}";
	private static String urlRegex = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";
	
	/**
	 * 获取存储的key
	 * @param jmsgRuleInfo
	 * @return
	 */
	public static String getRuleKey(SmsRuleInfo jmsgRuleInfo)
	{
		return "rule_" + jmsgRuleInfo.getId();
	}
	
	/**
	 * 获取存储的key
	 * @param ruleId
	 * @param ruleType
	 * @param status
	 * @return
	 */
	public static String getRuleKey(String ruleId)
	{
		return "rule_" + ruleId;
	}

	/**
	 * 存放规则
	 * @param ruleId
	 * @param ruleType
	 * @param status
	 * @param ruleContent
	 */
	public static void put(String ruleId, SmsRuleInfo ruleInfo)
	{
		//规则分类 1：网址 2：电话 3：关键字 4：正则式
		if (StringHelper.equals(ruleInfo.getRuleType(), "1"))
		{
			urlSet.add(ruleInfo.getRuleContent());
		}
		else if (StringHelper.equals(ruleInfo.getRuleType(), "2"))
		{
			phoneSet.add(ruleInfo.getRuleContent());
		}
		else if (StringHelper.equals(ruleInfo.getRuleType(), "3"))
		{
			keySet.add(ruleInfo.getRuleContent());
		}
		else
		{
			String key = getRuleKey(ruleId);
			ruleMap.put(key, ruleInfo);
		}
	}
	
	/**
	 * 存放规则
	 * @param jmsgRuleInfo
	 */
	public static void put(SmsRuleInfo jmsgRuleInfo)
	{
		put(jmsgRuleInfo.getId(), jmsgRuleInfo);
	}
	
	/**
	 * 删除规则
	 * @param jmsgRuleInfo
	 */
	public static void del(SmsRuleInfo jmsgRuleInfo)
	{
		del(jmsgRuleInfo.getId(), jmsgRuleInfo);
	}
	
	/**
	 * 删除规则
	 * @param ruleId
	 * @param ruleType
	 * @param status
	 */
	public static void del(String ruleId, SmsRuleInfo ruleInfo)
	{
		//规则分类 1：网址 2：电话 3：关键字 4：正则式
		if (StringHelper.equals(ruleInfo.getRuleType(), "1"))
		{
			urlSet.remove(ruleInfo.getRuleContent());
		}
		else if (StringHelper.equals(ruleInfo.getRuleType(), "2"))
		{
			phoneSet.remove(ruleInfo.getRuleContent());
		}
		else if (StringHelper.equals(ruleInfo.getRuleType(), "3"))
		{
			keySet.remove(ruleInfo.getRuleContent());
		}
		else
		{
			String key = getRuleKey(ruleId);
			ruleMap.remove(key);
		}
	}
	
	/**
	 * 短信内容匹配
	 * @param userId
	 * @param content
	 * @return
	 */
	public static Map<String, String> filtrate(String userId, String content)
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("filCode", "");
		
		String userRuleGroup = AccountCacheHelper.getStringValue(userId, "ruleGroupId", "");
		if(StringHelper.isBlank(userRuleGroup)) {
			resultMap.put("result", "T");
			return resultMap;
		}
		
		
		SmsRuleInfo jmsgRuleInfo;
		//根据规则组获取规则列表
		String key = getRuleGroupKey(userRuleGroup);
		List<SmsRuleRelation> list = ruleGroupMap.get(key);
		if (null == list || list.isEmpty())
		{
			resultMap.put("result", "T");
			return resultMap;
		}
		
		
		// 过滤网址和号码白名单，短信内容中包含的网址和号码任意一个不在白名单中，则不充许发送
		Pattern p;
		Matcher matcher;
		for (SmsRuleRelation ruleRelation : list)
		{
			//规则分类 1：网址 2：电话 3：关键字 4：正则式
			if (StringHelper.equals(ruleRelation.getRuleType(), "1"))
			{
				List<String> contentList = new ArrayList<String>();
				p = Pattern.compile(urlRegex);   
		        matcher = p.matcher(content);  
		        while (matcher.find()) {  
		            contentList.add(matcher.group());
		        }
		        
		        for (String str : contentList)
		        {
		        	if (!urlSet.contains(str))
		        	{
						resultMap.put("result", "短信内容：" + content + " 未包含网址白名单，匹配字符：" + str);
						jmsgRuleInfo = ruleMap.get(getRuleKey(ruleRelation.getRuleId()));
						resultMap.put("filCode", "F0091");
						return resultMap;
		        	}
		        }
			}
			else if (StringHelper.equals(ruleRelation.getRuleType(), "2"))
			{
				List<String> contentList = new ArrayList<String>();
				p = Pattern.compile(numRegex);   
		        matcher = p.matcher(content);  
		        while (matcher.find()) {  
		            contentList.add(matcher.group());
		        }
		        
		        for (String str : contentList)
		        {
		        	if (!phoneSet.contains(str))
		        	{
						resultMap.put("result", "短信内容：" + content + " 未包含电话白名单，匹配字符：" + str);
						jmsgRuleInfo = ruleMap.get(getRuleKey(ruleRelation.getRuleId()));
						resultMap.put("filCode", "F0092");
						return resultMap;
		        	}
		        }
			}
		}
		
		// 过滤通过规则,只要满足任一个，则充许通过
		for (SmsRuleRelation ruleRelation : list)
		{
			if (StringHelper.equals(ruleRelation.getRuleType(), "4"))
			{
				jmsgRuleInfo = ruleMap.get(getRuleKey(ruleRelation.getRuleId()));
				
				if (StringHelper.equals(jmsgRuleInfo.getRuleType(), "4") && StringHelper.equals(jmsgRuleInfo.getIspass(), "0"))
				{
					if (content.matches(jmsgRuleInfo.getRuleContent()))
					{
						resultMap.put("result", "T");
						return resultMap;
					}
				}
			}
		}
		
		//遍历匹配规则
		for (SmsRuleRelation ruleRelation : list)
		{
			if (StringHelper.equals(ruleRelation.getRuleType(), "4"))
			{
				jmsgRuleInfo = ruleMap.get(getRuleKey(ruleRelation.getRuleId()));
				
				if (StringHelper.equals(jmsgRuleInfo.getRuleType(), "4") && StringHelper.equals(jmsgRuleInfo.getIspass(), "1"))
				{
					if ( content.matches(jmsgRuleInfo.getRuleContent()) )
					{
						resultMap.put("result", "短信内容：" + content + " 未通过内容排除正则规则：" + jmsgRuleInfo.getDescription() + " 规则编号：" + jmsgRuleInfo.getRuleCode());
						resultMap.put("filCode", "F009"+jmsgRuleInfo.getRuleCode());
						return resultMap;
					}
				}
			}
		}
		
		resultMap.put("result", "T");
		return resultMap;
	}
	
	/**
	 * 获取存储的key
	 * @param ruleId
	 * @param ruleType
	 * @param status
	 * @return
	 */
	public static String getRuleGroupKey(String groupId)
	{
		return "ruleGroup_" + groupId;
	}
	
	/**
	 * 存放规则
	 * @param jmsgRuleInfo
	 */
	public static void put(SmsRuleRelation ruleRelation)
	{
		String key = getRuleGroupKey(ruleRelation.getGroupId());
		List<SmsRuleRelation> list = ruleGroupMap.get(key);
		if (null == list)
		{
			list = new ArrayList<SmsRuleRelation>();
		}
		list.add(ruleRelation);
		ruleGroupMap.put(key, list);
	}
	
	/**
	 * 存放规则
	 * @param jmsgRuleInfo
	 */
	public static void put(String groupId, List<SmsRuleRelation> list)
	{
		String key = getRuleGroupKey(groupId);
		
		ruleGroupMap.put(key, list);
	}
	
	/**
	 * 删除规则
	 * @param jmsgRuleInfo
	 */
	public static void del(String groupId)
	{
		String key = getRuleGroupKey(groupId);
		del(key);
	}
	
	/**
	 * 删除规则
	 * @param jmsgRuleInfo
	 */
	public static void del(SmsRuleRelation ruleRelation)
	{
		String key = getRuleGroupKey(ruleRelation.getGroupId());
		List<SmsRuleRelation> list = ruleGroupMap.get(key);
		
		for (Iterator<SmsRuleRelation> it = list.iterator(); it.hasNext();) 
		{
			SmsRuleRelation relation = it.next();
			if (StringHelper.equals(relation.getGroupId(), ruleRelation.getGroupId())
					&& StringHelper.equals(relation.getRuleId(), ruleRelation.getRuleId()))
			{
		          it.remove();
		    }
		}
		
		ruleGroupMap.put(key, list);
	}
}
