package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sanerzone.smscenter.biz.entity.GatewayWeight;
import com.sanerzone.smscenter.biz.entity.RoundRobinWeight;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel;
import com.sanerzone.smscenter.biz.utils.RoundRobinWeightHelper;

/**
 * 
 * 通道分组关系缓存 key分组ID, value=Map<运营商+省份, List>
 * 
 * @author Administrator
 * 
 */
public class GatewayRelHelper {

	//TODO 内存缓存
	private static Map<String, Serializable> map = Maps.newHashMap(); // BDBStoredMapFactoryImpl.INS.buildMap("sms_gateway_group_rel","gatewayrel_info");// 通道分组关系

	/**
	 * 初始化通道分组关系
	 * 
	 * @param list
	 */
	public static void init(List<SmsGatewayGroupRel> list) {

		if (list == null || list.isEmpty()) {
			map.clear();
			return;
		}

		Set<String> groupEnabled = Sets.newHashSet();

		Map<String, List<SmsGatewayGroupRel>> groupRelMap = Maps.newHashMap();
		for (SmsGatewayGroupRel item : list) {
			groupEnabled.add(item.getGroupId());

			List<SmsGatewayGroupRel> groupRelList = groupRelMap.get(item.getGroupId());
			if (groupRelList == null) {
				groupRelMap.put(item.getGroupId(), Lists.newArrayList(item));
			} else {
				groupRelList.add(item);
			}
		}

		// 持久化通道分组关系
		for (Entry<String, List<SmsGatewayGroupRel>> item : groupRelMap.entrySet()) {
			initGatewayGroupRel(item.getKey(), item.getValue());
		}

		// 清理Map
		for (String groupId : map.keySet()) {
			if (!groupEnabled.contains(groupId)) {
				map.remove(groupId);
			}
		}
	}

	public static void initGatewayGroupRel(String groupId, List<SmsGatewayGroupRel> smsGatewayGroupRel) {

		if (smsGatewayGroupRel == null || smsGatewayGroupRel.isEmpty()) {
			map.remove(groupId);
			return;
		}

		Map<String, List<SmsGatewayGroupRel>> groupRelMap = Maps.newHashMap();
		for (SmsGatewayGroupRel item : smsGatewayGroupRel) {
			String key = getVKey(item.getPhoneType(), item.getProvinceId());
			List<SmsGatewayGroupRel> list = groupRelMap.get(key);
			if (list == null) {
				groupRelMap.put(key, Lists.newArrayList(item));
			} else {
				list.add(item);
			}
		}

		Map<String, RoundRobinWeight<GatewayWeight>> groupWeightMap = Maps.newHashMap();
		for (Entry<String, List<SmsGatewayGroupRel>> entry : groupRelMap.entrySet()) {
			groupWeightMap.put(entry.getKey(), initGatewayGroupRelWeight(entry.getValue()));
		}

		// 持久化到bdb
		map.put(groupId, (Serializable) groupWeightMap);
	}

	private static RoundRobinWeight<GatewayWeight> initGatewayGroupRelWeight(List<SmsGatewayGroupRel> smsGatewayGroupRel) {
		List<GatewayWeight> gatewayWeightList = Lists.newArrayList();
		for (SmsGatewayGroupRel item : smsGatewayGroupRel) {
			gatewayWeightList.add(new GatewayWeight(item.getGwCode(), item.getLevel()));
		}
		// GatewayWeight[] gatewayWeights = (GatewayWeight[])
		// gatewayWeightList.toArray();
		return new RoundRobinWeight<GatewayWeight>(gatewayWeightList);
	}

	// 组装 value 主键
	private static String getVKey(String phoneType, String provinceId) {
		return phoneType + "_" + provinceId;
	}

	/**
	 * 删除通道分组关系
	 * 
	 * @param groupId
	 */
	public static void del(String groupId) {
		map.remove(groupId);
	}

	/**
	 * 获取通道分组关系
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, RoundRobinWeight<GatewayWeight>> get(String groupId) {
		Object obj = map.get(groupId);
		if (obj != null) {
			return (Map<String, RoundRobinWeight<GatewayWeight>>) obj;
		}
		return null;
	}

	public static SmsGateway getGwCode(String groupId, String phoneType, String provinceId) {
		Map<String, RoundRobinWeight<GatewayWeight>> gwGroupWeight = get(groupId);
		if (gwGroupWeight == null) {
			return null;
		}

		RoundRobinWeight<GatewayWeight> roundRobinWeight = gwGroupWeight.get(getVKey(phoneType, provinceId));
		if (roundRobinWeight == null) {
			roundRobinWeight = gwGroupWeight.get(getVKey(phoneType, "10"));
			if (roundRobinWeight == null) {
				return null;
			}
		}
		
		// 获取可用通道
		List<SmsGateway> availableCollection = getAvailableCollection(roundRobinWeight);
		return RoundRobinWeightHelper.getSmsGateway(roundRobinWeight.getIndex(), availableCollection);
	}
	
	/**
	 * 获取可用通道列表
	 * @param roundRobinWeight 
	 * @return
	 */
	public static List<SmsGateway> getAvailableCollection(RoundRobinWeight<GatewayWeight> roundRobinWeight) {
		List<GatewayWeight> gatewayWeights = roundRobinWeight.getGatewayWeights();
		if (gatewayWeights == null || gatewayWeights.isEmpty()) {
			return null;
		}
		
		List<SmsGateway> collection = Lists.newArrayList();
		for (GatewayWeight gatewayWeight : gatewayWeights) {
			SmsGateway smsGateway = GatewayCacheHelper.get(gatewayWeight.getGwCode());
			if (smsGateway != null && smsGateway.getGwStatus() == 1) {// 运行中
				int weight = gatewayWeight.getWeight();
				for (int i = 0; i < weight; i++) {
					collection.add(smsGateway);
				}
			}
		}
		return collection;
	}

}
