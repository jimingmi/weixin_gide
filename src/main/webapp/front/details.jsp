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
<meta name="format-detection" content="telephone=yes" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>每周特惠</title>
<!-- <meta name="viewport"
	content="width=device-width;initial-scale=1.0;maximum-scale=1.0;user-scalable=0;" /> -->
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
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
	<!-- <form name="aspnetForm" method="post" action=""
		id="aspnetForm"> -->
	<!-- <div>
		<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE"
			value="/wEPDwUJMjUyMzU2NTUzD2QWAmYPZBYCAgEPZBYCAgEPZBYCAgEPZBYGZg8PFgIeBFRleHQFD+iQqOaRqeWFheeUteWunWRkAgEPDxYCHwAFBDk2MDBkZAICDxYCHwAF4Qc8cCBzdHlsZT0idGV4dC1pbmRlbnQ6MHB4O2NvbG9yOiM5OTk5OTk7Ij4NCgk8ZW0+PHN0cm9uZz7kuqflk4Hlj4LmlbDvvJo8L3N0cm9uZz48L2VtPg0KPC9wPg0KPHVsIHN0eWxlPSJ0ZXh0LWluZGVudDowcHg7Y29sb3I6IzQwNDA0MDsiPg0KCTxsaSBzdHlsZT0iY29sb3I6IzY2NjY2Njt2ZXJ0aWNhbC1hbGlnbjp0b3A7Ij4NCgkJ5ZOB54mMOiZuYnNwO2VtaWUv5Lq/6KeF44CCDQoJPC9saT4NCgk8bGkgc3R5bGU9ImNvbG9yOiM2NjY2NjY7dmVydGljYWwtYWxpZ246dG9wOyI+DQoJCeWei+WPtzombmJzcDtlbWll6JCo5pGp56e75Yqo55S15rqQDQoJPC9saT4NCgk8bGkgc3R5bGU9ImNvbG9yOiM2NjY2NjY7dmVydGljYWwtYWxpZ246dG9wOyI+DQoJCeminOiJsuWIhuexuzombmJzcDvpu4ToibLokKjmkakmbmJzcDvnmb3oibLokKjmkakr6aaZ5riv5r2u6ZuG5YWF55S15aS0Jm5ic3A76buE6Imy6JCo5pGpK+mmmea4r+a9rumbhuWFheeUteWktCZuYnNwO+eZveiJsuiQqOaRqQ0KCTwvbGk+DQoJPGxpIHN0eWxlPSJjb2xvcjojNjY2NjY2O3ZlcnRpY2FsLWFsaWduOnRvcDsiPg0KCQnnlLXmsaDlrrnph486Jm5ic3A7NTAwMU1BaCjlkKspLTYwMDBNQWgo5ZCrKQ0KCTwvbGk+DQoJPGxpIHN0eWxlPSJjb2xvcjojNjY2NjY2O3ZlcnRpY2FsLWFsaWduOnRvcDsiPg0KCQnmmK/lkKbmlK/mjIHlpKrpmLPog706Jm5ic3A75ZCmDQoJPC9saT4NCgk8bGkgc3R5bGU9ImNvbG9yOiM2NjY2NjY7dmVydGljYWwtYWxpZ246dG9wOyI+DQoJCemAgueUqOexu+WeizombmJzcDvpgJrnlKgNCgk8L2xpPg0KCTxsaSBzdHlsZT0iY29sb3I6IzY2NjY2Njt2ZXJ0aWNhbC1hbGlnbjp0b3A7Ij4NCgkJTEVE54Gv54Wn5piOOiZuYnNwO+WQpg0KCTwvbGk+DQoJPGxpIHN0eWxlPSJjb2xvcjojNjY2NjY2O3ZlcnRpY2FsLWFsaWduOnRvcDsiPg0KCQnnlLXmsaDnsbvlnos6Jm5ic3A76ZSC55S15rGgDQoJPC9saT4NCjwvdWw+DQo8cD4NCgkmbmJzcDsNCjwvcD5kZHUtAGPcaINW5BgtgZf7KsDAZt1n" />
	</div>
	<div>

		<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION"
			value="/wEWAgLgkbuuCQKI+JrmBf+Ul372cpCvOJOEI7fXW4euRKUJ" />
	</div> -->
	<div id="ctl00_ContentPlaceHolder1_Panel1">

		<div class="container width90">
			<h3>
				<span id="commodity_name">${requestScope.weeklypreferences.name }</span>
			</h3>
			<p>
				原价：<span id="ctl00_ContentPlaceHolder1_txtGiftCost"><del>${requestScope.weeklypreferences.old_price }</del></span>
			<h3 style="color: #FF7F00">
				活动价格：<em>${requestScope.weeklypreferences.price }</em>
			</h3>
			<h6 style="color: #B8B8B8">
				<span>*另需安装费150元</span>
			</h6>
			</p>
			<p>
			<p style="text-indent: 0px; color: #999999;">
				<em><strong>产品清单：</strong></em>
			</p>
			<ul style="text-indent: 0px; color: #404040;">
			    <c:forEach items="${requestScope.inventory }" var="list">
                    <li style="color: #666666; vertical-align: top;">${list }</li>
			    </c:forEach>
			</ul>
			<p>&nbsp;</p>
			</p>
			<div class="imgbox">
				<img src="${requestScope.weeklypreferences.images }">
			</div>
			<p>&nbsp;</p>
			</p>
			<div class="control-group tc">
				<p class="red"></p>
				<input type="submit" name="ctl00$ContentPlaceHolder1$btnOK"
					value="立即咨询" id="ctl00_ContentPlaceHolder1_btnOK"
					class="btn-large green button width80" style="width: 80%;"
					onclick="window.open('tel:15958937697')" />
			</div>
		</div>
	</div>
	<!-- </form> -->
	<div>
		<img alt="微信联系" src="resource/images/weixin.jpg">
	</div>
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