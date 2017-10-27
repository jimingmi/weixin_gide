package com.javen.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;
import com.javen.interceptor.SessionInterceptor;
import com.javen.model.Weeklypreferences;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

@Before(SessionInterceptor.class)
public class WpBackController extends Controller {

	public void index() {
		setAttr("weeklyPreferencesList", Weeklypreferences.dao.getAllWp());
		render("wp.jsp");
	}

	public void add() {
		render("wp_add.jsp");
	}

	public void addDo() {
		Weeklypreferences weeklypreferences = new Weeklypreferences();

		//先上传文件(上传多文件)

		StringBuffer sbf=new StringBuffer();
		List<UploadFile> files = getFiles();
		String uploadPath = null;
		String realUploadPath = null;
		String filename = null;
		if (null!=files && files.size()>0) {
			//获取上传文件的路径
			uploadPath=files.get(0).getUploadPath();
			sbf.append(files.get(0).getFileName());
			filename = UUID.randomUUID().toString()+".jpg";
			realUploadPath = uploadPath+File.separator+filename;
			files.get(0).getFile().renameTo(new File(realUploadPath));
			weeklypreferences.set("images", "uploads"+File.separator+filename);
			sbf.append(files.get(1).getFileName());
			filename = UUID.randomUUID().toString()+".jpg";
			realUploadPath = uploadPath+File.separator+filename;
			files.get(1).getFile().renameTo(new File(realUploadPath));
			weeklypreferences.set("buy", "uploads"+File.separator+filename);
		}

		//后接受数据 (使用传统方式)

		weeklypreferences.set("wp_id", UUID.randomUUID().toString());
		weeklypreferences.set("name", getPara("name"));
		weeklypreferences.set("old_price", getPara("old_price"));
		weeklypreferences.set("price", getPara("price"));
		weeklypreferences.set("inventory", getPara("inventory"));

		//也可以使用getModel (这里注意表单中的name需要添加实体bean的小写)
		//weeklypreferences = getModel(Weeklypreferences.class);

		weeklypreferences.save();
		index();
	}

	public void delete() {
		String wp_id = getPara();
		Weeklypreferences.dao.deleteById(wp_id);
		index();
	}

}
