package com.sanerzone.smscenter.modules.sms.service;

//@Service
public class PublicSmsSendService {
//	Logger logger = LoggerFactory.getLogger(PublicSmsSendService.class);
//
//	@Autowired
//	private MQHelper mQHelper;
//
//	public String sendSms(String group, String gateway, String bizid, SmsSend msg) throws Exception {
//		SmsMtMessage mtMsg = new SmsMtMessage();
//		mtMsg.setId(msg.getId());
//		mtMsg.setTaskid(msg.getTaskid());
//		mtMsg.setUserid(msg.getUser().getId());
//		mtMsg.setGateWayID(msg.getGatewayId());
//		mtMsg.setPayType(msg.getFeeType());
//		mtMsg.setCstmOrderID(msg.getCustomServiceid());
//		mtMsg.setUserReportNotify(msg.getUserNotifyStatus());
//		mtMsg.setUserReportGateWayID(msg.getSourceGatewayId());
//		mtMsg.setMsgContent(msg.getSmsContent());
//		mtMsg.setPhone(msg.getPhone());
//		mtMsg.setSpNumber(msg.getSpnumber());
//		mtMsg.setSmsType("sms");
//
//		return mQHelper.sendSyncMsgId(group, gateway, bizid, FstObjectSerializeUtil.write(mtMsg));
//	}
//	
//	public String sendSms(String gateway, String bizid, SmsMtMessage msg) throws Exception {
//        
//        String appCode = GatewayUtils.getAppCode(gateway);// 应用代码
//
//        return mQHelper.sendSyncMsgId(CacheKeys.getSmsSingleTopic(), appCode, bizid, FstObjectSerializeUtil.write(msg));
//    }
//
//	public Map<String, String> sendHandler(SmsSend entity) {
//
//		String id = entity.getId();
//		String phone = entity.getPhone();
//
//		Map<String, String> map = Maps.newHashMap();
//		String sendStatus = "F000";
//		String msgid = "";
//		map.put("id", id);
//		
//		String limit = DictUtils.getDictValue(entity.getUser().getId(), "user_month_limit",  "-1");
//		try {
//			if (WhitelistUtils.isExist(phone) || StringUtils.equals(limit, "0")) {// 判断是否是白名单号码
//				Map<String, String> sendMap = sendSms(entity);
//				sendStatus = sendMap.get("sendStatus");
//				msgid = sendMap.get("msgid");
//			} else {
//				String dayKey = CacheKeys.getCacheDaySmsSendKey(phone);
//				String monthKey = CacheKeys.getCacheMonthSmsSendKey(phone);
//				long dayKeyValue = JedisClusterUtils.incr(dayKey, DateUtils.getEndDayTime());// 一天内发送次数+1
//				long monthKeyValue = JedisClusterUtils.incr(monthKey, DateUtils.getEndMonthTime());// 一月内发送次数+1
//				if (dayKeyValue > 50) {// 判断号码是否一天内发送50次以上 F3
//					sendStatus = "F003";
//				} else {
//					if (monthKeyValue > 300) {// 判断号码是否一个月内发送300次以上 F4
//						sendStatus = "F004";
//					} else {
//						Map<String, String> sendMap = sendSms(entity);
//						sendStatus = sendMap.get("sendStatus");
//						msgid = sendMap.get("msgid");
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			logger.error("{}", e);
//		}
//		map.put("sendStatus", sendStatus);
//		map.put("msgid", msgid);
//		return map;
//	}
//	
//	public Map<String, String> sendHandler(SmsMtMessage entity) {
//
//        String id = entity.getId();
//        String phone = entity.getPhone();
//
//        Map<String, String> map = Maps.newHashMap();
//        String sendStatus = "F000";
//        String msgid = "";
//        map.put("id", id);
//        
//        try {
//            if (WhitelistUtils.isExist(phone)) {// 判断是否是白名单号码
//                Map<String, String> sendMap = sendSms(entity);
//                sendStatus = sendMap.get("sendStatus");
//                msgid = sendMap.get("msgid");
//            } else {
//                String dayKey = CacheKeys.getCacheDaySmsSendKey(phone);
//                String monthKey = CacheKeys.getCacheMonthSmsSendKey(phone);
//                long dayKeyValue = JedisClusterUtils.incr(dayKey, DateUtils.getEndDayTime());// 一天内发送次数+1
//                long monthKeyValue = JedisClusterUtils.incr(monthKey, DateUtils.getEndMonthTime());// 一月内发送次数+1
//                if (dayKeyValue > 50) {// 判断号码是否一天内发送50次以上 F3
//                    sendStatus = "F003";
//                } else {
//                    if (monthKeyValue > 300) {// 判断号码是否一个月内发送300次以上 F4
//                        sendStatus = "F004";
//                    } else {
//                        Map<String, String> sendMap = sendSms(entity);
//                        sendStatus = sendMap.get("sendStatus");
//                        msgid = sendMap.get("msgid");
//                    }
//                }
//            }
//            
//        } catch (Exception e) {
//            logger.error("{}", e);
//        }
//        map.put("sendStatus", sendStatus);
//        map.put("msgid", msgid);
//        return map;
//    }
//
//	/**
//	 * 提交队列
//	 * 
//	 * @param bizid
//	 * @param phone
//	 * @param smsContent
//	 * @param spNumber
//	 * @param group
//	 * @param gateWayId
//	 * @param taskid
//	 * @param userId
//	 * @return
//	 * @throws Exception 
//	 */
//	public Map<String, String> sendSms(SmsSend entity) throws Exception {
//
//		Map<String, String> map = Maps.newHashMap();
//
//		// 发送号码
//		String msgid = "-1";
//		try {
//				msgid = sendSms("SMSMT"+GatewayUtils.getAppCode(entity.getGatewayId())+"B", entity.getGatewayId()+"_LOW", entity.getId(), entity);
//		} catch (Exception e) {
//
//		}
//		String sendStatus = "T000";
//		if ("-1".equals(msgid)) {
//			sendStatus = "F0074";
//		}
//
//		map.put("sendStatus", sendStatus);
//		map.put("msgid", msgid);
//		return map;
//	}
//	
//	public Map<String, String> sendSms(SmsMtMessage entity) {
//
//        Map<String, String> map = Maps.newHashMap();
//
//        // 发送号码
//        String msgid = null;
//        try {
//            msgid = sendSms(entity.getGateWayID(), entity.getId(), entity);
//        } catch (Exception e) {
//
//        }
//        String sendStatus = "T000";
//        if ("-1".equals(msgid)) {
//            sendStatus = "P001";// 重新发送
//            String againSendKey = CacheKeys.getCacheAgainSmsSendKey(entity.getPhone());
//            long agaginCount = JedisUtils.incr(againSendKey);
//            if (agaginCount > 3) {// 重新发送3次以上 F5
//                sendStatus = "F005";
//                String daySendKey = CacheKeys.getCacheDaySmsSendKey(entity.getPhone());
//                int dayValue = JedisUtils.getInt(daySendKey);
//
//                if (dayValue >= 2) {
//                	JedisUtils.set(daySendKey, "1", 0);
//                } else {
//                	JedisUtils.set(daySendKey, String.valueOf((0)), 0);
//                }
//                JedisUtils.decr(CacheKeys.getCacheMonthSmsSendKey(entity.getPhone()));
//
//                // 销毁key
//                JedisUtils.del(againSendKey);
//            }
//        }
//
//        map.put("sendStatus", sendStatus);
//        map.put("msgid", msgid);
//        return map;
//    }

}
