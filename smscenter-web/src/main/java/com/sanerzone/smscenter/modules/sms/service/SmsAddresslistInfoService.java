/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsAddresslistInfoDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsAddresslistGroup;
import com.sanerzone.smscenter.modules.sms.entity.SmsAddresslistInfo;

/**
 * 联系人列表Service
 * @author zhukc
 * @version 2017-04-01
 */
@Service
@Transactional(readOnly = true)
public class SmsAddresslistInfoService extends CrudService<SmsAddresslistInfoDao, SmsAddresslistInfo> {
	
	private static final int BATCH_COMMIT_MAX_COUNT = 500;//批量提交默认值

	public SmsAddresslistInfo get(String id) {
		return super.get(id);
	}
	
	public List<SmsAddresslistInfo> findList(SmsAddresslistInfo smsAddresslistInfo) {
		return super.findList(smsAddresslistInfo);
	}
	
	public Page<SmsAddresslistInfo> findPage(Page<SmsAddresslistInfo> page, SmsAddresslistInfo smsAddresslistInfo) {
		return super.findPage(page, smsAddresslistInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsAddresslistInfo smsAddresslistInfo) {
		super.save(smsAddresslistInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsAddresslistInfo smsAddresslistInfo) {
		super.delete(smsAddresslistInfo);
	}
	
	@Transactional(readOnly = false)
	public void deleteByGroup(String groupId){
		dao.deleteByGroupId(groupId);
	}
	
	@Transactional(readOnly = false)
	public void batchSave(List<SmsAddresslistInfo> list,String groupId){
		SqlSession sqlSession = SpringContextHolder.getBean(SqlSessionFactory.class).openSession(ExecutorType.BATCH, false);
		
		try{
			if(list != null && list.size() >0){
				int index = 0;
				User user = UserUtils.getUser();
				SmsAddresslistGroup group = new SmsAddresslistGroup(groupId);
				for (SmsAddresslistInfo smsAddresslistInfo : list) {
					if(StringUtils.isBlank(smsAddresslistInfo.getPhone())){
						continue;
					}
					index++;
					smsAddresslistInfo.setGroup(group);
					smsAddresslistInfo.setCreateBy(user);
					sqlSession.insert("com.sanerzone.smscenter.modules.sms.dao.SmsAddresslistInfoDao.insert", smsAddresslistInfo);//批量提交
					if(index % BATCH_COMMIT_MAX_COUNT == 0){ 
						sqlSession.commit(); 
					}
				}
				sqlSession.commit();
			}
		}finally{
			if(sqlSession != null)sqlSession.close();
		}
	}
	
}