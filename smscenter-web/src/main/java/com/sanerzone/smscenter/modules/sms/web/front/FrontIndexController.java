/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web.front;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 账号信息Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front")
public class FrontIndexController extends BaseController {
	
	@RequestMapping(value = "view/{pageView}")
	public String pageView(@PathVariable String pageView, HttpServletResponse response) {
		return "modules/sms/front/" + pageView;
	}
	
	@RequestMapping(value = "logout")
	public String logout()
	{
	
		Subject subject = SecurityUtils.getSubject();
		if (subject != null){
			subject.logout();
		}
		return "modules/sms/front/login";
	}
}