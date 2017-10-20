package com.sanerzone.smscenter.biz.config;

import com.sanerzone.smscenter.biz.cache.PhoneHelper;
import com.sanerzone.smscenter.biz.cache.SysBlacklistHelper;
import com.sanerzone.smscenter.biz.cache.UserBlacklistHelper;
import com.sanerzone.smscenter.biz.cache.WhitelistHelper;
import com.sanerzone.smscenter.biz.iface.PhoneConfigInterface;

public class PhoneConfigInterfaceImpl implements PhoneConfigInterface{

	@Override
	public boolean configPhoneInfo(int type, String phone, String phoneType, String cityCode) {
		switch (type) {
		case 1:
			PhoneHelper.put(phone, phoneType, cityCode);
			break;
		case 2:
			PhoneHelper.del(phone);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean configSysBlacklist(int type, String phone, String[] phones) {
		switch (type) {
		case 1:
			SysBlacklistHelper.put(phones);
			break;
		case 2:
			SysBlacklistHelper.del(phone);
			break;
		case 3:
			SysBlacklistHelper.del(phones);
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean configUserBlacklist(int type, String phone, String userid, String[] phones) {
		switch (type) {
		case 1:
			UserBlacklistHelper.put(phones, userid);
			break;
		case 2:
			UserBlacklistHelper.del(userid, phone);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean configWhitelist(int type, String phone, String[] phones) {
		switch (type) {
		case 1:
			WhitelistHelper.put(phones);
			break;
		case 2:
			WhitelistHelper.del(phone);
			break;
		default:
			break;
		}
		return false;
	}

}
