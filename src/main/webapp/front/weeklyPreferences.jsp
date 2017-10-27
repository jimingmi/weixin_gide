<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	//项目的发布路径，例如:  /rabc
	String path = request.getContextPath();
	/*
	全路径，形式如下: http://127.0.0.1:8001/rbac/
	request.getScheme()      ——> http 获取协议
	request.getServerName()  --> 127.0.0.1 获取服务名
	request.getServerPort()  --> 8001 获取端口号
	path                     --> /rbac 获取访问的路径 路
	*/
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- 
	<base/>标签解决路径问题
	参考文章:http://www.cnblogs.com/muqianying/archive/2012/03/16/2400280.html
--%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>限时体验</title>
<meta name="viewport"
	content="width=device-width;initial-scale=1.0;maximum-scale=1.0;user-scalable=0;" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<link href="resource/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<link href="resource/css/NewGlobal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resource/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="resource/js/zepto.js"></script>
<style type="text/css">
a {
	text-decoration: none;
}

a:link {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

a:active {
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="header">
		<!-- <a href="index.html" class="home"> <span
			class="header-icon header-icon-home"></span> <span
			class="header-name">主页</span>
		</a> -->
		<div class="title" id="titleString">每周特惠</div>
		<!-- <a href="javascript:history.go(-1);" class="back"> <span
			class="header-icon header-icon-return"></span> <span
			class="header-name">返回</span>
		</a> -->
	</div>
	<div class="container">
		<ul class="giftlist unstyled">
			<c:forEach items="${weeklypreferencesList }" var="list">
				<li><a href="weeklyPreferences/details/${list.wp_id }">
						<div class="imgbox">
							<img src="${list.images }">
						</div>
						<div class="desc">
							<span>${list.name }<br /></span> <em>${list.price } </em>
						</div>
				</a></li>
			</c:forEach>
		</ul>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.giftlist li img').each(function() {
				var width = $(this).width(); // 图片实际宽度
				var height = $(this).height(); // 图片实际高度
				// 检查图片是否超宽
				if (width != height) {
					$(this).css("height", width); // 设定等比例缩放后的高度
				}
			});
		});
	</script>
	<div class="footer">

		<div class="gezifooter">
			<hr />
			<!-- <a href="login.aspx" class="ui-link">立即登陆</a> <font color="#878787">|</font>
			<a href="reg.aspx" class="ui-link">免费注册</a> <font color="#878787">|</font>
			<a href="http://www.gridinn.com/@display=pc" class="ui-link">电脑版</a> -->
		</div>
		<div class="gezifooter">
			<p style="color: #bbb;">义乌闪蝶科技 &copy; 版权所有 2016-2017</p>
		</div>
	</div>
</body>
</html>