package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sanerzone.smscenter.biz.entity.RoundRobinWeight;
import com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid;
import com.sanerzone.smscenter.biz.entity.TopicQueue;
import com.sanerzone.smscenter.biz.utils.RoundRobinWeightHelper;

//队列缓存
public class GatewayQueueHelper {

	//TODO 内存缓存
	private static Map<String, Serializable> map = Maps.newHashMap();
	
	/**
	 * 初始化通道队列
	 * @param list
	 */
	public static void init(List<SmsGatewayQueueid> list){
		if (list == null || list.isEmpty()) {
			map.clear();
			return;
		}

		Set<String> queueEnabled = Sets.newHashSet();

		Map<String, List<SmsGatewayQueueid>> quequeMap = Maps.newHashMap();
		for (SmsGatewayQueueid item : list) {
			queueEnabled.add(item.getGwCode());

			List<SmsGatewayQueueid> groupRelList = quequeMap.get(item.getGwCode());
			if (groupRelList == null) {
				quequeMap.put(item.getGwCode(), Lists.newArrayList(item));
			} else {
				groupRelList.add(item);
			}
		}

		// 持久化通道分组关系
		for (Entry<String, List<SmsGatewayQueueid>> item : quequeMap.entrySet()) {
			initGatewayQueue(item.getKey(), item.getValue());
		}

		// 清理Map
		for (String groupId : map.keySet()) {
			if (!queueEnabled.contains(groupId)) {
				map.remove(groupId);
			}
		}
	}
	
	/**
	 * 初始化单个通道队列
	 * 
	 * @param gwCode
	 * @param gatewayQueue
	 */
	public static void initGatewayQueue(String gwCode, List<SmsGatewayQueueid> gatewayQueue) {

		if (gatewayQueue == null || gatewayQueue.isEmpty()) {
			map.remove(gwCode);
			return;
		}
		
		Map<String, List<SmsGatewayQueueid>> queueMap = Maps.newHashMap();
		for (SmsGatewayQueueid item : gatewayQueue) {
			String key = item.getServiceid();
			List<SmsGatewayQueueid> list = queueMap.get(key);
			if (list == null) {
				queueMap.put(key, Lists.newArrayList(item));
			} else {
				list.add(item);
			}
		}

		Map<String, RoundRobinWeight<TopicQueue>> topicMap = Maps.newHashMap();
		for (Entry<String, List<SmsGatewayQueueid>> entry : queueMap.entrySet()) {
			topicMap.put(entry.getKey(), initGatewayGroupRelWeight(entry.getValue()));
		}

		map.put(gwCode, (Serializable) topicMap);
	}
	
	private static RoundRobinWeight<TopicQueue> initGatewayGroupRelWeight(List<SmsGatewayQueueid> smsGatewayQueueid) {
		List<TopicQueue> topicQueueList = Lists.newArrayList();
		for (SmsGatewayQueueid item : smsGatewayQueueid) {
			topicQueueList.add(new TopicQueue(item.getTopic(),item.getQueueId(),item.getWeight()));
		}
		return new RoundRobinWeight<TopicQueue>(topicQueueList);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,RoundRobinWeight<TopicQueue>> get(String gwCode){
		Object obj = map.get(gwCode);
		if(obj == null) {
			return null;
		}
		return (Map<String,RoundRobinWeight<TopicQueue>>) obj;
	}
	
	public static TopicQueue getTopicQueue(String gwCode, String bizModel) {
		
		Map<String,RoundRobinWeight<TopicQueue>> bizModelTopicQueues = get(gwCode);
		if(bizModelTopicQueues == null || bizModelTopicQueues.isEmpty()) {
			return null;
		}
		
		RoundRobinWeight<TopicQueue> roundRobinWeight = bizModelTopicQueues.get(bizModel);
		if(roundRobinWeight.getGatewayWeights() == null || roundRobinWeight.getGatewayWeights().isEmpty()) {
			return null;
		}
		
		return RoundRobinWeightHelper.getRoundRobin(roundRobinWeight.getIndex(), roundRobinWeight.getGatewayWeights());
	}
	
	/**
	 * 删除通道队列
	 * 
	 * @param gwCode
	 */
	public static void del(String gwCode) {
		map.remove(gwCode);
	}
}
