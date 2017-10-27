<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
</head>
<frameset rows="100px,*,19px" framespacing="0" border="0"
	frameborder="0">
	<frame src="background/detail/top.jsp" scrolling="no" noresize />
	<frameset cols="178px,*">
		<frame noresize src="background/detail/left.html" scrolling="yes" />
		<frame noresize name="right" src="background/detail/right.html" scrolling="yes" />
	</frameset>
	<frame noresize name="status_bar" scrolling="no"
		src="background/detail/bottom.html" />
</frameset>
<noframes>
	<body>
		你的浏览器不支持框架布局，推荐你使用
		<a href="http://www.firefox.com.cn/download/"
			style="text-decoration: none;">火狐浏览器</a>,
		<a href="http://www.google.cn/intl/zh-CN/chrome/"
			style="text-decoration: none;">谷歌浏览器</a>
	</body>
</noframes>
</html>
