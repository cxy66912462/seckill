var seckill = {
		URL:{
			now : function(){
				return path+'/seckill/time/now';
			},
			exposer : function(seckillId){
				return path+'/seckill/'+seckillId+'/exposer';
			},
			execution : function(seckillId,md5){
				return path+'/seckill/'+seckillId+'/'+md5+'/execution';
			}
		},
	    //详情页秒杀逻辑
	    detail: {
	        //详情页初始化
	        init : function(params){
				//使用EL表达式传入参数
				var killPhone = $.cookie('killPhone');
//		         alert(killPhone);
		        //校验手机号
		        if(!seckill.checkPhone(killPhone)){
		        	//验证失败
		        	//绑定phone,控制输出
		        	var killPhoneModal = $("#killPhoneModal");
		        	killPhoneModal.modal({
		        		show:true,
		        		backdrop:'static',//禁止位置关闭
		        		keyboard:false //关闭键盘事件
		        	});
		        }
		        $("#killPhoneBtn").click(function(){
		        	var inputPhone = $("#killPhone").val();
		        	console.log('inputPhone='+inputPhone);//TODO
		        	if(seckill.checkPhone(inputPhone)){
		        		//手机号写入cookie
		        		$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
		        		//刷新页面
		        		window.location.reload();
		        	}else{
		        		$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
		        	}
		        });
		        //已经登录
		        //计时交互
		        var startTime = params['startTime'];
		        var endTime = params['endTime'];
		        var seckillId = params['seckillId'];
		        $.get(seckill.URL.now(),{},function(data){
		        	if(data && data['result']){
		        		//响应成功
		        		var nowTime = data['data'];
		        		//时间判断,计时交互
		        		seckill.countdown(seckillId,nowTime,startTime,endTime);
		        	}else{
		        		console.log('result:'+data);
		        	}
		        });
		        
			}
		},//校验手机号
		checkPhone:function(killPhone){ 
	        if(!(/^1(3|4|5|7|8)\d{9}$/.test(killPhone))){ 
	            return false; 
	        } else{
	        	return true;
	        }
	    },//秒杀计时逻辑
	    countdown:function(seckillId,nowTime,startTime,endTime){
//	    	var now = dateToString(nowTime);
	    	var seckillBox = $("#seckillBox");
	    	if(nowTime>endTime){
	    		//秒杀结束
	    		seckillBox.html("秒杀结束");
	    	}else if(nowTime<startTime){
				//秒杀未开始,计时事件绑定
				var killTime = new Date(startTime);
				seckillBox.countdown(killTime,function(event){
					//时间格式
					var format = event.strftime(' 秒杀倒计时: %D天 %H时 %M分 %S秒');
	                seckillBox.html(format);
				}).on("finish.countdown",function(){
					//时间完成后的回调事件,开启秒杀
					seckillBox.handleSeckill(seckillId,seckillBox);
				});
	    	}else{
	    		//开启秒杀
	    		seckillBox.html("秒杀中...");
	    		seckill.handleSeckillkill(seckillId,seckillBox);
	    	}
	    },//秒杀逻辑
	    handleSeckillkill:function(seckillId,seckillBox){
		    	// 获取秒杀地址,控制显示逻辑,执行秒杀
	    	seckillBox.hide()
				.append('<div style="margin:20px 5px 5px 5px"><button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button></div>')
				.show();
		    $.post(seckill.URL.exposer(seckillId),{},function(data){
		    	if(data && data['result']){
		    		var exposer = data['data'];
		    		if(exposer['exposed']){
		    			//开启秒杀,获取秒杀地址
		    			var md5 = exposer['md5'];
		    			var killUrl = seckill.URL.execution(seckillId,md5);
		    			console.log("killUrl:"+killUrl);
		    			//绑定一次点击事件
		    			//TODO 绑定事件先不做限制,让用户无限点击
		    			$("#killBtn").click(function(){
		    				//执行秒杀请求
	                        //1:先禁用按钮
		    				$(this).addClass("disabled");
		    				//2:发送秒杀请求执行秒杀
		    				alert(killUrl);
		    				$.post(killUrl,{},function(data){
		    					if(data && data['result']){
		    						//请求成功
		    						var killResult = data['data'];//秒杀结果
		    						var status = killResult['status'];//
		    						var stateInfo = killResult['stateInfo'];
		    						//3:显示秒杀结果
		    						seckillBox.html('<span class="label label-success">' + stateInfo + '</span>');
		    					}else{
		    						//请求成功
		    						var killResult = data['data'];//秒杀结果
		    						var status = killResult['status'];//
		    						var stateInfo = killResult['stateInfo'];
		    						//3:显示秒杀结果
		    						seckillBox.html('<span class="label label-danger">' + stateInfo + '</span>');
		    					}
		    				});
		    			});
		    		}else{
		    			//未开启秒杀
	                    var now = exposer['now'];
	                    var start = exposer['start'];
	                    var end = exposer['end'];
	                    //重新计算计时逻辑
	                    seckill.countdown(seckillId, now, start, end);
		    		}
		    	}else{
	                console.log('result:'+result);
	            }
		    	
		    });	
	    }
}

//日期转字符串 YYYY-MM-DD
function dateToString(now){  
    var year = now.getFullYear(); 
    var month =(now.getMonth() + 1).toString();  
    var day = (now.getDate()).toString();  
    var hour = (now.getHours()).toString();  
    var minute = (now.getMinutes()).toString();  
    var second = (now.getSeconds()).toString();  
    if (month.length == 1) {  
        month = "0" + month;  
    }  
    if (day.length == 1) {  
        day = "0" + day;  
    }  
    if (hour.length == 1) {  
        hour = "0" + hour;  
    }  
    if (minute.length == 1) {  
        minute = "0" + minute;  
    }  
    if (second.length == 1) {  
        second = "0" + second;  
    }  
     var dateTime = year + "-" + month + "-" + day +" "+ hour +":"+minute+":"+second;  
     return dateTime;  
  }  