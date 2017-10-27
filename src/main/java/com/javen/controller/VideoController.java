package com.javen.controller;

import com.jfinal.core.Controller;

public class VideoController extends Controller {

	//跳转到视频页面
	public void index(){
		render("video.jsp");
	}

	public void detailsPages(){
		render("detailsPages.jsp");
	}
}
