package com.cxy.seckill.dto;

/**
 */
//所有ajax请求放回类型,封装json结果
public class SeckillResult<T> {

    private boolean result;

    private T data;

    private String error;

    public SeckillResult(T data, boolean success) {
        this.result = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.result = success;
        this.error = error;
    }
    
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "SeckillResult [result=" + result + ", data=" + data + ", error=" + error + "]";
	}


}
