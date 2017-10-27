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
					src="/background/detail/style/images/title_arrow.gif" /> 添加每周特惠产品
			</div>
		</div>
		<div id="TitleArea_End"></div>
	</div>
	<!-- 主内容区域（数据列表或表单显示） -->
	<div id="MainArea">
		<!-- 表单内容 -->
		<form action="wpback/addDo" method="post" enctype="multipart/form-data">
			<!-- 本段标题（分段标题） -->
			<div class="ItemBlock_Title">
				<img width="4" height="7" border="0"
					src="/background/detail/style/images/item_point.gif">
				产品信息&nbsp;
			</div>
			<!-- 本段表单字段 -->
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<div class="ItemBlock2">
						<table cellpadding="0" cellspacing="0" class="mainForm">
							<tr>
								<td width="80px">标题</td>
								<td><input type="text" name="name" class="InputStyle"
									value="" /> *</td>
							</tr>
							<tr>
                                <td width="80px">商品封面图片</td>
                                <td><input type="file" name="images" /> *</td>
                            </tr>
							<tr>
								<td>原价</td>
								<td><input type="text" name="old_price" class="InputStyle"
									value="" /> *</td>
							</tr>
							<tr>
								<td>现价</td>
								<td><input type="text" name="price" class="InputStyle"
									value="" /> *</td>
							</tr>
							<tr>
								<td>产品列表</td>
								<td><textarea name="inventory" class="TextareaStyle"></textarea></td>
								<td><span>中间以英文,分隔&nbsp;如：</span><span style="color: red">网关监控摄像机02型（随便看）,专业安装服务</span></td>
							</tr>
							<tr>
                                <td width="80px">商品图片简介</td>
                                <td><input type="file" name="buy" /> *</td>
                            </tr>
						</table>
					</div>
				</div>
			</div>
			<!-- 表单操作 -->
			<div id="InputDetailBar">
				<input type="submit" value="添加" class="FunctionButtonInput">
				<a href="javascript:history.go(-1);" class="FunctionButton">返回</a>
			</div>
		</form>
	</div>
</body>
</html>