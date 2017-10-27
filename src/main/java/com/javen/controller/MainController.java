package com.javen.controller;

import com.javen.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

@Before(SessionInterceptor.class)
public class MainController extends Controller {

	public void index() {
		render("index.jsp");
	}

}
