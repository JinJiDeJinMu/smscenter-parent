package com.sanerzone.smscenter.api.entity;

public class ApiResult<T> {
	
	private String code;
	private String msg;
	private T data;
	
	public ApiResult (){
        this.code = "0";
        this.msg = "成功";
    }
	
	public ApiResult (String code, String msg){
	    this.code = code;
	    this.msg = msg;
	}
	
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getMsg()
    {
        return msg;
    }
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    public T getData()
    {
        return data;
    }
    public void setData(T data)
    {
        this.data = data;
    }
	
}
