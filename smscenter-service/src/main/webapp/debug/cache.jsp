<%@page import="com.sanerzone.smscenter.biz.entity.TopicQueue"%>
<%@page import="com.sanerzone.smscenter.biz.cache.GatewayQueueHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.WhitelistHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.UserBlacklistHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.SysBlacklistHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.PhoneHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.KeywordsHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.TemplateCacheHelper"%>
<%@page import="com.sanerzone.smscenter.biz.entity.SmsTemplate"%>
<%@page import="com.sanerzone.smscenter.biz.cache.AccountCacheHelper"%>
<%@page import="com.sanerzone.smscenter.biz.entity.RoundRobinWeight"%>
<%@page import="java.util.Map"%>
<%@page import="com.sanerzone.smscenter.biz.entity.SmsGatewayGroup"%>
<%@page import="com.sanerzone.smscenter.biz.entity.SmsGateway"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.sanerzone.smscenter.biz.cache.GatewayRelHelper"%>
<%@page import="com.sanerzone.smscenter.biz.cache.GatewayGroupHelper"%>
<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.sanerzone.smscenter.biz.cache.GatewayCacheHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调试结果</title>
</head>
<body>
<% 
	String type = request.getParameter("type");
	String key = request.getParameter("key");
	String json = "";
	if("1".equals(type)){//网关信息
		SmsGateway entity = GatewayCacheHelper.get(key);
		json = JSON.toJSONString(entity);
	}else if("2".equals(type)){//网关分组
		SmsGatewayGroup entity = GatewayGroupHelper.get(key);
		out.println("通道分组实体:"+JSON.toJSONString(entity));
		out.println("<br/>========================================<br/>");
		out.println("通道分组关系:"+JSON.toJSONString(GatewayRelHelper.get(key)));
	}else if("3".equals(type)){//网关分组关系
		/* out.println("通道分组轮循:"+JSON.toJSONString(GatewayRelHelper.getGwCode(key, request.getParameter("phoneType"), request.getParameter("provinceId"))));
		out.println("<br/>========================================<br/>");
		out.println("通道分组关系:"+JSON.toJSONString(GatewayRelHelper.get(key))); */
	}else if("4".equals(type)){//用户账号信息
		Map<String,Object> map = AccountCacheHelper.getMap(key);
		json = JSON.toJSONString(map);
	}else if("5".equals(type)){//短信模板
		SmsTemplate entity = TemplateCacheHelper.get(key);
		json = JSON.toJSONString(entity);
	}else if("6".equals(type)){//敏感词
		boolean flag = KeywordsHelper.exits(key);
		json = JSON.toJSONString(flag);
	}else if("7".equals(type)){//号段属性
		Map<String,String> map = PhoneHelper.get(key+"0000");
		json = JSON.toJSONString(map);
	}else if("8".equals(type)){//系统黑名单
		boolean flag = SysBlacklistHelper.isExist(key);
		json = JSON.toJSONString(flag);
	}else if("9".equals(type)){//营销黑名单
		String userid = request.getParameter("userid");
		boolean flag = UserBlacklistHelper.isExist(userid,key);
		json = JSON.toJSONString(flag);
	}else if("10".equals(type)){//白名单
		boolean flag = WhitelistHelper.isExist(key);
		json = JSON.toJSONString(flag);
	}else if("11".equals(type)){//网关队列
		Map<String,RoundRobinWeight<TopicQueue>> map = GatewayQueueHelper.get(key);
		json = JSON.toJSONString(map);
	}
	out.println(json);
%>
</body>
</html>