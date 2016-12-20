package com.cxy.seckill.dto;

/**
 * 暴露秒杀地址DTO
 */
public class Exposer {

    //是否开启秒杀
    private Boolean exposed;

    //一种加密措施
    private String md5;

    //id
    private Long seckillId;

    //系统当前时间(毫秒)
    private Long now;

    //开启时间
    private Long start;

    //结束时间
    private Long end;
    
    public Exposer() {
		super();
	}

	public Exposer(boolean exposed, Long seckillId) {
		this.exposed = exposed;
		this.seckillId = seckillId;
	}
    
    public Exposer(boolean exposed,String md5, Long seckillId) {
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.md5 = md5;
	}

	public Exposer(boolean exposed, Long seckillId, Long now, Long start, Long end) {
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Boolean getExposed() {
		return exposed;
	}

	public void setExposed(Boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	public Long getNow() {
		return now;
	}

	public void setNow(Long now) {
		this.now = now;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", now=" + now + ", start="
				+ start + ", end=" + end + "]";
	}

}
