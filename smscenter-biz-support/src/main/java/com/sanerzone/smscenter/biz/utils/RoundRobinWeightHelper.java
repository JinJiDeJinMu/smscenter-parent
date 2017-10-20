package com.sanerzone.smscenter.biz.utils;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.sanerzone.common.support.sequence.DefaultSequenceNumberUtil;
import com.sanerzone.smscenter.biz.entity.SmsGateway;

public class RoundRobinWeightHelper implements Serializable {
	private static final long serialVersionUID = 857377105569713919L;

	private RoundRobinWeightHelper(){}
	
	/**
	 * 权重轮循，返回可用短信网关
	 * @param index 权重索引
	 * @param availableCollection 可用通道集合
	 * @return
	 */
	public static SmsGateway getSmsGateway(AtomicLong index, List<SmsGateway> availableCollection) {
		return (SmsGateway) getRoundRobin(index, availableCollection);
	}
	
	public static synchronized <E> E getRoundRobin(AtomicLong index, List<E> availableCollection) {
		if(availableCollection == null || availableCollection.isEmpty()) {
			return null;
		}
		
		int size = availableCollection.size();
		if (size == 0)
			return null;

		int idx = (int) DefaultSequenceNumberUtil.getNextAtomicValue(index, 65535L);
		return (E) availableCollection.get(idx % size);
	}
	
	public static void main(String[] args) {
		RoundRobinWeightHelper.getSmsGateway(new AtomicLong(), null);
	}

}
