package com.javen.controller;

import java.util.Arrays;
import java.util.List;
import com.javen.common.model.Weeklypreferences;
import com.jfinal.core.Controller;

public class WeeklyPreferencesController extends Controller {

	//跳转到页面
	/*public void index(){
		setAttr("weeklypreferencesList", Weeklypreferences.getAll());
		render("weeklyPreferences.jsp");
	}*/

	public void index(){
		List<Weeklypreferences> list = Weeklypreferences.getAll();
		Weeklypreferences weeklypreferences = list.get(0);
		List<String> inventory = Arrays.asList(weeklypreferences.getInventory().split(","));
		setAttr("weeklypreferences", weeklypreferences);
		setAttr("inventory", inventory);
		render("details.jsp");
	}

}
