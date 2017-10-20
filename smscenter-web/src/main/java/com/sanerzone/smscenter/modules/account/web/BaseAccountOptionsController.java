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
import com.sanerzone.smscenter.modules.account.entity.BaseAccountOptions;
import com.sanerzone.smscenter.modules.account.service.BaseAccountOptionsService;

/**
 * 账号属性Controller
 * @author zhukc
 * @version 2017-03-03
 */
@Controller
@RequestMapping(value = "${adminPath}/account/baseAccountOptions")
public class BaseAccountOptionsController extends BaseController {

	@Autowired
	private BaseAccountOptionsService baseAccountOptionsService;
	
	@ModelAttribute
	public BaseAccountOptions get(@RequestParam(required=false) String id) {
		BaseAccountOptions entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseAccountOptionsService.get(id);
		}
		if (entity == null){
			entity = new BaseAccountOptions();
		}
		return entity;
	}
	
	@RequiresPermissions("account:baseAccountOptions:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseAccountOptions baseAccountOptions, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseAccountOptions> page = baseAccountOptionsService.findPage(new Page<BaseAccountOptions>(request, response), baseAccountOptions); 
		model.addAttribute("page", page);
		return "modules/account/baseAccountOptionsList";
	}

	@RequiresPermissions("account:baseAccountOptions:view")
	@RequestMapping(value = "form")
	public String form(BaseAccountOptions baseAccountOptions, Model model) {
		model.addAttribute("baseAccountOptions", baseAccountOptions);
		return "modules/account/baseAccountOptionsForm";
	}

	@RequiresPermissions("account:baseAccountOptions:edit")
	@RequestMapping(value = "save")
	public String save(BaseAccountOptions baseAccountOptions, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseAccountOptions)){
			return form(baseAccountOptions, model);
		}
		baseAccountOptionsService.save(baseAccountOptions);
		addMessage(redirectAttributes, "保存账号属性成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccountOptions/?repage";
	}
	
	@RequiresPermissions("account:baseAccountOptions:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseAccountOptions baseAccountOptions, RedirectAttributes redirectAttributes) {
		baseAccountOptionsService.delete(baseAccountOptions);
		addMessage(redirectAttributes, "删除账号属性成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseAccountOptions/?repage";
	}

}