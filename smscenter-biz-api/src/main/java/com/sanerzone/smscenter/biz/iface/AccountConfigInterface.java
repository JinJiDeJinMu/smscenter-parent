package com.sanerzone.smscenter.biz.iface;

import java.util.Map;

public interface AccountConfigInterface {
	public boolean configAccount(int type,String accId,Map<String,Object> map);
}
