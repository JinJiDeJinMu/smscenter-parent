package com.sanerzone.smscenter.gateway.base;

import java.io.Serializable;

import com.sanerzone.smscenter.biz.message.SMSMTMessage;

public interface GatewayFactory {
	
	void initGateway(String appCode);

	void closeAll();

	boolean closeGateway(String id);

	boolean closeGatewayTemp(String id);

	boolean openGateway(String id);

	boolean hasGateway(String id);

	Object getGateway(String gatewayId);

	Result sendMsg(SMSMTMessage msg);
	
	GateEnum getGatewayType(String gatewayId);
	
	SMSMTMessage getSubmitResult(String gID, String msgid);
	
	GateWayMessageAbstract getGateWayMessage();
	
	void addSmsGateWay(String id, GateWay gateWay);
	
	GateWay getSmsGateWay(String id);
	
	Result setGatewaySendRate(String gatewayId, int permitsPerSecond);
	
	boolean closev1(String id);
	
	void sendSmsSRMessage (Serializable message, String gateWayID);
	
	
}
