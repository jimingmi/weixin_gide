package com.javen.model;

import com.jfinal.plugin.activerecord.Model;


public class Login extends Model<Login> {

	private static final long serialVersionUID = 1L;
	public static final Login dao = new Login();

	public Login userLogin( String account , String password ) {
		return dao.findFirst("select * from user where account = ? and password = ?",account,password);
	}

}
