/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web.front;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.utils.IdGen;
import com.sanerzone.jeebase.common.utils.JedisUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.entity.Office;
import com.sanerzone.jeebase.modules.sys.entity.Role;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.service.SystemService;
import com.sanerzone.jeebase.modules.sys.utils.DictUtils;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.common.utils.SmsApi2CRF;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;

/**
 * 注册信息Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front/reg")
public class FrontRegController extends BaseController {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private BaseAccountService baseAccountService;
	
	//注册页面
	@RequestMapping(value = "index")
	public String reg(HttpServletResponse response) {
		return "modules/sms/front/reg";
	}
	
	//获取验证码
	@RequestMapping(value = "verifyCode")
	public String verifyCode(String mobile, HttpServletResponse response) {
		int count = systemService.getUserCount(mobile);
		if(count >= 1){
			return renderString(response, new Result<String>("1", "手机号码已经注册"));
		}else{
			String key = "code_" + mobile;
			
	        if (JedisUtils.ttl(key) > 4 * 60) {
	            return renderString(response, new Result<String>("2", "一分钟后再试"));
	        }
			
			String verifyCode = radomVerifyCode();//随机6位数字验证码
			logger.info("{}, 获取验证码为: {}", key, verifyCode);
			JedisUtils.set(key, verifyCode, 300);
			
			//实际下发
	        try {
	            sendCode(verifyCode, mobile);//发送验证码
	        } catch (Exception e) {
	            return renderString(response, new Result<String>("3", "获取验证码异常"));
	        }
		}
		
		return renderString(response, new Result<String>());
	}
	
	private String radomVerifyCode(){
		return String.valueOf((int)((Math.random()*9+1)*100000));
	}
	
	//注册
	@RequestMapping(value = "register")
	public String register(User user, String verifyCode, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		try{
			//校验验证码
			if(!validateCode(user.getMobile(), verifyCode)){
				return renderString(response, new Result<String>("1","验证码错误"));
			}
			user.setLoginName(user.getMobile());
			user.setName(user.getMobile());
			user.setUserType("1");
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
			user.setCompany(new Office("1"));//公司
			
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			Role role = new Role("4");
			roleList.add(role);
			user.setRoleList(roleList);
			// 保存用户信息
			systemService.saveUser(user);
			// 保存用户账号
			BaseAccount baseAccount = new BaseAccount();
			baseAccount.setAccName("应用01");
			baseAccount.setAccStatus("-2");
			baseAccount.setServiceId("10");//行业短信
			baseAccount.setAccType("sms");
			baseAccount.setCompany(user.getCompany());
			baseAccount.setUserid(user.getId());
			Map<String,Object> map = Maps.newHashMap();
			map.put("feeType", 2);
			map.put("filterWordFlag",1);
			map.put("sysBlackListFlag", 1);
			map.put("userBlackListFlag", 1);
			map.put("groupId", DictUtils.getDictValue("10", "service_group", ""));//通道分组  应用对应的通道分组
			map.put("interfaceFlag",1);
			map.put("apikey", IdGen.uuid());
			map.put("rspContentType", 1);
			baseAccountService.save(baseAccount, map, false);
			
		}catch(Exception e){
			logger.info("注册失败{}", e);
			result.setCode("2");
			result.setMsg("注册账号失败");
		}
		
		return renderString(response, result);
	}
	
    // 发送验证码
    private void sendCode(String code, String phone) throws UnsupportedEncodingException {
       SmsApi2CRF.sendSMS(phone, "【云通讯】您本次验证码" + code + "，5分钟内有效。如非本人操作，请忽略！");
    }
	
	private boolean validateCode(String mobile, String code){
		String key = "code_" + mobile;
		String value = JedisUtils.get(key);
		if(code.equals(value))return true;
		return false;
	}
		
}