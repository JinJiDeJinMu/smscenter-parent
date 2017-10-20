package com.sanerzone.smscenter.biz.service.impl;

import com.sanerzone.smscenter.biz.entity.SmsSendInterface;
import com.sanerzone.smscenter.biz.mapper.SmsSendInterfaceMapper;
import com.sanerzone.smscenter.biz.service.ISmsSendInterfaceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XuRui
 * @since 2017-07-26
 */
@Service
public class SmsSendInterfaceServiceImpl extends ServiceImpl<SmsSendInterfaceMapper, SmsSendInterface> implements ISmsSendInterfaceService {
	
	public List<SmsSendInterface> findSWSendList(SmsSendInterface param){
		return baseMapper.findSWSendList(param);
	}
	
	public int updateSendStatus(SmsSendInterface param){
		return baseMapper.updateSendStatus(param);
	}
}
