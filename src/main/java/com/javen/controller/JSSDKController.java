package com.javen.controller;

import com.javen.interceptor.JSSDKInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * @author Javen
 * 2016年5月13日
 */
/**
 * 对整个Controller或者其中的方法添加JSSDK签名验证拦截器
 */
@Before(JSSDKInterceptor.class)
public class JSSDKController extends Controller{
	
	public void index(){
		render("share.jsp");
	}
	
	public void customer(){
		render("customer.jsp");
	}
	
	public void pic(){
		render("pic.jsp");
	}
}
