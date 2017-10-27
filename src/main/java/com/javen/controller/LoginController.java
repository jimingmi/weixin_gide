package com.javen.controller;

import com.javen.model.Login;
import com.jfinal.core.Controller;


public class LoginController extends Controller {

	public void index() {
		render("login.jsp");
	}

	public void login() {
		String account = getPara("account");
		String password = getPara("password");
		Login login = Login.dao.userLogin(account,password);
		if (login != null) {
			setSessionAttr("session_user", login);
			redirect("main");
		} else {
			index();
		}
	}

	public void exit() {
		removeSessionAttr("session_user");
		redirect("/");
	}

}
