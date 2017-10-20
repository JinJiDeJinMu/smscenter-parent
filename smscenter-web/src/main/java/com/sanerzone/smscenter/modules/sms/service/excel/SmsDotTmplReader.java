package com.sanerzone.smscenter.modules.sms.service.excel;

import java.util.List;

import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.common.utils.excel.IRowReader;
import com.sanerzone.smscenter.modules.sms.entity.SmsDot;

public class SmsDotTmplReader implements IRowReader{  
	
	private List<SmsDot> list;
	private int phoneRow;//手机所在列
	private String smsContent;//发送内容
	private int maxRow;//最大列
	private String code = "1";
	
	public SmsDotTmplReader(List<SmsDot> list,String phoneRow,String smsContent){
		this.list = list;
		this.phoneRow = StringUtils.isBlank(phoneRow)?0:Integer.valueOf(phoneRow);
		this.smsContent = smsContent;
	}
	
    /* 业务逻辑实现方法 
     */  
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
    	if("1".equals(code)){
    		if(curRow  == 0 ){
    			maxRow = rowlist.size();
    		}
    		if(maxRow != rowlist.size()){
    			code = "-1";//导入excel信息失败，列长度不一致
    		}else{
    			if(rowlist.size() > phoneRow){
    				SmsDot smsDot = new SmsDot();
    				smsDot.setPhone(rowlist.get(phoneRow));
    	        	String realContent = smsContent;
    	        	for (int i = 0; i < rowlist.size(); i++) {
    	        		realContent = smsContent(realContent, i, rowlist.get(i));
					}
    	        	smsDot.setSmsContent(realContent);
    	        	list.add(smsDot);
    			}else{
    				code = "-2";//导入excel信息失败，获取手机号码失败
    			}
    		}
    	}
    }
    
    private String smsContent(String content,int index, String replacement){
    	String key ="\\[\\("+Integer.toString(index+10, 36).toUpperCase()+"\\)\\]";
    	content = content.replaceAll(key, replacement);
    	return content;
    }
    

	public String getResult() {
		return code;
	}
}  
