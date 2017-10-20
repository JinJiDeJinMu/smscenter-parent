package com.sanerzone.smscenter.gateway.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.SmsGateway;

public interface SmsGatewayMapper extends BaseMapper<SmsGateway>{
	/**
	 * 网关初始化，根据应用编码获取启用（<2）的网关
	 * @param smsGateway
	 * @return
	 */
	public List<SmsGateway> findList(SmsGateway smsGateway);
	
	/**
	 * 根据网关编码获取网关信息
	 * @param smsGateway
	 * @return
	 */
	public SmsGateway findByGwCode(String gwCode);
	
	/**
	 * 更新网关状态
	 * @param smsGateway
	 */
	public void updateStatus(SmsGateway smsGateway);
}
