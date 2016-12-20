<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入jstl -->
<%@include file="../common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>秒杀列表页</title>
<%@include file="../common/head.jsp"%>
</head>
<script src="${pageContext.request.contextPath }/js/seckill.js" type="text/javascript"></script>
<body>
	 <!-- 页面显示部分 -->
	 <div class="container">
	 	<div class="panel panel-default">
	 		<div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
            	<table class="table table-hover">
            		<thead>
            			<tr>
            				<th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情页</th>
            			</tr>
            		</thead>
					<c:forEach var="seckill" items="${seckillList }">
						<tr>
							<td>${seckill.name }</td>
							<td>${seckill.number }</td>
							<td>${seckill.startTime }</td>
							<td>${seckill.endTime }</td>
							<td>${seckill.createTime }</td>
							<td> <a class="btn btn-info" href="${pageContext.request.contextPath }/seckill/${seckill.seckillId}/detail" target="_blank">link</a></td>
						</tr>
					</c:forEach>
            	</table>
            </div>
	 	</div>
	 </div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</html>