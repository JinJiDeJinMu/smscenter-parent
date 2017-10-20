package com.sanerzone.smscenter.biz.config;

import com.sanerzone.smscenter.biz.cache.KeywordsHelper;
import com.sanerzone.smscenter.biz.iface.SmsClusterInterface;

public class SmsClusterInterfaceImpl implements SmsClusterInterface{

	@Override
	public String findKeywords(String content) {
		return KeywordsHelper.keywords(content);
	}

}
