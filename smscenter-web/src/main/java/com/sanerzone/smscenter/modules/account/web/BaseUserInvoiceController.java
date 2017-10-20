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
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoice;
import com.sanerzone.smscenter.modules.account.service.BaseUserInvoiceService;

/**
 * 用户发票Controller
 * @author zhukc
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/account/baseUserInvoice")
public class BaseUserInvoiceController extends BaseController {

	@Autowired
	private BaseUserInvoiceService baseUserInvoiceService;
	
	@ModelAttribute
	public BaseUserInvoice get(@RequestParam(required=false) String id) {
		BaseUserInvoice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseUserInvoiceService.get(id);
		}
		if (entity == null){
			entity = new BaseUserInvoice();
		}
		return entity;
	}
	
	@RequiresPermissions("account:baseUserInvoice:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseUserInvoice baseUserInvoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseUserInvoice> page = baseUserInvoiceService.findPage(new Page<BaseUserInvoice>(request, response), baseUserInvoice); 
		model.addAttribute("page", page);
		return "modules/account/baseUserInvoiceList";
	}

	@RequiresPermissions("account:baseUserInvoice:view")
	@RequestMapping(value = "form")
	public String form(BaseUserInvoice baseUserInvoice, Model model) {
		model.addAttribute("baseUserInvoice", baseUserInvoice);
		return "modules/account/baseUserInvoiceForm";
	}

	@RequiresPermissions("account:baseUserInvoice:edit")
	@RequestMapping(value = "save")
	public String save(BaseUserInvoice baseUserInvoice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseUserInvoice)){
			return form(baseUserInvoice, model);
		}
		baseUserInvoiceService.save(baseUserInvoice);
		addMessage(redirectAttributes, "保存用户发票成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserInvoice/?repage";
	}
	
	@RequiresPermissions("account:baseUserInvoice:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseUserInvoice baseUserInvoice, RedirectAttributes redirectAttributes) {
		baseUserInvoiceService.delete(baseUserInvoice);
		addMessage(redirectAttributes, "删除用户发票成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserInvoice/?repage";
	}

}