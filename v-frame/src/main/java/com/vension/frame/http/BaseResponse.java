package com.vension.frame.http;

import java.io.Serializable;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 15:31
 * 描  述：服务器通用返回数据格式
 * ========================================================
 */
public class BaseResponse<T> implements Serializable {

	/**
	 * success : true
	 * error :
	 * data : {"access_token":"be2b8c336231b8e92d11165be6f793ced6e882eb","expires_in":2628000,"token_type":"Bearer"}
	 * code : 0
	 */


	private boolean success;
	private String message;
	private int code;
	private T data;

	private boolean hasNext;
	private String retcode;
	private String dataType;
	private String appCode;
	private String pageToken;

	public BaseResponse(boolean success, String message, int code, T data) {
		this.success = success;
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getPageToken() {
		return pageToken;
	}

	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}
}
