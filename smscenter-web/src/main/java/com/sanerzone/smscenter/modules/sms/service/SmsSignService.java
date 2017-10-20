/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.smscenter.modules.sms.dao.SmsSignDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsSign;

/**
 * 通道签名Service
 * @author zhukc
 * @version 2016-07-29
 */
@Service
@Transactional(readOnly = true)
public class SmsSignService extends CrudService<SmsSignDao, SmsSign> {

	
	public SmsSign get(String id) {
		return super.get(id);
	}
	
	public SmsSign getByParam(SmsSign param) {
		return dao.getByParam(param);
	}
	
	/**
     * 获取用户通道签名
     * @param param
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<SmsSign> getUserGatewaySign(SmsSign param)
    {
        return dao.getUserGatewaySign(param);
    }
    
    /**
     * 账户下面已分配通道分组下的已经分配好的签名信息
     * @param page
     * @param param
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Page<SmsSign> getUserGatewaySingList(Page<SmsSign> page, SmsSign param)
    {
        param.setPage(page);
        page.setList(dao.getUserGatewaySingList(param));
        return page;
    }
	
	public List<SmsSign> findList(SmsSign smsSign) {
		return super.findList(smsSign);
	}
	
	public Page<SmsSign> findPage(Page<SmsSign> page, SmsSign smsSign) {
		return super.findPage(page, smsSign);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsSign smsSign) {
		super.save(smsSign);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsSign smsSign) {
		super.delete(smsSign);
		
	}
	
	private com.sanerzone.smscenter.modules.sms.entity.SmsSign toDest(SmsSign smsSign){
		com.sanerzone.smscenter.modules.sms.entity.SmsSign dest = new com.sanerzone.smscenter.modules.sms.entity.SmsSign();
		smsSign.setUserId(smsSign.getUser().getId());
		try {
			BeanUtils.copyProperties(dest, smsSign);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dest;
	}
	
}