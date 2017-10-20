/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsDaySign;
import com.sanerzone.smscenter.modules.sms.service.SmsDaySignService;
import com.sanerzone.smscenter.modules.sms.service.SmsTaskService;
import com.sanerzone.smscenter.modules.sms.task.SmsDayReportTask;

/**
 * 日签名报表Controller
 * @author huangjie
 * @version 2017-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsDaySign")
public class SmsDaySignController extends BaseController {
    
	
	@Autowired
	private SmsDaySignService smsDaySignService;
	
	@Autowired
	private SmsTaskService smsTaskService;
	
	@ModelAttribute
	public SmsDaySign get(@RequestParam(required=false) String id) {
		SmsDaySign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsDaySignService.get(id);
		}
		if (entity == null){
			entity = new SmsDaySign();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsDaySign:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsDaySign smsDaySign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsDaySign> page = smsDaySignService.findPage(new Page<SmsDaySign>(request, response), smsDaySign); 
		model.addAttribute("page", page);
		return "modules/sms/smsDaySignList";
	}

	@RequiresPermissions("sms:smsDaySign:view")
	@RequestMapping(value = "form")
	public String form(SmsDaySign smsDaySign, Model model) {
		model.addAttribute("smsDaySign", smsDaySign);
		return "modules/sms/smsDaySignForm";
	}

	@RequiresPermissions("sms:smsDaySign:edit")
	@RequestMapping(value = "save")
	public String save(SmsDaySign smsDaySign, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsDaySign)){
			return form(smsDaySign, model);
		}
		smsDaySignService.save(smsDaySign);
		addMessage(redirectAttributes, "保存日签名报表成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsDaySign/?repage";
	}
	
	@RequiresPermissions("sms:smsDaySign:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsDaySign smsDaySign, RedirectAttributes redirectAttributes) {
		smsDaySignService.delete(smsDaySign);
		addMessage(redirectAttributes, "删除日签名报表成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsDaySign/?repage";
	}
    
	@Scheduled(fixedDelay=360000)
	public void execTask(){
		logger.info("进入报表定时任务:短信任务统计");
		String tableName = "sms_send_"+DateUtils.getDayOfMonth(0);
		smsTaskService.smsTaskReport(tableName, new Date());
	}
	
	
}