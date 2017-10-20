/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.FileUtils;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsSend;
import com.sanerzone.smscenter.modules.sms.entity.SmsTask;
import com.sanerzone.smscenter.common.utils.EmailUtil;
import com.sanerzone.smscenter.modules.sms.dao.SmsSendDao;
import com.sanerzone.smscenter.modules.sms.dao.SmsTaskDao;

/**
 * 短信任务Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsTaskService extends CrudService<SmsTaskDao, SmsTask> {
	
	@Autowired
	private SmsSendDao smsSendDao;

	public SmsTask get(String id) {
		return super.get(id);
	}
	
	public List<SmsTask> findList(SmsTask smsTask) {
		return super.findList(smsTask);
	}
	
	public Page<SmsTask> findPage(Page<SmsTask> page, SmsTask smsTask) {
		return super.findPage(page, smsTask);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsTask smsTask) {
		super.save(smsTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsTask smsTask) {
		super.delete(smsTask);
	}
	
	//审核短信内容
	@Transactional(readOnly = false)
	public void recheckSmsContent(SmsTask smsTask){
		dao.recheckSmsContent(smsTask);
	}
	
	/**
	 * 发送任务报表汇总
	 * 
	 * @param sendTimeQ 发送时间起
	 * @param sendTimeZ 发送时间止
	 */
	@Transactional(readOnly = false)
	public void smsTaskReport(String tableName,Date day){
		SmsTask param = new SmsTask();
		Date sendDatetimeQ = DateUtils.getDay(day,0,0,0);
		Date sendDatetimeZ = DateUtils.getDay(day,23,59,59);
		param.setSendDatetimeQ(sendDatetimeQ);
		param.setSendDatetimeZ(sendDatetimeZ);
		
		List<SmsTask> list = dao.findSmsTaskReport(param);
		
		if(list != null && list.size() > 0){
			SmsSend jmsgSmsSend = new SmsSend();
			jmsgSmsSend.setTableName(tableName);
			for (SmsTask smsTask : list) {
				if(smsTask.getSendCount() - smsTask.getSuccessCount() == smsTask.getFailCount())continue;
				jmsgSmsSend.setTaskid(smsTask.getTaskid());
				SmsTask report = smsSendDao.findSmsSendByTaskId(jmsgSmsSend);
				if(report != null){
					smsTask.setSuccessCount(report.getSuccessCount());
					smsTask.setFailCount(report.getFailCount());
					dao.updateReport(smsTask);
				}
			}
		}
	}
	
	//修改发送行次
	@Transactional(readOnly = false)
	public void updateRowNumber(Map<String,Object> map){
		dao.updateRowNumber(map);
	}
	
	//修改任务发送状态
	public int updateSendStatus(Map<String,Object> map){
		return dao.updateSendStatus(map);
	}
	
	//修改任务状态(版本号不增加)
	@Transactional(readOnly = false)
	public void updateStatus(SmsTask smsTask){
		Map<String,String> map = Maps.newHashMap();
		map.put("taskid", smsTask.getTaskid());
		map.put("sendStatus", smsTask.getSendStatus());
		map.put("userBy", UserUtils.getUser().getId());
		dao.updateStatus(map);
	}
	
	@Transactional(readOnly = false)
    public String createSmsTask(SmsTask smsTask, List<String> phoneList,int errorCount) {
		try{
	        
	        StringBuilder tmpSb = new StringBuilder();
	        Iterator<String> it = phoneList.iterator();  
	        while (it.hasNext()) {  
	            tmpSb.append(it.next()).append("\r\n");
	        }  
	        
	        String fileDir = Global.getConfig("smsTask.phoneList.dir");
	        String fileName = fileDir + File.separator + smsTask.getTaskid() + ".txt";
	        FileUtils.createFile(fileName);
	        FileUtils.writeToFile(fileName, tmpSb.toString(), "UTF-8", false);
	        dao.insert(smsTask);
		}catch(Exception e){
			logger.error("短信接收号码导入失败", e);
			return "短信接收号码导入失败";
		}
		
		String msg = "短信接收号码导入："+smsTask.getSendCount()+"条,无效号码"+errorCount+"条";
		if("-1".equals(smsTask.getSendStatus())){
			EmailUtil.CheckSmssendEmail("您有短信内容需要审核,提交信息:"+msg+", 用户名称:" + smsTask.getCreateBy().getName() + ", 短信内容:" + smsTask.getSmsContent());
			msg =msg+"；短信内容需要审核，审核成功后会自动发送";
		}
        
        return msg;
    }
	
}