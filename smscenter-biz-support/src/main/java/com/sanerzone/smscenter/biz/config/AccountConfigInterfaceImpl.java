package com.sanerzone.smscenter.biz.config;

import java.util.Map;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.iface.AccountConfigInterface;

public class AccountConfigInterfaceImpl implements AccountConfigInterface {

	public boolean configAccount(int type,String accId,Map<String,Object> map) {
		switch (type) {
		case 1:
			AccountCacheHelper.put(accId, map);
			break;
		case 2:
			AccountCacheHelper.del(accId);
			break;
		default:
			break;
		}
		return false;
	}
}
