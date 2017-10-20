package com.sanerzone.smscenter.biz.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.sanerzone.smscenter.biz.entity.SmsReportSync;
import com.sanerzone.smscenter.biz.mapper.SmsReportSyncMapper;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;

/**
 * 网关提交同步
 * @author zhangjie
 */
public class SmsReportSyncTask {
	Logger logger = LoggerFactory.getLogger(SmsReportSyncTask.class);
	
	private SmsReportSyncMapper smsReportSyncMapper;
	
	SqlSessionFactory sqlSessionFactory;

	/**
	 * 延时一分钟执行一次
	 */
	public void execSync()
	{
		logger.info("网状提交状态，同步任务执行中...");
		List<SmsReportSync> list = smsReportSyncMapper.findList(new SmsReportSync());
		
		if (null != list)
		{
			SqlSession sqlSession = sqlSessionFactory.openSession();
			
			Map<String,Object> map = null;
			
			for (SmsReportSync para : list)
			{
				map = Maps.newHashMap();
				
				map.put("id", para.getBizid());
				if("DELIVRD".equals(para.getStat())){//成功
					map.put("gatewayStatus", "T100");
				}else{
					map.put("gatewayStatus", "F2" + para.getStat());//失败
				}
				map.put("submitTime", para.getSubmitTime());
                map.put("reportTime", para.getDoneTime());
				map.put("tableName", "jmsg_sms_send_" + TableNameHelper.getTableIndex(para.getBizid()));
				int num = sqlSession.update("com.sanerzone.jmsg.dao.JmsgSmsSendDao.batchUpdateReprotStatus", map);
				
				if (num == 1)
				{
					smsReportSyncMapper.delete(para);
				}
			}
			
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	public SmsReportSyncMapper getSmsReportSyncMapper() {
		return smsReportSyncMapper;
	}

	public void setSmsReportSyncMapper(SmsReportSyncMapper smsReportSyncMapper) {
		this.smsReportSyncMapper = smsReportSyncMapper;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
