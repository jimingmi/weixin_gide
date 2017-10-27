package com.javen.model;

import java.util.List;
import com.jfinal.plugin.activerecord.Model;


public class Weeklypreferences extends Model<Weeklypreferences> {

	private static final long				serialVersionUID	= 1L;
	public static final Weeklypreferences	dao					= new Weeklypreferences();

	public List<Weeklypreferences> getAllWp() {
		return dao.find("select * from weeklypreferences");
	}

	public Weeklypreferences getByWpId( String wp_id ) {
		return dao.findFirst("select * from weeklypreferences where wp_id = ?", wp_id);
	}

	public Weeklypreferences getFirst() {
		return dao.findFirst("select * from weeklypreferences");
	}

}
