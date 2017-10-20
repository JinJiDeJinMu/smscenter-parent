package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RoundRobinWeight<T> implements Serializable{
	private static final long serialVersionUID = 857377105569713919L;
	
	private AtomicLong index = new AtomicLong();
	private List<T> gatewayWeights ; // new GatewayWeight[10];// 机器序号：权重

	public RoundRobinWeight(List<T> gatewayWeights) {
		this.gatewayWeights = gatewayWeights;
	}

	public List<T> getGatewayWeights() {
		return gatewayWeights;
	}

	public void setGatewayWeights(List<T> gatewayWeights) {
		this.gatewayWeights = gatewayWeights;
	}

	public AtomicLong getIndex() {
		return index;
	}

	public void setIndex(AtomicLong index) {
		this.index = index;
	}

	
}
