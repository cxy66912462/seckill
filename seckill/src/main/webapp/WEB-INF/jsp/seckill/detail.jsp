<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="../common/head.jsp" %>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading text-center">
                <h1>${seckill.name }</h1>
            </div>
			<div class="panel-body">
				<h2 class="text-danger">
					<!-- 显示time图标 -->
					<span class="glyphicon glyphicon-time"></span>
					<!-- 显示倒计时 -->
					<span class="glyphicon" id="seckillBox"></span>
				</h2>
			</div>
		</div>
	</div>
	<!-- 登录弹出层，输入电话 -->
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀手机号
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhone" 
								placeholder="填写手机号" class="form-control" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
	                <!-- 验证信息 -->
	                <span id="killPhoneMessage" class="glyphicon"></span>
	                <button type="button" id="killPhoneBtn" class="btn btn-success">
	                    <span class="glyphicon glyphicon-phone"></span>
	                    Submit
	                </button>
	            </div>
	        </div>
		</div>
	</div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap.js"></script> --%>
<!-- 使用CDN 获取公共js http://www.bootcdn.cn/ -->
<!-- jQuery cookie操作插件 -->
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQuery countDown倒计时插件 -->
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<!-- 开始编写交互逻辑 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/seckill.js"></script>
<script type="text/javascript">
	var path = '${pageContext.request.contextPath }';
    $(function(){
		seckill.detail.init({
			startTime:'${seckill.startTime}',
			endTime:'${seckill.endTime}',
			seckillId:'${seckill.seckillId}'
		});
    });
    
    
    
</script>
</html>