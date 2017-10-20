/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
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
import com.sanerzone.smscenter.modules.account.entity.BaseUserAmount;
import com.sanerzone.smscenter.modules.account.service.BaseUserAmountService;

/**
 * 用户额度Controller
 * @author zhukc
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/account/baseUserAmount")
public class BaseUserAmountController extends BaseController {

	@Autowired
	private BaseUserAmountService baseUserAmountService;
	
	@ModelAttribute
	public BaseUserAmount get(@RequestParam(required=false) String id) {
		BaseUserAmount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseUserAmountService.get(id);
		}
		if (entity == null){
			entity = new BaseUserAmount();
		}
		return entity;
	}
	
	@RequiresPermissions("account:baseUserAmount:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseUserAmount baseUserAmount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseUserAmount> page = baseUserAmountService.findPage(new Page<BaseUserAmount>(request, response), baseUserAmount); 
		model.addAttribute("page", page);
		return "modules/account/baseUserAmountList";
	}

	@RequiresPermissions("account:baseUserAmount:view")
	@RequestMapping(value = "form")
	public String form(BaseUserAmount baseUserAmount, Model model) {
		model.addAttribute("baseUserAmount", baseUserAmount);
		return "modules/account/baseUserAmountForm";
	}

	@RequiresPermissions("account:baseUserAmount:edit")
	@RequestMapping(value = "save")
	public String save(BaseUserAmount baseUserAmount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseUserAmount)){
			return form(baseUserAmount, model);
		}
		baseUserAmountService.save(baseUserAmount);
		addMessage(redirectAttributes, "保存用户额度成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserAmount/?repage";
	}
	
	@RequiresPermissions("account:baseUserAmount:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseUserAmount baseUserAmount, RedirectAttributes redirectAttributes) {
		baseUserAmountService.delete(baseUserAmount);
		addMessage(redirectAttributes, "删除用户额度成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserAmount/?repage";
	}

}