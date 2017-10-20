/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.smscenter.modules.sms.entity.SmsDaySign;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;
import com.sanerzone.smscenter.modules.sms.dao.SmsDaySignDao;

/**
 * 日签名报表Service
 * @author huangjie
 * @version 2017-08-13
 */
@Service
@Transactional(readOnly = true)
public class SmsDaySignService extends CrudService<SmsDaySignDao, SmsDaySign> {

	private static final int BATCH_COMMIT_MAX_COUNT = 200;//批量提交默认值
	@Autowired
	private BaseAccountService smsAccountService;
	
	public SmsDaySign get(String id) {
		return super.get(id);
	}
	
	public List<SmsDaySign> findList(SmsDaySign smsDaySign) {
		return super.findList(smsDaySign);
	}
	
	public Page<SmsDaySign> findPage(Page<SmsDaySign> page, SmsDaySign smsDaySign) {
		return super.findPage(page, smsDaySign);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsDaySign smsDaySign) {
		super.save(smsDaySign);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsDaySign smsDaySign) {
		super.delete(smsDaySign);
	}
    
	@Transactional(readOnly = false)
	public void saveSmsDaySignReport(String tableName, Date date) {
		SqlSession sqlSession = SpringContextHolder.getBean(SqlSessionFactory.class).openSession(ExecutorType.BATCH, false);
		try{
			List<SmsDaySign> result = getUserDaySendList(tableName, date);
			if(result != null && result.size() > 0){
				int i=0;
				for (SmsDaySign resultEntity : result) {
					i++;
					sqlSession.insert("com.sanerzone.smscenter.modules.sms.dao.SmsDaySignDao.batchInsert", resultEntity);//批量提交
					if(i % BATCH_COMMIT_MAX_COUNT == 0){
						sqlSession.commit();
					}
				}
				sqlSession.commit();
			}
		}catch(Exception e){
			logger.error("日签名报表统计失败:{}", e);
		}finally{
			if(sqlSession != null)sqlSession.close();
		}
		
	}

	private List<SmsDaySign> getUserDaySendList(String tableName, Date date) {
		List<SmsDaySign> result = Lists.newArrayList();
		SmsDaySign param = new SmsDaySign();
		param.setTableName(tableName);
		param.setDay(date);
		List<BaseAccount> accountList = smsAccountService.findList(new BaseAccount());
		for(BaseAccount account : accountList){
			param.setAccId(account.getId());
			param.setUserid(account.getUserid());
			result.addAll(getDaySendList(param));
			
		}
		return result;
	}

	private List<SmsDaySign>getDaySendList(SmsDaySign param)
   {
		List<SmsDaySign> result = Lists.newArrayList();
		List<SmsDaySign> sendList =findSendList(param);
		if(null != sendList && sendList.size()>0 )
		{
			for(SmsDaySign entity : sendList)
			{
				int failCount=0;
				int successCount = entity.getSuccessCount();//发送成功量
				int sendFailCount = entity.getSendFailCount();//发送失败量
				int reportFailCount = entity.getReportFailCount();//状态报告失败量
				int submitFailCount = entity.getSubmitFailCount();//提交失败量
				int submitSuccessCount = 0;
				submitSuccessCount = successCount - submitFailCount;//提交成功量 = 提交总量-提交失败量
				failCount = sendFailCount+reportFailCount;//失败量 = 发送失败量+状态失败量
				entity.setFailCount(failCount);
				entity.setSubmitSuccessCount(submitSuccessCount);
				entity.setSuccessCount(successCount);//发送成功量
				result.add(entity);
			}
		}
		
		
		     return result;
	}
    
	//从sms_send_XX表中获取用户日发送量
	private List<SmsDaySign> findSendList(SmsDaySign param) {
		return dao.findSendList(param);
	}
	
}