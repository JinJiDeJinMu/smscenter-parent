/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web.front;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.utils.IdGen;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.DictUtils;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;

/**
 * 账号信息Controller
 * @author zhukc
 * @version 2017-08-15
 */
@Controller
@RequestMapping(value = "/front/account")
public class FrontAccountController extends BaseController {
    
    @Autowired
    private BaseAccountService baseAccountService;
	
	//获取用户基本信息
	@RequestMapping(value = "accountList")
	public String accountList(HttpServletResponse response) {
		Result<List<BaseAccount>> result = new Result<List<BaseAccount>>();
		User user = UserUtils.getUser();
		BaseAccount param = new BaseAccount();
		param.setUserid(user.getId());
		List<BaseAccount> list = baseAccountService.findList(param);
		result.setData(list);
		return renderString(response, result);
	}
	
	//创建应用
	@RequestMapping(value = "createAccount")
	public String createAccount(BaseAccount baseAccount, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		Map<String,Object> map = Maps.newHashMap();
		User user = UserUtils.getUser();
		baseAccount.setUserid(user.getId());
		map.put("callbackUrl", baseAccount.getCallBackUrl());
		map.put("groupId", DictUtils.getDictValue(baseAccount.getServiceId(), "service_group", ""));//通道分组  应用对应的通道分组
		if(StringHelper.isBlank(baseAccount.getId())){//新增
			baseAccount.setAccStatus("-2");//待上线
			baseAccount.setCompany(user.getCompany());
			baseAccount.setAccType("sms");
			map.put("filterWordFlag",1);
			map.put("sysBlackListFlag", 1);
			map.put("userBlackListFlag", 1);
			map.put("interfaceFlag",1);
			map.put("apikey", IdGen.uuid());
			map.put("rspContentType", 1);
			baseAccountService.save(baseAccount, map, false);
		}else{//修改
			baseAccountService.updateByFront(baseAccount,map,true);
		}
        return renderString(response, result);
	}
	
	
	//应用信息
	@RequestMapping(value = "accountInfo")
	public String accountInfo(BaseAccount baseAccount, String type ,HttpServletResponse response, Model model) {
		String render = "accountForm";
		if("view".equals(type)){
			render = "accountView";
		}
		BaseAccount entity = baseAccountService.get(baseAccount.getId());
		Map<String,Object> map = baseAccountService.getDBMap(baseAccount.getId());
		if(entity != null){
			entity.setCallBackUrl(StringHelper.valueof(map.get("callbackUrl")));
			entity.setApikey(StringHelper.valueof(map.get("apikey")));
		}
		model.addAttribute("entity", entity);
		
		return "modules/sms/front/"+render;
	}
	
	//申请上线 -1,删除应用 -3
	@RequestMapping(value = "updateStatus")
	public String updateStatus(BaseAccount baseAccount, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		baseAccountService.updateAccStatus(baseAccount, false);
        return renderString(response, result);
	}
}