/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsTask;
import com.sanerzone.smscenter.modules.sms.service.SmsTaskService;

/**
 * 短信任务Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsTask")
public class SmsTaskController extends BaseController {

	@Autowired
	private SmsTaskService smsTaskService;
	
	@ModelAttribute
	public SmsTask get(@RequestParam(required=false) String id) {
		SmsTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsTaskService.get(id);
		}
		if (entity == null){
			entity = new SmsTask();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsTask:view")
	@RequestMapping(value = "recheckList")
	public String recheckList(SmsTask smsTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		smsTask.setSendStatus("-1");//待审
		Page<SmsTask> page = smsTaskService.findPage(new Page<SmsTask>(request, response), smsTask); 
		model.addAttribute("page", page);
		return "modules/sms/smsTaskRecheckList";
	}
	
	@RequiresPermissions("sms:smsTask:view")
	@RequestMapping(value ="recheckSms")
	public String recheckSms(String ids,String sendStatus,String recheckRemarks,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String[] array = ids.split(";");
		if(array != null && array.length > 0){
			SmsTask param;
			User user = UserUtils.getUser();
			
			try {
				if(StringUtils.isNotBlank(recheckRemarks)){
					recheckRemarks = URLDecoder.decode(recheckRemarks, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			for (String id : array) {
				param = new SmsTask();
				param.setTaskid(id);
				param.setSendStatus(sendStatus);
				param.setRecheckRemarks(recheckRemarks);
				param.setUser(user);
				smsTaskService.recheckSmsContent(param);
			}
		}
		addMessage(redirectAttributes, "短信审核成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsTask/recheckList";
	}
	
	@RequiresPermissions("sms:smsTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsTask smsTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsTask> page = smsTaskService.findPage(new Page<SmsTask>(request, response), smsTask); 
		model.addAttribute("page", page);
		return "modules/sms/smsTaskList";
	}

	@RequiresPermissions("sms:smsTask:view")
	@RequestMapping(value = "form")
	public String form(SmsTask smsTask, Model model) {
		model.addAttribute("smsTask", smsTask);
		return "modules/sms/smsTaskForm";
	}

	@RequiresPermissions("sms:smsTask:edit")
	@RequestMapping(value = "save")
	public String save(SmsTask smsTask, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsTask)){
			return form(smsTask, model);
		}
		smsTaskService.save(smsTask);
		addMessage(redirectAttributes, "保存短信任务成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsTask/?repage";
	}
	
	@RequiresPermissions("sms:smsTask:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsTask smsTask, RedirectAttributes redirectAttributes) {
		smsTaskService.delete(smsTask);
		addMessage(redirectAttributes, "删除短信任务成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsTask/?repage";
	}

}