package com.sanerzone.smscenter.biz.mapper;

import com.sanerzone.smscenter.biz.entity.SmsSendInterface;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author XuRui
 * @since 2017-07-26
 */
public interface SmsSendInterfaceMapper extends BaseMapper<SmsSendInterface> {
	
	public List<SmsSendInterface> findSWSendList(SmsSendInterface param);
	
	public int updateSendStatus(SmsSendInterface param);
}