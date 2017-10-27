<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
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
<meta charset="UTF-8">
<title>后台管理</title>
<script type="text/javascript"
	src="/background/detail/style/js/jquery.js"></script>
<script type="text/javascript"
	src="/background/detail/style/js/page_common.js"></script>
<link href="/background/detail/style/css/common_style_blue.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="/background/detail/style/css/index_1.css" />
</head>
<body>
	<!-- 页面标题 -->
	<div id="TitleArea">
		<div id="TitleArea_Head"></div>
		<div id="TitleArea_Title">
			<div id="TitleArea_Title_Content">
				<img border="0" width="13" height="13"
					src="/background/detail/style/images/title_arrow.gif" /> 每日特惠列表
			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>

	<!-- 过滤条件 -->
	<!-- <div id="QueryArea">
		<form action="/background/detail//wirelessplatform/food.html"
			method="get">
			<input type="hidden" name="method" value="search"> <input
				type="text" name="keyword" title="请输入菜品名称"> <input
				type="submit" value="搜索">
		</form>
	</div> -->
	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<table class="MainArea_Content" align="center" cellspacing="0"
			cellpadding="0">
			<!-- 表头-->
			<thead>
				<tr align="center" valign="middle" id="TableTitle">
					<td>编号</td>
					<td>标题</td>
					<td>原价</td>
					<td>现价</td>
					<td>操作</td>
				</tr>
			</thead>
			<!--显示数据列表 -->
			<tbody id="TableData">
                <c:forEach items="${requestScope.weeklyPreferencesList }" var="list" varStatus="var">
                    <tr class="TableDetail1">
	                    <td>${var.count }</td>
	                    <td>${list.name }</td>
	                    <td>${list.old_price }</td>
	                    <td>${list.price }</td>
	                    <td><a href="wpback/delete/${list.wp_id }"
	                        onClick="return delConfirm();" class="FunctionButton">删除</a></td>
	                </tr>
                </c:forEach>
				

			</tbody>
		</table>

		<!-- 其他功能超链接 -->
		<div id="TableTail" align="center">
			<div class="FunctionButton">
				<a href="wpback/add">添加</a>
			</div>
		</div>
	</div>
</body>
</html>