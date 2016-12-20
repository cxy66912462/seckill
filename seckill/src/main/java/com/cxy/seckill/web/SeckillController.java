package com.cxy.seckill.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxy.seckill.common.SeckillStatEnum;
import com.cxy.seckill.dto.Exposer;
import com.cxy.seckill.dto.SeckillExecution;
import com.cxy.seckill.dto.SeckillResult;
import com.cxy.seckill.entity.Seckill;
import com.cxy.seckill.exception.RepeatKillException;
import com.cxy.seckill.exception.SeckillCloseException;
import com.cxy.seckill.service.SeckillService;

/**
 * description:秒杀controller
 * 2016年12月14日 下午1:29:55
 * @author cxy
 */
@Controller
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分  
public class SeckillController {
	
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Logger logger = LogManager.getLogger(SeckillController.class);

	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){
		//获取列表页
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("seckillList", seckillList);
		return "/seckill/list";
	}
	
//	@RequestMapping(value="{seckillId}/detail",method = RequestMethod.GET)
//	public String detail(@PathVariable("seckillId") Long seckillId, Model model){
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
            return "redirect:/seckill/list";
        }
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill == null){
			return "redirect:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "/seckill/detail";
	}
	
	
	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST)
	@ResponseBody
	public SeckillResult<SeckillExecution> executeSeckill(@PathVariable("seckillId") Long seckillId,
					@PathVariable("md5") String md5,
					@CookieValue(value = "killPhone", required = false) String phone){
		
		if(StringUtils.isBlank(phone)){
			return new SeckillResult<>(false, "未注册手机号");
		}
		SeckillResult<SeckillExecution> result = null;
		try {
			SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
			result = new SeckillResult<SeckillExecution>(execution, true);
			logger.info("seckill success");
		}catch (RepeatKillException e) {
			logger.error(e.getMessage(),e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(execution, false);
		}catch (SeckillCloseException e) {
			logger.error(e.getMessage(),e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			result = new SeckillResult<SeckillExecution>(execution, false);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(execution, false);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST)
	@ResponseBody
	public SeckillResult<Exposer> exposerSeckillAddress(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Exposer> result = null;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(exposer, true);
			logger.info("秒杀开启:暴露秒杀地址");
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
		
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<String> time() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		@SuppressWarnings("rawtypes")
		SeckillResult result = new SeckillResult(sdf.format(now),true);
		return result;
	}
}
