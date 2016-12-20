package com.cxy.seckill.common;

public enum SeckillStatEnum {
	
	SUCCESS(1000,"秒杀成功"),
    END(1998,"秒杀结束"),
    REPEAT_KILL(2998,"重复秒杀"),
    INNER_ERROR(3998,"系统异常"),
    DATA_REWRITE(4998,"数据篡改");
	
	private int status;

    private String stateInfo;
    
    SeckillStatEnum(int status, String stateInfo) {
        this.status = status;
        this.stateInfo = stateInfo;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public static SeckillStatEnum stateOf(int index) {
        for (SeckillStatEnum state : values()) {
            if (state.getStatus() == index) {
                return state;
            }
        }
        return null;
    }
}
