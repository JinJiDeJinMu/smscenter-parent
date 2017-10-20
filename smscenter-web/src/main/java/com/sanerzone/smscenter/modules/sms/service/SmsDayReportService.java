/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

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
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;
import com.sanerzone.smscenter.modules.sms.dao.SmsDayReportDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsDayReport;

/**
 * 日报表Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsDayReportService extends CrudService<SmsDayReportDao, SmsDayReport> {
	
//	@Autowired
//	private SmsDayReportDao jmsgSmsDayReportDao;
	
	@Autowired
	private BaseAccountService jmsgAccountService;
	
	private static final int BATCH_COMMIT_MAX_COUNT = 200;//批量提交默认值

	public SmsDayReport get(String id) {
		return super.get(id);
	}
	
	public List<SmsDayReport> findList(SmsDayReport smsDayReport) {
		return super.findList(smsDayReport);
	}
	
	public Page<SmsDayReport> findPage(Page<SmsDayReport> page, SmsDayReport smsDayReport) {
		return super.findPage(page, smsDayReport);
	}
	
	public Page<SmsDayReport> findByUserPage(Page<SmsDayReport> page, SmsDayReport smsDayReport) {
		smsDayReport.setPage(page);
		page.setList(dao.findByUserList(smsDayReport));
		return page;
	}
	
	public Page<SmsDayReport> findByAccidPage(Page<SmsDayReport> page, SmsDayReport smsDayReport) {
		smsDayReport.setPage(page);
		page.setList(dao.findListByAccId(smsDayReport));
		return page;
	}
	
	public Page<SmsDayReport> findByGatewayPage(Page<SmsDayReport> page, SmsDayReport smsDayReport) {
		smsDayReport.setPage(page);
		page.setList(dao.findByGatewayList(smsDayReport));
		return page;
	}
	
	public Page<SmsDayReport> findByUserGatewayPage(Page<SmsDayReport> page, SmsDayReport smsDayReport) {
		smsDayReport.setPage(page);
		page.setList(dao.findByUserGatewayList(smsDayReport));
		return page;
	}
	
	public Page<SmsDayReport> findByUserPhoneTypePage(Page<SmsDayReport> page, SmsDayReport smsDayReport) {
		smsDayReport.setPage(page);
		page.setList(dao.findByUserPhoneTypeList(smsDayReport));
		return page;
	}	
	
	@Transactional(readOnly = false)
	public void save(SmsDayReport smsDayReport) {
		super.save(smsDayReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsDayReport smsDayReport) {
		super.delete(smsDayReport);
	}
	
	/**
	 * 
	 * @param flag true:计算返充量
	 * @param index
	 */
	@Transactional(readOnly = false)
	public void saveSmsDayReport(boolean flag,String tableName,Date date){
		SqlSession sqlSession = SpringContextHolder.getBean(SqlSessionFactory.class).openSession(ExecutorType.BATCH, false);
		try{
			List<SmsDayReport> result = getUserDaySendList(flag, tableName, date);
			if(result != null && result.size() > 0){
				int i=0;
				for (SmsDayReport resultEntity : result) {
					i++;
					sqlSession.insert("com.sanerzone.smscenter.modules.sms.dao.SmsDayReportDao.batchInsert", resultEntity);//批量提交
					if(i % BATCH_COMMIT_MAX_COUNT == 0){
						sqlSession.commit();
					}
				}
				sqlSession.commit();
			}
		}catch(Exception e){
			logger.error("日报表统计失败:{}", e);
		}finally{
			if(sqlSession != null)sqlSession.close();
		}
	}
	
	//用户日发送信息
	public List<SmsDayReport> getUserDaySendList(boolean flag,String tableName,Date day){
		List<SmsDayReport> result = Lists.newArrayList();
		SmsDayReport param = new SmsDayReport();
		param.setTableName(tableName);
		param.setDay(day);
		List<BaseAccount> accountList = jmsgAccountService.findList(new BaseAccount());
		if(accountList != null && accountList.size() >0){
			for (BaseAccount baseAccount : accountList) {
				 param.setUserid(baseAccount.getUserid());
				 param.setAccId(baseAccount.getId());
				 result.addAll(getDaySendList(flag, param));
			}
		}
		return result;
	}
	
	//发送信息
	public List<SmsDayReport> getDaySendList(boolean flag,SmsDayReport param){
		List<SmsDayReport> result = Lists.newArrayList();
		List<SmsDayReport> smsSendList = findSendList(param);
		if(smsSendList != null && smsSendList.size() >0){
			for (SmsDayReport entity : smsSendList) {
				int failCount=0;
				int successCount = entity.getSuccessCount();//发送成功量
				int sendFailCount = entity.getSendFailCount();//发送失败量
				int reportFailCount = entity.getReportFailCount();//状态报告失败量
				int submitFailCount = entity.getSubmitFailCount();//提交失败量
				int userCount = entity.getUserCount();//用户计费
				int userBackCount = 0;
				int backCount = 0;
				int submitSuccessCount = 0;
				submitSuccessCount = successCount - submitFailCount;//提交成功量 = 提交总量-提交失败量
				if(flag){
					userBackCount=entity.getSendCount()-userCount;
				}
				failCount = sendFailCount+reportFailCount;//失败量 = 发送失败量+状态失败量
				entity.setUserBackCount(userBackCount);
				entity.setBackCount(backCount);
				entity.setFailCount(failCount);
				entity.setSubmitSuccessCount(submitSuccessCount);
				entity.setSuccessCount(successCount);//发送成功量
				entity.setBackFlag("0");
				result.add(entity);
			}
		}
		return result;
	}
	
	@Transactional(readOnly = false)
	public void updateBackFlag(SmsDayReport param){
		dao.updateBackFlag(param);
	}
	
	//获取返充列表
	public List<SmsDayReport> findRechargeList(Date day){
		return dao.findRechargeList(day);
	}
	
	//获取用户日发送量列表
	public List<SmsDayReport> findDaySendCountList(SmsDayReport param){
		return dao.findDaySendCountList(param);
	}
	
	//从sms_send_XX表中获取用户日发送量
	public List<SmsDayReport> findSendList(SmsDayReport param){
		return dao.findSendList(param);
	}
	
	//获取当用户当天，一周，月的发送记录
	public List<SmsDayReport> findList4Index(SmsDayReport param)
	{
		return dao.findList4Index(param);
	}
	
	
}