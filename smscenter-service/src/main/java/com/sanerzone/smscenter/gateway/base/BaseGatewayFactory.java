package com.sanerzone.smscenter.gateway.base;

import java.util.concurrent.ConcurrentHashMap;

import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.gateway.service.ISmsGatewayService;

public abstract class BaseGatewayFactory implements GatewayFactory {

	private ConcurrentHashMap<String, GateWay> gateWayMap = new ConcurrentHashMap<String, GateWay>();

	public void initGateway() {

	}

	public void closeAll() {

	}

	public abstract boolean closeGateway(String id);

	public abstract boolean closeGatewayTemp(String id);

	public abstract boolean openGateway(String id);

	public abstract boolean hasGateway(String id);

	public abstract Object getGateway(String gatewayId);

	public abstract Result sendMsg(SMSMTMessage msg);

	public abstract GateEnum getGatewayType(String gatewayId);

	public abstract SMSMTMessage getSubmitResult(String gID, String msgid);
	
	/**
	 * 设置网关速率
	 */
	public Result setGatewaySendRate(String gatewayId, int permitsPerSecond) {
		GateWay smsGateWay = getSmsGateWay(gatewayId);
		if( smsGateWay != null ) {
			smsGateWay.setRate(permitsPerSecond);
			return new Result("T0", "设置成功");
		} 
		
		return new Result("F1", "网关不存在");
	}
	
	protected GatewayService gatewayService;
	protected GateWayMessageAbstract gateWayMessage;
	protected ISmsGatewayService smsGatewayService;

	public ISmsGatewayService getSmsGatewayService() {
		return smsGatewayService;
	}

	public void setSmsGatewayService(ISmsGatewayService smsGatewayService) {
		this.smsGatewayService = smsGatewayService;
	}

	public void setGatewayService(GatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

	public void setGateWayMessage(GateWayMessageAbstract gateWayMessage) {
		this.gateWayMessage = gateWayMessage;
	}

	public GateWayMessageAbstract getGateWayMessage() {
		return this.gateWayMessage;
	}

	public void addSmsGateWay(String id, GateWay gateWay) {
		gateWayMap.put(id, gateWay);
	}

	public GateWay getSmsGateWay(String id) {
		return gateWayMap.get(id);
	}
	
	public boolean closev1 (String gateWayid){
		
		//关闭数据接收线程
		synchronized (gateWayid) {
			GateWay smsGateWay = getSmsGateWay(gateWayid);
			if( smsGateWay != null ) {
				smsGateWay.shutdown();
				gateWayMap.remove(gateWayid);
			}
		}
		
		//关闭发送线程
		closeGateway(gateWayid);
		
		return true;
	}

}
