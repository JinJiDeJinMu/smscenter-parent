package com.sanerzone.smscenter.biz.iface;

/**
 * 集群消费
 * 
 * @author Administrator
 *
 */
public interface SmsClusterInterface {
	//查找敏感词
	public String findKeywords(String content);
}
