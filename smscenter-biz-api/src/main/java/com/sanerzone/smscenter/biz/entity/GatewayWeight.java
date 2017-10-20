package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;

/**
 * 通道权重
 * @author Administrator
 *
 */
public class GatewayWeight implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String gwCode;//网关代码
	
	private int weight;	  //优先级
	
//	public GatewayWeight(){}
	public GatewayWeight (String gwCode, int weight) {
		this.gwCode = gwCode;
		this.weight = weight;
	}
	
	public String getGwCode() {
		return gwCode;
	}
	public void setGwCode(String gwCode) {
		this.gwCode = gwCode;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
}
