package com.sanerzone.smscenter.common.message;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result<T> {
	
	private String code;
	private String msg;
	private T data;
	
	public Result (){
        this.code = "0";
        this.msg = "成功";
    }
	
	public Result (String code, String msg){
	    this.code = code;
	    this.msg = msg;
	}
	
    public String getCode(){
        return code;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public String getMsg(){
        return msg;
    }
    
    public void setMsg(String msg){
        this.msg = msg;
    }
    
    public T getData(){
        return data;
    }
    
    public void setData(T data){
        this.data = data;
    }
	
    @JsonIgnore
    public boolean isSuccess(){
        return StringUtils.startsWith("0", code);
    }
	
	
}
