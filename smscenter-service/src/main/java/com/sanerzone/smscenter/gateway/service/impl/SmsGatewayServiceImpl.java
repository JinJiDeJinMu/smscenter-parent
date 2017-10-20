package com.sanerzone.smscenter.gateway.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sanerzone.smscenter.biz.cache.GatewayCacheHelper;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.gateway.mapper.SmsGatewayMapper;
import com.sanerzone.smscenter.gateway.service.ISmsGatewayService;

@Service
public class SmsGatewayServiceImpl extends ServiceImpl<SmsGatewayMapper, SmsGateway> implements ISmsGatewayService {

	/**
	 * 网关初始化，根据应用编码获取启用（<2）的网关
	 * @param smsGateway
	 * @return
	 */
	public List<SmsGateway> findList(SmsGateway smsGateway)
	{
		return baseMapper.findList(smsGateway);
	}
	
	/**
	 * 根据网关编码获取网关信息
	 * @param smsGateway
	 * @return
	 */
	public SmsGateway findByGwCode(String gwCode)
	{
		return baseMapper.findByGwCode(gwCode);
	}
	
	/**
	 * 更新网关状态
	 * @param smsGateway
	 */
	public void updateStatus(SmsGateway smsGateway)
	{
		baseMapper.updateStatus(smsGateway);
		
		//TODO 刷新缓存，暂时只刷新自己，后续根据使用改为dubbo通知
		GatewayCacheHelper.put(smsGateway.getGwCode(), smsGateway);
	}
}
