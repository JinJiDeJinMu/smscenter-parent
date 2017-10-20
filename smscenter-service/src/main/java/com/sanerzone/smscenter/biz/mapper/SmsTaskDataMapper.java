package com.sanerzone.smscenter.biz.mapper;

import com.sanerzone.smscenter.biz.entity.SmsTaskData;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author XuRui
 * @since 2017-07-14
 */
public interface SmsTaskDataMapper extends BaseMapper<SmsTaskData> {
	
	public String findTaskData(String taskid);
}