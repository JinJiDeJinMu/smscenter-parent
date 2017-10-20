/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.utils.JedisUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.entity.Office;
import com.sanerzone.jeebase.modules.sys.entity.Role;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.service.OfficeService;
import com.sanerzone.jeebase.modules.sys.service.SystemService;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.service.BaseAccountOptionsService;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;

/**
 * 账号信息Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/account/baseAccount")
public class BaseAccountController extends BaseController {

	@Autowired
	private BaseAccountService baseAccountService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private BaseAccountOptionsService baseAccountOptionsService;
	
	@ModelAttribute
	public BaseAccount get(@RequestParam(required=false) String id) {
		BaseAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseAccountService.get(id);
		}
		if (entity == null){
			entity = new BaseAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = "index")
	public String index() {
		return "modules/account/baseAccountIndex";
	}
	
	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseAccount baseAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		baseAccount.setUserType("1");//业务用户
		Page<BaseAccount> page = baseAccountService.findPage(new Page<BaseAccount>(request, response), baseAccount); 
		model.addAttribute("page", page);
		return "modules/account/baseAccountList";
	}
	
	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = "checkList")
	public String checkList(BaseAccount baseAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		baseAccount.setUserType("1");//业务用户
		baseAccount.setAccStatus("-1");//申请上线
		Page<BaseAccount> page = baseAccountService.findPage(new Page<BaseAccount>(request, response), baseAccount); 
		model.addAttribute("page", page);
		return "modules/account/baseAccountCheckList";
	}
	
	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = "lowerList")
	public String lowerList(BaseAccount baseAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		baseAccount.setUserType("1");//业务用户
		baseAccount.setCreateBy(UserUtils.getUser());
		Page<BaseAccount> page = baseAccountService.findPage(new Page<BaseAccount>(request, response), baseAccount); 
		model.addAttribute("page", page);
		return "modules/account/lowerBaseAccountList";
	}

	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = "form")
	public String form(BaseAccount baseAccount, Model model) {
		
		User user = systemService.getUser(baseAccount.getUserid());
		if(user != null){
			baseAccount.setUser(user);
		}
		List<Role> roleList = Lists.newArrayList();
		roleList.addAll(systemService.findRoleByType("1"));
		
		Map<String,String> options = Maps.newHashMap(); 
		if(baseAccount != null && StringUtils.isNotBlank(baseAccount.getId())){
			Map<String,String> pMap = Maps.newHashMap();
			pMap.put("accId", baseAccount.getId());
			options.putAll(baseAccountOptionsService.findOptionsMap(pMap));
			Map<String,Object> pubMap = baseAccountService.getPubOptions(user.getId());
			baseAccount.setOpt_feeType(StringHelper.valueof(pubMap.get("feeType")));//计费方式
		}
		
		model.addAttribute("baseAccount", baseAccount);
		model.addAttribute("allRoles", roleList);
		model.addAttribute("options", options);
		return "modules/account/baseAccountForm";
	}
	
	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = "lowerForm")
	public String lowerForm(BaseAccount baseAccount, Model model) {
		
		User user = systemService.getUser(baseAccount.getUserid());
		if(user != null){
			baseAccount.setUser(user);
		}
		List<Role> roleList = Lists.newArrayList();
		roleList.addAll(systemService.findRoleByType("1"));
		
		/**
		Map<String,String> options = Maps.newHashMap(); 
		if(baseAccount != null && StringUtils.isNotBlank(baseAccount.getId())){
			options.putAll(baseAccountOptionsService.findOptionsMap(baseAccount.getUserid()));
			baseAccount.setOpt_feeType(options.get("feeType"));//计费方式
		}**/
		
		model.addAttribute("baseAccount", baseAccount);
		model.addAttribute("allRoles", roleList);
		//model.addAttribute("options", options);
		return "modules/account/lowerBaseAccountForm";
	}

	@RequiresPermissions("account:baseAccount:edit")
	@RequestMapping(value = "save")
	public String save(BaseAccount baseAccount, String newPassword,String oldLoginName, String loginName, Model model, RedirectAttributes redirectAttributes
			, HttpServletRequest request) {
		if (!beanValidator(model, baseAccount)){
			return form(baseAccount, model);
		}
		User user = baseAccount.getUser();
		List<String> roleIdList = user.getRoleIdList();

		
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}
		
		if (!"true".equals(checkLoginName(oldLoginName, loginName))){
			addMessage(model, "保存账户信息失败，登录名已存在");
			return form(baseAccount, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		user.setLoginFlag("1");
		user.setCompany(baseAccount.getCompany());
		user.setLoginName(loginName);
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
		}
		baseAccount.setAccStatus("-2");//待上线
		baseAccount.setUserid(user.getId());
		Map<String,Object> map = WebUtils.getParametersStartingWith(request, "opt_");
		baseAccountService.save(baseAccount,map);
		
		addMessage(redirectAttributes, "保存账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccount/?repage";
	}
	
	@RequiresPermissions("account:baseAccount:edit")
	@RequestMapping(value = "lowerSave")
	public String lowerSave(BaseAccount baseAccount, String newPassword,String oldLoginName, String loginName, Model model, RedirectAttributes redirectAttributes
			, HttpServletRequest request) {
		if (!beanValidator(model, baseAccount)){
			return form(baseAccount, model);
		}
		User user = baseAccount.getUser();
		List<String> roleIdList = user.getRoleIdList();

		
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(newPassword)) {
			user.setPassword(SystemService.entryptPassword(newPassword));
		}
		
		if (!"true".equals(checkLoginName(oldLoginName, loginName))){
			addMessage(model, "保存账户信息失败，登录名已存在");
			return form(baseAccount, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		// 保存用户信息
		user.setLoginFlag("1");
		user.setCompany(baseAccount.getCompany());
		user.setLoginName(loginName);
		systemService.saveUser(user);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
		}
		baseAccount.setUserid(user.getId());
		baseAccount.setAccStatus("-2");//待上线
		Map<String,Object> map = WebUtils.getParametersStartingWith(request, "opt_");
		baseAccountService.save(baseAccount,map,false);
		
		addMessage(redirectAttributes, "保存账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccount/lowerList";
	}
	
	@RequiresPermissions("account:baseAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseAccount baseAccount, RedirectAttributes redirectAttributes) {
		baseAccountService.delete(baseAccount);
		addMessage(redirectAttributes, "删除账户信息成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccount/?repage";
	}
	
	@RequiresPermissions("account:baseAccount:edit")
	@RequestMapping(value = "updateAccStatus")
	public String updateAccStatus(BaseAccount baseAccount, String oldAccStatus, String type ,RedirectAttributes redirectAttributes) {
		String redirect = "list";
		if("lower".equals(type)){
			redirect = "lowerList";
		}else if("check".equals(type)){
			redirect = "checkList";
		}
		String accStatus = "1";
		String msg = "启用";
		if("1".equals(oldAccStatus)){
			accStatus = "0";
			msg = "禁用";
		}
		baseAccount.setAccStatus(accStatus);
		baseAccountService.updateAccStatus(baseAccount);
		addMessage(redirectAttributes, msg+"账户成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccount/"+redirect;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	private Map<String,String> saveOffice(String parentId,String companyName){
		Map<String,String> result = Maps.newHashMap();
		String code = "1";
		String officeId = "";
		Office office = new Office();
		Office parent = officeService.get(parentId);
		if(parent != null){
			if(parent.getParentIds().split(",").length >= 6){//最多支持5级公司
				code = "0";
			}
		}
		
		office.setParent(parent);
		office.setType("1");
		office.setGrade("1");
		office.setUseable(Global.YES);
		office.setName(companyName);
		officeService.save(office);
		officeId = office.getId();
		result.put("code", code);
		result.put("officeId",officeId);
		
		return result;
	}
	
	@RequiresPermissions("account:baseAccount:view")
	@RequestMapping(value = "rechargeInit")
	public String rechargeInit(BaseAccount baseAccount, Model model) {
		model.addAttribute("baseAccount", baseAccount);
		return "modules/account/baseAccountRecharge";
	}
	
	@RequiresPermissions("account:baseAccount:edit")
	@RequestMapping(value = "recharge")
	public String recharge(BaseAccount baseAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseAccount)){
			return form(baseAccount, model);
		}
		
		String accType = baseAccount.getAccType();
		String msg = "";
		msg = rechargePublic(baseAccount);
		return redirect(msg, accType, redirectAttributes);
	}
	
	
	@RequiresPermissions("account:baseAccount:edit")
	@RequestMapping(value = "ajaxRecharge")
	public String ajaxRecharge(BaseAccount baseAccount, HttpServletResponse response) {
		
		String remark = baseAccount.getRemark();
		if(StringUtils.isNotBlank(remark)){
			try {
				remark = URLDecoder.decode(remark, "UTF-8");
				baseAccount.setRemark(remark);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String msg = rechargePublic(baseAccount);
		return renderString(response, msg);
	}
	
	
	private String rechargePublic(BaseAccount baseAccount){
		String msg = "操作成功";
		User curUser = UserUtils.getUser();//当前操作人
		int iAmount = Integer.valueOf(baseAccount.getAccAmount());//充值额度
		String changeType = baseAccount.getChangeType();//变动类型
		boolean adminFlag = false;//充值标识  系统管理员或者是充值角色人员  不需要账户信息
		if("0".equals(curUser.getUserType())){//超级用户
			adminFlag = true;
		}
		User selUser = UserUtils.get(baseAccount.getUserid());//被操作的用户
		
		
		String selKey = AccountCacheUtils.getAmountKey("sms", baseAccount.getUserid());
		String curKey = AccountCacheUtils.getAmountKey("sms", curUser.getId());
		if(selUser != null){
			if("CZ00".equals(changeType)||"CZ01".equals(changeType)){//充值转入或手动返充
				if(!amountFlag(adminFlag, curKey, iAmount)){
					msg = "充值转入失败,余额不足";
					JedisUtils.incrBy(curKey, iAmount);
					return msg;
				}
				
				JedisUtils.incrBy(selKey, iAmount);
				int code = baseAccountService.recharge(baseAccount,selUser,curUser,adminFlag);
				if(code == 0){
					msg = "操作失败，请重试";
					if(!adminFlag)JedisUtils.incrBy(curKey, iAmount);
					JedisUtils.decrBy(selKey, iAmount);
					return msg;
				}
				
			}else if("XF01".equals(changeType)){//手动扣款
				if(!amountFlag(false, selKey, iAmount)){
					msg = "手动扣款失败,余额不足";
					JedisUtils.incrBy(selKey, iAmount);
					return msg;
				}
				if(!adminFlag)JedisUtils.incrBy(curKey, iAmount);
				int code = baseAccountService.recharge(baseAccount,selUser,curUser,adminFlag);
				if(code == 0){
					msg = "操作失败，请重试";
					if(!adminFlag)JedisUtils.decrBy(curKey, iAmount);
					JedisUtils.incrBy(selKey, iAmount);
					return msg;
				}
			}
			
		}else{
			msg = "账户信息错误，请联系管理员";
		}
		
		return msg;
	}
	
	//判断余额是否足够 true:余额足够 false:否
	private boolean amountFlag(boolean adminFlag,String userKey,int amount){
		if(adminFlag)return true;
		if(JedisUtils.decrBy(userKey, amount) >=0)return true;
		return false;
	}
	
	private String redirect(String msg,String accType,RedirectAttributes redirectAttributes){
		addMessage(redirectAttributes, msg);
		return "redirect:"+Global.getAdminPath()+"/account/baseAccount/rechargeInit?accType="+accType;
	}

}