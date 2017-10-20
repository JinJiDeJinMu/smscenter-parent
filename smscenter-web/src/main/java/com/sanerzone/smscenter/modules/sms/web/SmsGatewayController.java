/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import java.util.List;

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
import com.sanerzone.smscenter.modules.sms.entity.SmsGateway;
import com.sanerzone.smscenter.modules.sms.service.SmsGatewayService;

/**
 * 通道信息Controller
 * @author zhukc
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsGateway")
public class SmsGatewayController extends BaseController {

	@Autowired
	private SmsGatewayService smsGatewayService;
	
	@ModelAttribute
	public SmsGateway get(@RequestParam(required=false) String id) {
		SmsGateway entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsGatewayService.get(id);
		}
		if (entity == null){
			entity = new SmsGateway();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsGateway:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsGateway smsGateway, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsGateway> page = smsGatewayService.findPage(new Page<SmsGateway>(request, response), smsGateway); 
		model.addAttribute("page", page);
		return "modules/sms/smsGatewayList";
	}

	@RequiresPermissions("sms:smsGateway:view")
	@RequestMapping(value = "form")
	public String form(SmsGateway smsGateway, Model model) {
		model.addAttribute("smsGateway", smsGateway);
		return "modules/sms/smsGatewayForm";
	}

	@RequiresPermissions("sms:smsGateway:edit")
	@RequestMapping(value = "save")
	public String save(SmsGateway smsGateway, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsGateway)){
			return form(smsGateway, model);
		}
		smsGatewayService.save(smsGateway);
		addMessage(redirectAttributes, "保存通道信息成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGateway/?repage";
	}
	
	@RequiresPermissions("sms:smsGateway:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsGateway smsGateway, RedirectAttributes redirectAttributes) {
		smsGatewayService.delete(smsGateway);
		addMessage(redirectAttributes, "删除通道信息成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGateway/?repage";
	}
	
	/**
	 * 根据ID获取网关信息
	 * @param gatewayId
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequiresPermissions("sms:smsGateway:view")
    @RequestMapping(value = "getGatewayInfo")
    public String getGatewayInfo(String gwCode, HttpServletResponse response, HttpServletRequest request, Model model)
    {
//缓存待实现        JmsgGatewayInfo entity = GatewayUtils.getGatewayInfo(gatewayId);
//        
//        if (entity == null || StringUtils.isBlank(entity.getSpNumber()))
//        {
		SmsGateway entity = smsGatewayService.get(gwCode);
//   }
        
        return renderString(response, entity);
    }

	/**
	 * 获取网关信息列表
	 * @param jmsgGatewayInfo
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGatewayInfoList")
	public String getGatewayInfoList(SmsGateway smsGateway, String type, HttpServletResponse response, HttpServletRequest request, Model model)
	{
		if (StringUtils.isNotBlank(type))
		{
			smsGateway.setGwType(type);
		}
		
		List<SmsGateway> list = smsGatewayService.findList(smsGateway);
		return renderString(response, list);
	}

}