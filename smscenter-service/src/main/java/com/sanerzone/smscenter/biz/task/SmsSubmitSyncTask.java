package com.sanerzone.smscenter.biz.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.sanerzone.smscenter.biz.entity.SmsSubmitSync;
import com.sanerzone.smscenter.biz.mapper.SmsSubmitSyncMapper;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;

/**
 * 状态报告同步任务
 * @author zhangjie
 */
public class SmsSubmitSyncTask {
	Logger logger = LoggerFactory.getLogger(SmsSubmitSyncTask.class);
	
	private SmsSubmitSyncMapper smsSubmitSyncMapper;
	
    private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 延时一分钟执行一次
	 */
	public void execSync()
	{
		logger.info("网状状态回执，同步任务执行中...");
		List<SmsSubmitSync> list = smsSubmitSyncMapper.findList(new SmsSubmitSync());
		if (null != list)
		{
			SqlSession sqlSession = sqlSessionFactory.openSession();
		
			Map<String,Object> map = Maps.newHashMap();
		
			for (SmsSubmitSync para : list)
			{
				map = Maps.newHashMap();
                map.put("id", para.getBizid());
                map.put("gatewayStatus", para.getResult());
                map.put("submitTime", para.getCreatetime());
                map.put("reportTime", para.getCreatetime());
                map.put("tableName", "jmsg_sms_send_" + TableNameHelper.getTableIndex(para.getBizid()));
                
                int update = sqlSession.update("com.sanerzone.smscenter.biz.mapper.SmsSendMapper.batchUpdateGatewayStatus", map);
                
                if (update == 1)
                {
                	smsSubmitSyncMapper.delete(para);
                }
			}
			
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	public SmsSubmitSyncMapper getSmsSubmitSyncMapper() {
		return smsSubmitSyncMapper;
	}

	public void setSmsSubmitSyncMapper(SmsSubmitSyncMapper smsSubmitSyncMapper) {
		this.smsSubmitSyncMapper = smsSubmitSyncMapper;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
