package com.wishit.order.dto;

import com.wishit.order.enums.ErrorCode;

public class ResponseWrapper<T> {
	private T data;
	private String errCode;
	private String description;
	
	public ResponseWrapper(T data) {
		this.data = data;
	}
	
	public ResponseWrapper(String errCode, String description) {
		this.errCode = errCode;
		this.description = description;
	}
	
	 public ResponseWrapper(ErrorCode errorCode) {
	        this.errCode = errorCode.getCode();
	        this.description = errorCode.getMessage();
	    }
	
	public T getData() {
		return data;
	}
	public String getErrCode() {
		return errCode;
	}
	public String getDescription() {
		return description;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public void setDescription(String description) {
		this.description= description;
	}
	
}
