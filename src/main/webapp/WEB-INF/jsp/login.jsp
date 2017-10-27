<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- 
	<base/>标签解决路径问题
	参考文章:http://www.cnblogs.com/muqianying/archive/2012/03/16/2400280.html
--%>
<!DOCTYPE HTML>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>登陆</title>
		<link rel="stylesheet" type="text/css" href="resource/css/style.css" />
	    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	    <script src="http://www.modernizr.com/downloads/modernizr-latest.js"></script>
	</head>
	<body>
	    <form id="slick-login" action="login" method="post">
	        <label for="username">用户名</label><input type="text" name="account" class="placeholder" placeholder="请输入用户名">
	        <label for="password">密码</label><input type="password" name="password" class="placeholder" placeholder="请输入密码">
	        <input type="submit" value="Log In">
	    </form>
    </body>
</html>