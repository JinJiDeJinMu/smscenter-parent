package com.sanerzone.smscenter.biz.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSURREQMessage;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.biz.utils.MessageExtUtil;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.common.tools.HttpRequest;
import com.sanerzone.smscenter.common.tools.MQHelper;

@Service
public class SMSURREQListener implements MessageListenerConcurrently {
	private Logger logger = LoggerFactory.getLogger(SMSURREQListener.class);

	@Autowired
	public MQHelper mQUtils;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

		Map<String, List<SMSURREQMessage>> map = Maps.newHashMap();
		try {
			for (MessageExt msg : msgs) {
				SMSURREQMessage urReqMessage = MessageExtUtil.convertMessageExt(SMSURREQMessage.class, msg);

				if (null == urReqMessage) {
					continue;
				}

				String userId = urReqMessage.getUserid();// 获取用户ID
				if (map.containsKey(userId)) {
					map.get(userId).add(urReqMessage);
				} else {
					map.put(userId, Lists.newArrayList(urReqMessage));
				}
			}

			if (map != null && map.size() > 0) {
				for (String userId : map.keySet()) {
					String url = AccountCacheHelper.getStringValue(userId, "callbackUrl", "");
					if (!StringHelper.startsWith(url.toLowerCase(), "http")) {
						continue;
					}
					List<SMSURREQMessage> list = map.get(userId);
					String param = null;
					String result = null;

					param = jsonResult(list);// 扣费方式 0:提交扣费 2:状态报告
					try {
						result = HttpRequest.sendPostJson(url, param, null, 3000);// 推送
					} catch (Exception e) {
					}

					logger.info("用户推送:URL:{},参数:{},响应:{}", url, param, result);
					if (StringHelper.isNotBlank(result) && result.length() > 100) {
						result = result.substring(0, 100);
					}
					
//					mQUtils.sendSMSTRES();
				}
			}
		} catch (Exception e) {
			logger.error("{}", e);
		} 
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	private String jsonResult(List<SMSURREQMessage> list) {
		Map<String, List<SMSURREQMessage>> map = Maps.newHashMap();
		for (SMSURREQMessage urReqMessage : list) {
			String taskid = urReqMessage.getTaskid();
			if (map.containsKey(taskid)) {
				map.get(taskid).add(urReqMessage);
			} else {
				map.put(taskid, Lists.newArrayList(urReqMessage));
			}
		}

		List<Map<String, String>> rList = Lists.newArrayList();
		Map<String, String> resultMap = null;

		for (String taskid : map.keySet()) {
			List<SMSURREQMessage> resultList = map.get(taskid);
			for (SMSURREQMessage entity : resultList) {
				resultMap = Maps.newHashMap();
				resultMap.put("taskid", taskid);
				resultMap.put("code", entity.getStat());
				resultMap.put("msg", "成功");
				resultMap.put("mobile", entity.getPhone());
				resultMap.put("messageid", entity.getMessageid());
				resultMap.put("time", DateHelper.getDate("yyyyMMddHHmmss"));
				rList.add(resultMap);
			}
		}

		return JSON.toJSONString(rList);
	}
}
