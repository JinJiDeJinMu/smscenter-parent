package com.sanerzone.smscenter.biz.service.impl;

import com.sanerzone.smscenter.biz.entity.SmsTaskData;
import com.sanerzone.smscenter.biz.mapper.SmsTaskDataMapper;
import com.sanerzone.smscenter.biz.service.ISmsTaskDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XuRui
 * @since 2017-07-14
 */
@Service
public class SmsTaskDataServiceImpl extends ServiceImpl<SmsTaskDataMapper, SmsTaskData> implements ISmsTaskDataService {
	
	//获取发送
	public String getTaskData(String taskid){
		return baseMapper.findTaskData(taskid);
	}
}
