package com.sanerzone.smscenter.biz.entity;

public class DataEntity<T> {

	private String tableName;
	private T data;
	
	public DataEntity(String tableName, T data) {
		this.tableName = tableName;
		this.data = data;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
