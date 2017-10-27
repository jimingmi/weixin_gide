package com.javen.controller;

import java.util.Arrays;
import java.util.List;
import com.javen.model.Weeklypreferences;
import com.jfinal.core.Controller;

public class WeeklyPreferencesController extends Controller {

	//跳转到页面
	/*public void index(){
		setAttr("weeklypreferencesList", Weeklypreferences.getAll());
		render("weeklyPreferences.jsp");
	}*/

	public void index(){
		List<Weeklypreferences> list = Weeklypreferences.dao.getAllWp();
		Weeklypreferences weeklypreferences = list.get(0);
		List<String> inventory = Arrays.asList(weeklypreferences.get("inventory").toString().split(","));
		setAttr("weeklypreferences", weeklypreferences);
		setAttr("inventory", inventory);
		render("details.jsp");
	}

}
