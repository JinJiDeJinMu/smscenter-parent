package com.sanerzone.smscenter.biz.utils;

import java.util.Calendar;
import java.util.Date;

import com.sanerzone.common.support.sequence.MsgId;

public class TableNameHelper {
	
	public static String SMSSEND_PRE = "sms_send_";
	public static String SMSSUBMIT_PRE = "sms_submit_";
	public static String SMSREPORT_PRE = "sms_report_";
	
	/**
	 * 根据业务ID获取表名下标(日表 sms_send)
	 * 主要用于sms_send入库
	 * @param bizid
	 * @return
	 */
	public static String getSmsSendTable(String bizid){
		try{
			if (StringHelper.isNotBlank(bizid)){
				return String.format("%s%d", SMSSEND_PRE, new MsgId(bizid).getDay());
			}
		}catch(Exception e){
		}
		return String.format("%s%s", SMSSEND_PRE, DateHelper.getDay());
	}
	
	public static String getSmsSubmitTable(String bizid){
		return String.format("%s%s", SMSSUBMIT_PRE, getTableMonth(bizid));
	}
	
	public static String getSmsReportTable(String bizid){
		return String.format("%s%s", SMSREPORT_PRE, getTableMonth(bizid));
	}
	
	/**
	 * 根据业务ID获取表名下标(日表、历史月表 用户获取sms_send表)
	 * 主要用于sms_send查询
	 * @param bizid
	 * @return
	 */
	public static String getTableIndex(String bizid){
		try{
			if (StringHelper.isNotBlank(bizid)){
				//当前时间
				Calendar current = Calendar.getInstance();
				int curMonth = current.get(Calendar.MONTH) + 1;//当前月份
				int month = new MsgId(bizid).getMonth();//业务月份
				if(curMonth == month){
					int curDay = current.get(Calendar.DAY_OF_MONTH);//当前日
					int day = new MsgId(bizid).getDay();//业务日
					if(curDay-4>=day){
						return fmtMonth(month);
					}else{
						return StringHelper.valueof(day);
					}
					
				}else{
					return fmtMonth(month);
				}
			}
		}catch(Exception e){
			return DateHelper.getDay();
		}
		
		return DateHelper.getDay();		
	}
	
	/**
	 * 根据业务ID获取表名下标(月表 用于获取sms_report、sms_submit)
	 * @param bizid
	 * @return
	 */
	public static String getTableMonth(String bizid){
		if (StringHelper.isNotBlank(bizid)){
			int month = new MsgId(bizid).getMonth();
			return fmtMonth(month);
		}else{
			return "";
		}
	}
	
	//格式化月份
	private static String fmtMonth(int month){
		if(month < 10){
			return "history_0"+month;
		}
		return "history_"+month;
	}
	
	/**
	 * 获取表名下标(月表)
	 * @return
	 */
	public static String getTableMonth(){
		return getTableMonth(new Date());
	}
	
	/**
	 * 根据时间获取表名下标(月表)
	 * @param date
	 * @return
	 */
	public static String getTableMonth(Date date){
		return "history_"+DateHelper.formatDate(date, "MM");
	}
	
	//获取表下标
    public static int getDayOfMonth(int index){
    	Calendar calendar = Calendar.getInstance();  
        calendar.add(Calendar.DATE, index);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
	
	/**
	 * 根据时间获取表名下标
	 * @param day
	 * @return
	 */
	public static String getTableIndex(Date day){
		Calendar calendar = Calendar.getInstance();
		if(day == null){
			day = calendar.getTime();
		}
		double dis = DateHelper.getDistanceOfTwoDate(day, calendar.getTime());
		if(dis >=4){
			return "history_"+DateHelper.formatDate(day, "MM");
		}
		return DateHelper.formatDate(day, "d");
	}
	
	/**
	 * 根据业务ID获取表名下标
	 * 主要用于有历史表的场景
	 * @param bizid
	 * @return
	 */
	public static String getTableIndexNew(String bizid){
		//当前时间
		Calendar current = Calendar.getInstance();
		
		int curMonth = current.get(Calendar.MONTH) + 1;
		int curDay = current.get(Calendar.DAY_OF_MONTH);
		
		if (StringHelper.isNotBlank(bizid)){
			try{
				String monthStr = "";
				int day = new MsgId(bizid).getDay();
				int month = new MsgId(bizid).getMonth();
				if (month < 10){
					monthStr = "0" + month;
				}else{
					monthStr = "" + month;
				}
				
				//业务时间
				Calendar bizDate = Calendar.getInstance();
				
				if (month > curMonth){
					bizDate.set(Calendar.YEAR, current.get(Calendar.YEAR) - 1);
				}else if (month == curMonth){
					if (day > curDay){
						bizDate.set(Calendar.YEAR, current.get(Calendar.YEAR) - 1);
					}else{
						bizDate.set(Calendar.YEAR, current.get(Calendar.YEAR));
					}
				}else{
					bizDate.set(Calendar.YEAR, current.get(Calendar.YEAR));
				}
				
				bizDate.set(Calendar.MONTH, month - 1);
				bizDate.set(Calendar.DAY_OF_MONTH, day);
				bizDate.set(Calendar.HOUR_OF_DAY, 0);
				bizDate.set(Calendar.MINUTE, 0);
				bizDate.set(Calendar.SECOND, 0);
				bizDate.set(Calendar.MILLISECOND, 0);
				
				Calendar yesterday = Calendar.getInstance();    //3天前  
		        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));  
		        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));  
		        yesterday.set(Calendar.DAY_OF_MONTH, yesterday.get(Calendar.DAY_OF_MONTH)-3);  
		        yesterday.set( Calendar.HOUR_OF_DAY, 0);  
		        yesterday.set( Calendar.MINUTE, 0);  
		        yesterday.set(Calendar.SECOND, 0);  
		        yesterday.set(Calendar.MILLISECOND, 0);
				
		        if (bizDate.after(yesterday) || DateHelper.isSameDay(bizDate, yesterday)){
		        	return String.valueOf(new MsgId(bizid).getDay());
		        }else{
		        	return "history_" + bizDate.get(Calendar.YEAR) + monthStr;
		        }
			}catch(Exception e){
				return String.valueOf(current.get(Calendar.DAY_OF_MONTH));
			}
		}else{
			return String.valueOf(current.get(Calendar.DAY_OF_MONTH));
		}
	}
	
	public static String getTableIndex(int day){
		return String.valueOf(DateHelper.getDayOfMonth(day));
	}
	
	
	
	
	public static void main(String[] args)
	{
		String bizId = "0401174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0510174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0509174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0508174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0507174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0506174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0505174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0501174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0101174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0601174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "asdfghjkloiuytre";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0918174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0919174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0915174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
		bizId = "0914174628000212143328";
		System.out.println(bizId +" --------------------> "+ getTableIndex(bizId));
	}
}
