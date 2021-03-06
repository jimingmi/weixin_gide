package com.javen.common.model;

import java.util.ArrayList;
import com.javen.common.model.base.BaseWeeklypreferences;

/**
 * Generated by JFinal.
 */
public class Weeklypreferences extends BaseWeeklypreferences<Weeklypreferences> {
	private static final long serialVersionUID = 1L;
	public static final Weeklypreferences dao = new Weeklypreferences().dao();

	public static ArrayList<Weeklypreferences> getAll(){
		return (ArrayList<Weeklypreferences>) dao.find("select * from weeklypreferences");
	}

	public static Weeklypreferences getById(String wp_id){
		return dao.find("select * from weeklypreferences").get(0);
	}
}
