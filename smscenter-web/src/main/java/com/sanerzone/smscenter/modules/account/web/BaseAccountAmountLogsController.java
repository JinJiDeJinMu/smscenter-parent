/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountAmountLogs;
import com.sanerzone.smscenter.modules.account.service.BaseAccountAmountLogsService;

/**
 * 账务变动日志Controller
 * @author zhukc
 * @version 2017-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/account/baseAccountAmountLogs")
public class BaseAccountAmountLogsController extends BaseController {

	@Autowired
	private BaseAccountAmountLogsService baseAccountAmountLogsService;
	
	@ModelAttribute
	public BaseAccountAmountLogs get(@RequestParam(required=false) String id) {
		BaseAccountAmountLogs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseAccountAmountLogsService.get(id);
		}
		if (entity == null){
			entity = new BaseAccountAmountLogs();
		}
		return entity;
	}
	
	@RequiresPermissions("account:baseAccountAmountLogs:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseAccountAmountLogs baseAccountAmountLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseAccountAmountLogs> page = baseAccountAmountLogsService.findPage(new Page<BaseAccountAmountLogs>(request, response), baseAccountAmountLogs); 
		model.addAttribute("page", page);
		return "modules/account/baseAccountAmountLogsList";
	}

	@RequiresPermissions("account:baseAccountAmountLogs:view")
	@RequestMapping(value = "form")
	public String form(BaseAccountAmountLogs baseAccountAmountLogs, Model model) {
		model.addAttribute("baseAccountAmountLogs", baseAccountAmountLogs);
		return "modules/account/baseAccountAmountLogsForm";
	}

	@RequiresPermissions("account:baseAccountAmountLogs:edit")
	@RequestMapping(value = "save")
	public String save(BaseAccountAmountLogs baseAccountAmountLogs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseAccountAmountLogs)){
			return form(baseAccountAmountLogs, model);
		}
		baseAccountAmountLogsService.save(baseAccountAmountLogs);
		addMessage(redirectAttributes, "保存账务变动日志成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccountAmountLogs/?repage";
	}
	
	@RequiresPermissions("account:baseAccountAmountLogs:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseAccountAmountLogs baseAccountAmountLogs, RedirectAttributes redirectAttributes) {
		baseAccountAmountLogsService.delete(baseAccountAmountLogs);
		addMessage(redirectAttributes, "删除账务变动日志成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccountAmountLogs/?repage";
	}
	
	//下级用户充值消费明细
	@RequestMapping(value = "lowerUserDetailList")
	public String lowerUserDetailList(BaseAccountAmountLogs baseAccountAmountLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		baseAccountAmountLogs.setCreateBy(UserUtils.getUser());
		Page<BaseAccountAmountLogs> page = baseAccountAmountLogsService.findPage(new Page<BaseAccountAmountLogs>(request, response), baseAccountAmountLogs); 
		model.addAttribute("page", page);
		return "modules/account/smsLowerUserAccountLogsList";
	}

}