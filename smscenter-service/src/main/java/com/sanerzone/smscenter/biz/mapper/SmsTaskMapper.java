package com.sanerzone.smscenter.biz.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.SmsTask;

public interface SmsTaskMapper extends BaseMapper<SmsTask>{
	public List<SmsTask> findPendingSendTask();
	public void updateRowNumber(Map<String,Object> map);
	public Integer updateSendStatus(Map<String,Object> map);
	public SmsTask get(String taskid);
}
