package com.cxy.seckill.dto;

import com.cxy.seckill.common.SeckillStatEnum;
import com.cxy.seckill.entity.SuccessKilled;

/**
 * 封装秒杀执行后结果
 */
public class SeckillExecution {

    private Long seckillId;

    //秒杀执行结果状态
    private int status;

    //状态表示
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;
    
	public SeckillExecution() {
		super();
	}
	

	public SeckillExecution(Long seckillId, SeckillStatEnum seckillStatEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.status = seckillStatEnum.getStatus();
		this.stateInfo = seckillStatEnum.getStateInfo();
		this.successKilled = successKilled;
	}

	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.status = statEnum.getStatus();
        this.stateInfo = statEnum.getStateInfo();
    }

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return status;
	}

	public void setState(int state) {
		this.status = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", status=" + status + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}

}
