package com.sanerzone.smscenter.modules.sms.service.excel;

import java.util.List;

import com.sanerzone.jeebase.common.utils.excel.IRowReader;
import com.sanerzone.smscenter.modules.sms.entity.SmsDot;

public class SmsDotReader implements IRowReader{  
	
	private List<SmsDot> list;
	private String code = "1";
	private SmsDot smsDot;
	
	public SmsDotReader(List<SmsDot> list){
		this.list = list;
	}
	
    /* 业务逻辑实现方法 
     */  
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
    	if("1".equals(code)){
	    	if(rowlist.size() ==2){
	    		smsDot = new SmsDot();
	    		smsDot.setPhone(rowlist.get(0));
	    		smsDot.setSmsContent(rowlist.get(1));
	        	list.add(smsDot);
	    	}else{
	    		code = "-1";
	    	}
    	}
    }

	public String getResult() {
		return code;
	}
}  
