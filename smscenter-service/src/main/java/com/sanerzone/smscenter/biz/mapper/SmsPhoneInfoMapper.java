package com.sanerzone.smscenter.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.SmsPhoneInfo;

public interface SmsPhoneInfoMapper extends BaseMapper<SmsPhoneInfo>{
	public List<SmsPhoneInfo> findPhoneInfoList();
}
