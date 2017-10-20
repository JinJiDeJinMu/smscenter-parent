/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import java.util.Calendar;

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
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsSend;
import com.sanerzone.smscenter.modules.sms.service.SmsSendService;

/**
 * 短信发送Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsSend")
public class SmsSendController extends BaseController {

	@Autowired
	private SmsSendService smsSendService;
	
	@ModelAttribute
	public SmsSend get(@RequestParam(required=false) String id) {
		SmsSend entity = null;
		if (StringUtils.isNotBlank(id)){
			String tableIndex = getTableIndex(id);
			if (Integer.parseInt(tableIndex) > 31 || Integer.parseInt(tableIndex) < 1){
				Calendar current = Calendar.getInstance();
				tableIndex = String.valueOf(current.get(Calendar.DAY_OF_MONTH));
			}
			SmsSend param = new SmsSend();
			param.setTableName("sms_send_" + tableIndex);
			param.setId(id);
			entity = smsSendService.getV2(param);
		}
		if (entity == null){
			entity = new SmsSend();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsSend:view")
	@RequestMapping(value = "index")
	public String index(SmsSend smsSend, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sms/smsSendList";
	}
	
	@RequiresPermissions("sms:smsSend:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsSend smsSend, HttpServletRequest request, HttpServletResponse response, Model model) {
		String tableName = "sms_send_"+DateUtils.getTableIndex(smsSend.getSendTimeQ());
		smsSend.setTableName(tableName);
		Page<SmsSend> page = smsSendService.findPage(new Page<SmsSend>(request, response), smsSend); 
		model.addAttribute("page", page);
		return "modules/sms/smsSendList";
	}

	@RequiresPermissions("sms:smsSend:view")
	@RequestMapping(value = "form")
	public String form(SmsSend smsSend, Model model) {
		model.addAttribute("smsSend", smsSend);
		return "modules/sms/smsSendForm";
	}

	@RequiresPermissions("sms:smsSend:edit")
	@RequestMapping(value = "save")
	public String save(SmsSend smsSend, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsSend)){
			return form(smsSend, model);
		}
		smsSendService.save(smsSend);
		addMessage(redirectAttributes, "保存短信发送成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsSend/?repage";
	}
	
	@RequiresPermissions("sms:smsSend:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsSend smsSend, RedirectAttributes redirectAttributes) {
		smsSendService.delete(smsSend);
		addMessage(redirectAttributes, "删除短信发送成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsSend/?repage";
	}
	
	/**
	 * 根据业务ID获取表名下标
	 * @param bizid
	 * @return
	 */
	public static String getTableIndex(String bizid){
		if (StringUtils.isNotBlank(bizid)){
			return String.valueOf(new MsgId(bizid).getDay());
		}else{
			return "";
		}
	}

}