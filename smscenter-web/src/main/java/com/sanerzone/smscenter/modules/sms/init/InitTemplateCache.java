package com.sanerzone.smscenter.modules.sms.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanerzone.smscenter.common.utils.TemplateCacheHelper;
import com.sanerzone.smscenter.modules.sms.entity.SmsTemplate;
import com.sanerzone.smscenter.modules.sms.service.SmsTemplateService;

@Service
public class InitTemplateCache {
		
	private Logger logger = LoggerFactory.getLogger(InitTemplateCache.class);
	
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@PostConstruct
	public void init(){
		int size = 0;
		List<SmsTemplate> list = smsTemplateService.findList(new SmsTemplate());
		if(list != null && list.size() >0){
			size = list.size();
			for (SmsTemplate entity : list) {
				TemplateCacheHelper.put(entity.getId(), entity);
			}
		}
		logger.info("模板缓存加载完成,模板个数:"+size);
	}
	
}
