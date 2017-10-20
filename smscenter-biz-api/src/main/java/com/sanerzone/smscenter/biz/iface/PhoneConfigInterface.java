package com.sanerzone.smscenter.biz.iface;
/**
 * @param type 1:新增 2:删除
 * @author zhukc
 */
public interface PhoneConfigInterface {
	//号码属性(运营商、省市代码)
	public boolean configPhoneInfo(int type,String phone,String phoneType,String cityCode);
	//系统黑名单
	public boolean configSysBlacklist(int type,String phone,String[] phones);
	//营销黑名单
	public boolean configUserBlacklist(int type,String phone,String userid,String[] phones);
	//系统白名单
	public boolean configWhitelist(int type,String phone,String[] phones);
}
