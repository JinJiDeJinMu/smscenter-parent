package com.sanerzone.smscenter.api.http;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanerzone.common.support.utils.StreamUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.smscenter.api.entity.ApiResult;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.cache.TemplateCacheHelper;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.utils.SignHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.common.tools.HttpRequest;
import com.sanerzone.smscenter.common.tools.IPHelper;
import com.sanerzone.smscenter.common.tools.JedisUtils;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.common.tools.StreamHelper;
import com.sanerzone.smscenter.common.tools.ValidateHelper;
import com.sanerzone.smscenter.common.web.BaseController;

@Controller
@RequestMapping(value = "/api/template")
public class SmsApiTemplateController extends BaseController{
    
    public static Logger logger = LoggerFactory.getLogger(SmsApiTemplateController.class);
    
    @Autowired
    private MQHelper mQUtils;
    
//    @Autowired
//    private SmsTaskInterface smsTaskInterface;
    
    @SuppressWarnings("rawtypes")
    public Map getPostDataMap(HttpServletRequest request, HttpServletResponse response){
        try{
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null){
                charEncoding = "UTF-8";
            }
            /*String respText = StreamHelper.InputStreamTOString(request.getInputStream(), charEncoding);*/
            String respText = StreamUtils.InputStreamTOString(request.getInputStream(),charEncoding);
            logger.info("sms api json: {}", respText);
            if(StringHelper.isNotBlank(respText)){
                return (Map) JSON.parseObject(respText, Map.class);
            }
        }catch (IOException e){
            
        }
        return null;
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "sms/send", method = RequestMethod.POST)
    public String smsSend(HttpServletRequest request, HttpServletResponse response){
        Map pMap = getPostDataMap(request, response);
    	long time = System.currentTimeMillis();
        Map map = Maps.newHashMap();
        try{
            String userId = StringHelper.valueof(pMap.get("userid"));
            String ts = StringHelper.valueof(pMap.get("ts"));
            String pSign = StringHelper.valueof(pMap.get("sign"));
            String appid = StringHelper.valueof(pMap.get("appid"));
            
            
            //基础能数校验
            String result = ValidateHelper.validate(userId, appid, ts, pSign, request, response);
            if (!"0".equals(result)){//验证成功
            	return result;
            }
            
            //校验业务参数
            result = ValidateHelper.validateSmsParameter(pMap, request, response);
            if (!"0".equals(result)){//验证成功
            	return result;
            }
            
            int speed = AccountCacheHelper.getIntegerValue(userId, "httpSpeed", -1);//发送速率
            if(speed > 0){
            	RateLimiter limiter = AccountCacheHelper.getHttpSpeed(userId);
            	limiter.acquire();
            }
            
            //校验发送时间
            String sendtime = StringHelper.valueof(pMap.get("time"));
            Date sendDatetime = null;
            if (StringHelper.isNotBlank(sendtime)){
                sendDatetime = sendDatetime(sendtime);
                if (sendDatetime == null){
                    return ValidateHelper.result("8", "time时间格式错误", null, response);
                }
            }
            
            //校验手机号码
            String phones = StringHelper.valueof(pMap.get("mobile"));
            if (phones.length() > 12){
                return ValidateHelper.result("15", "号码个数超过限制", null, response);
            }
            
            //校验短信内容
            String templateid = StringHelper.valueof(pMap.get("templateid"));
            String smsContent = "";
            List<String> templateparam = Lists.newArrayList(); 
            if(StringHelper.isBlank(templateid)){
	            smsContent = StringEscapeUtils.unescapeHtml4(StringHelper.valueof(pMap.get("msgcontent")));
	            smsContent = smsContent.trim();
	            smsContent = SignHelper.formatContent(smsContent);
	            
	            String sign = SignHelper.get(smsContent);//短信签名
	            if (StringHelper.isBlank(sign)){
					sign = AccountCacheHelper.getStringValue(userId, "forceSign", "");	//强制签名
	            	if(StringHelper.isNotBlank(sign)) {
						smsContent = "【"+sign+"】" + smsContent;
					}
				}
            }else{
            	//获取模板短信
            	templateparam = (List<String>)pMap.get("templateparam");
            	if(templateparam != null && templateparam.size() > 0){
                	String templateStatus = TemplateCacheHelper.getStatus(templateid, userId);//模板状态
                	if(!"1".equals(templateStatus)){
                		String msg = "短信模板不存在";
                		if("-2".equals(templateStatus)){
                			msg = "短信模板审核失败";
                		}else if("-1".equals(templateStatus)){
                			msg = "短信模板待审核";
                		}else if("0".equals(templateStatus)){
                			msg = "短信模板禁用";
                		}
                		return ValidateHelper.result("18", msg, null, response);
                	}
                	String template = TemplateCacheHelper.getTemplate(templateid);//获取模板内容
                	
                	int maxParam = templateparam.size();
                	if(template.indexOf(("{"+maxParam+"}")) >= 0){
                		for (int i=1;i<=maxParam;i++) {
                			template = template.replace("{"+i+"}", templateparam.get(i-1));
						}
                	}else{
                		return ValidateHelper.result("19", "短信模板参数个数不匹配", null, response);
                	}
            		
                	smsContent = template;
            	}else{
                    return ValidateHelper.result("17", "短信模板参数不能为空", null, response);
            	}
            }
            
            String sTemplateid = TemplateCacheHelper.getTemplateId(templateid);//获取源模板编号
            
            if(smsContent.length() > 600) {
                return ValidateHelper.result("16", "短信内容过长", null, response);
            }
            
            // 执行发送
            int smsSize = StringHelper.smsSize(smsContent);
        	String sendtermid = StringHelper.stripToEmpty(StringHelper.valueof(pMap.get("extnum")));//扩展号
            String customTaskid = StringHelper.valueof(pMap.get("messageid"));//客户任务ID
        	//余额
            String amountKey = AccountCacheHelper.getAmountKey("sms", userId);
            // 扣款
            long amount = JedisUtils.decrBy(amountKey, smsSize);
                if (amount < 0){
            	JedisUtils.incrBy(amountKey, smsSize);
                return ValidateHelper.result("3", "余额不足", null, response);
            }
        	
            
            Map<String,Object> paramValueMap = Maps.newHashMap();
            paramValueMap.put("templateId", sTemplateid);
            paramValueMap.put("paramValue", templateparam);
            String paramValue = JSON.toJSONString(paramValueMap);

        	SMSREQMessage message = new SMSREQMessage();
        	String taskId = new MsgId(1000001).toString();
        	message.setTaskid(taskId);
        	message.setAccId(appid);
        	message.setUserid(userId);
        	message.setFeeType(AccountCacheHelper.getStringValue(userId+"_0", "feeType", "1"));
        	message.setFeePayment("1");//默认值
            message.setCustomTaskid(customTaskid);
            message.setCustomServiceid("0");
            message.setPhones(phones);
            message.setSendnumber(sendtermid);
            message.setMsgContent(smsContent);
            message.setSmsContentId(templateid);
            message.setSmsType("0");//短信
            message.setSourceGateWayId("HTTP");
            message.setSourceGateWayProto("HTTP");
        	message.setMassFlag("0");//单个发送
        	message.setSendTime(new Date());
        	message.setReceiveTime(new Date());
        	message.setParamValue(paramValue);
        	message.setRegisteredDelivery(StringHelper.registeredDelivery(AccountCacheHelper.getStringValue(userId+"_"+appid, "callbackUrl", ""))?"1":"0");
        	message.setDeliveryGateWayId("HTTP");

            
            // 提交到REQ
            String msgid = mQUtils.sendSmsREQ("SINGLE", taskId, message);
            logger.info("HttpApi-Submit-REQ = taskid:{}, msgid:{}, time:{}",  message.getTaskid(), msgid, (System.currentTimeMillis()-time));
            
            // 提交队列失败
            if( "-1".equals(msgid)) {
            	 return ValidateHelper.result("1", "提交失败", null, response);
            }
            	
            // 提交队列成功
            map = Maps.newHashMap();
            map.put("taskid", message.getTaskid());
            return ValidateHelper.result("0", "提交成功", map, response);
        }catch (Exception e){
            e.printStackTrace();
            return ValidateHelper.result("1", "提交失败", null, response);
        }
    }
    
    
    //	查询订单状态
    @RequestMapping(value = "sms/query")
    public String smsSendQuery(HttpServletRequest request, HttpServletResponse response){
        return null;
    }
    
    
    
    //	查询用户余额
    @RequestMapping(value = "sms/balance")
    public String smsSendBalance(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userid");
        String sign = request.getParameter("sign");
        String ts = request.getParameter("ts");
        String appid = request.getParameter("appid");
        
        String result = ValidateHelper.validate(userId, appid, ts, sign, request, response);
        if ("0".equals(result)){//验证成功
        	//余额
            String amountKey = AccountCacheHelper.getAmountKey("amount", "sms", userId);
            String money = JedisUtils.get(amountKey);
            Map<String, String> data = Maps.newHashMap();
            data.put("balance", String.valueOf(money));
            return ValidateHelper.result("0", "查询成功", data, response);
        }else{
            return result;
        }
    }
    
    // 获取敏感词
    @RequestMapping(value = "sms/keyword")
    public String keyword(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userid");
        String sign = request.getParameter("sign");
        
        String result = validateKeyword(userId, sign, request, response);
        if ("0".equals(result)){//验证成功
            String smsContent = request.getParameter("msgcontent");
            if (StringHelper.isBlank(smsContent)){
                return resultKeyword("2", "msgcontent参数不能为空", userId, "", response);
            }
            
            return resultKeyword("0", "查询成功", userId, "", response);//TODO 匹配敏感词 没有实现
        }else{
            return result;
        }
    }
    
    private String validateKeyword(String userId, String sign, HttpServletRequest request, HttpServletResponse response){
        // 校验 userId
        if (StringHelper.isBlank(userId)){
            return resultKeyword("2", "userid参数不能为空", userId, "", response);
        }
        // 校验 apikey
        if (StringHelper.isBlank(sign)){
            return resultKeyword("2", "sign参数不能为空", userId, "", response);
        }
        
        String ts = request.getParameter("ts");
        if (StringHelper.isBlank(ts)){
            return resultKeyword("2", "ts参数不能为空", userId, "", response);
        }
        
        //验证用户是否存在
        String curUserId = AccountCacheHelper.getStringValue(userId, "id", "");
        
        if (StringHelper.isBlank(curUserId)){
            return resultKeyword("4", "用户不存在", userId, "", response);
        }else{
        	int usedFlag = AccountCacheHelper.getIntegerValue(userId, "usedFlag", 1);
			if(usedFlag == 0) {  //账户禁用发送功能
				return resultKeyword("4", "用户不存在", userId, "", response);
			}
        	
            String whiteIP = AccountCacheHelper.getStringValue(userId, "whiteIP", "");
            String ip = IPHelper.getIpAddr(request);
            
            if(StringHelper.isBlank(whiteIP) || ("," + whiteIP + ",").indexOf("," + ip + ",") >= 0){//验证IP
                String apikey = AccountCacheHelper.getStringValue(userId, "apikey", "");
                String myMD5 = HttpRequest.md5(userId + ts + apikey);//MD5加密 md5(userid + ts + apikey)
                if (!sign.equals(myMD5)){//MD5验证
                    return resultKeyword("6", "MD5校验失败", userId, "", response);
                }
            }else{
                return resultKeyword("7", "IP校验失败", userId, "", response);
            }
        }
        
        return "0";
    }
    
    
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    private String result(HttpServletResponse response, Map map){
//        String code = StringHelper.valueof(map.get("code"));
//        String msg = StringHelper.valueof(map.get("msg"));
//        String taskid = StringHelper.valueof(map.get("taskid"));
//        
//        
//        ApiResult entity = new ApiResult();
//        entity.setCode(code);
//        entity.setMsg(msg);
//        if (StringHelper.equals(code, "0")){
//            Map<String, String> tmp = Maps.newHashMap();
//            tmp.put("taskid", taskid);
//            entity.setData(tmp);
//        }
//        
//        return renderString(response, entity);
//        
//    }
    
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private String resultKeyword(String code, String msg, String userid, String keywords, HttpServletResponse response){
        
        ApiResult entity = new ApiResult();
        entity.setCode(code);
        entity.setMsg(msg);
        if (StringHelper.isNotBlank(keywords)){
            Map<String, String> map = Maps.newHashMap();
            map.put("keywords", keywords);
            entity.setData(map);
        }
        
        return renderString(response, entity);
    }
    
    public static Date sendDatetime(String sendTime){
        Date result = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            result = sdf.parse(sendTime);
        }catch (Exception err){
            logger.debug("时间格式错误: " + err.getMessage());
        }
        return result;
    }
    
    @RequestMapping(value = "sms/report")
    public String reportTest(HttpServletRequest request, HttpServletResponse response){
    	@SuppressWarnings("rawtypes")
		Map map = getPostDataMap(request, response);
    	System.out.println("=========================>"+JSON.toJSONString(map));
    	return renderString(response, "ok", "application/json");
    }
    
    private String getTaskData(List<String> phoneList){
    	StringBuffer sb = new StringBuffer("");
    	for (String phone : phoneList) {
    		sb.append(phone).append("\r\n");
		}
    	return sb.toString();
    }
    
    
}
