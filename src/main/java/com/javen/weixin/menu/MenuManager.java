/**
 * Copyright (c) 2015-2016, Javen Zhou  (javen205@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.javen.weixin.menu;


import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;

/**
 * 菜单管理器类
 * @author Javen
 * 2016年5月30日
 */
public class MenuManager  {

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public static void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}

	public static void main(String[] args) {

		// 将菜单对象转换成json字符串
		//有问题：主菜单项多了一个type
		String jsonMenu = JsonKit.toJson(MenuManager.getTestMenu()).toString();
		System.out.println(jsonMenu);
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		MenuManager.loadProp("javen_config_pro.txt", "javen_config.txt");
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		//			ac.setAppId(PropKit.get("appId"));
		//			ac.setAppSecret(PropKit.get("appSecret"));
		ApiConfigKit.setThreadLocalApiConfig(ac);

		//创建菜单
		ApiResult apiResult=MenuApi.createMenu(jsonMenu);
		System.out.println(apiResult.getJson());
	}

	/**
	 * 组装菜单数据
	 *
	 * @return
	 */
	private static Menu getTestMenu() {
		ViewButton btn11 = new ViewButton();
		btn11.setName("闪蝶商城");
		btn11.setType("view");
		btn11.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000001&idx=1&sn=3998b445f852a82bdf5d2769f5800ea6&chksm=7eb9651c49ceec0a2a7b9e463a0151f0e8cbab9184f19604bb5e3f978b42ef6b327f3bf0241a&mpshare=1&scene=23&srcid=0904kQFrGNDiLVQeIVvGEfzD#rd");

		ViewButton btn21 = new ViewButton();
		btn21.setName("视频场景展示");
		btn21.setType("view");
		//btn21.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000029&idx=1&sn=1bebe2d77642ea13271a341032c41c57&chksm=7eb9650049ceec16df07432308080090a25c749c33b4cd69993143b183b33965a22d47ad687c&mpshare=1&scene=23&srcid=0904L9mEe8f6pVsOng4NvnQv#rd");
		btn21.setUrl("http://111.231.66.140/video");

		ViewButton btn22 = new ViewButton();
		btn22.setName("每周特惠");
		btn22.setType("view");
		btn22.setUrl("http://111.231.66.140/weeklyPreferences");

		/*btn22.setName("合作洽谈");
		btn22.setType("view");
		btn22.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000007&idx=1&sn=205205cd454eb29532ee8fdb4faf218a&chksm=7eb9651a49ceec0c8be4794ebbf7e0c2ab34ffbd253674e234e1b2e90eeb621ba0d0802c236a&mpshare=1&scene=23&srcid=0904IEb7QRCkkq1IElJFNdt5#rd");
		 */
		ViewButton btn23 = new ViewButton();
		btn23.setName("免费体验");
		btn23.setType("view");
		btn23.setUrl("https://m.zhundao.net/event/38934");

		/*btn23.setName("公司简介");
		btn23.setType("view");
		btn23.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000019&idx=1&sn=c04604821ecbcb03ec37dfac7191447c&chksm=7eb9650e49ceec1828a96a7895fad17db4f30d9cf2c166fa46bd4dd65a0ac8ca77a9ac46bff0&mpshare=1&scene=23&srcid=0904uX9LQcGjUu9qNXnrxlo9#rd");
		 */
		ViewButton btn31 = new ViewButton();
		btn31.setName("联系我们");
		btn31.setType("view");
		btn31.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000025&idx=1&sn=60e18d9171a3dae07b8c219c95cdba35&chksm=7eb9650449ceec129624af163a1050470c3fc218844a0492512b335ae1cb27c40bec18407363&mpshare=1&scene=23&srcid=09041Cn5IeZg3cZgvMrijanB#rd");

		ViewButton btn32 = new ViewButton();
		btn32.setName("智能家居合作");
		btn32.setType("view");
		btn32.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000013&idx=1&sn=734f54dcaf639750c7285a07a49c5cfc&chksm=7eb9651049ceec0676de8aa9fafb60b8dc60098c28385ea9d4df2b6c473c01e6af3f8550e75a&mpshare=1&scene=23&srcid=0904k9g0QYh7oo5w5ectXuuP#rd");

		ViewButton btn33 = new ViewButton();
		btn33.setName("企业战略合作");
		btn33.setType("view");
		btn33.setUrl("http://mp.weixin.qq.com/s?__biz=MzU5OTIyNTcxNA==&mid=100000011&idx=1&sn=849794069f6884b3ebdd85fa4aaf8fff&chksm=7eb9651649ceec006c1f3f6e6658db2135afb1e7a385e9e5e5b39a420d278e8690c635295ff6&mpshare=1&scene=23&srcid=0904F2D3SjVxmg02WBfasCPw#rd");

		ComButton mainBtn2 = new ComButton();
		mainBtn2.setName("活动专区");
		mainBtn2.setSub_button(new Button[] { btn21, btn22 ,btn23});

		ComButton mainBtn3 = new ComButton();
		mainBtn3.setName("合作洽谈");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33});

		/*ClickButton btn11 = new ClickButton();
		btn11.setName("微信相册发图");
		btn11.setType("pic_weixin");
		btn11.setKey("rselfmenu_1_1");

		ClickButton btn12 = new ClickButton();
		btn12.setName("拍照或者相册发图");
		btn12.setType("pic_photo_or_album");
		btn12.setKey("rselfmenu_1_2");;

		ClickButton btn13 = new ClickButton();
		btn13.setName("系统拍照发图");
		btn13.setType("pic_sysphoto");
		btn13.setKey("rselfmenu_1_3");

		ClickButton btn21 = new ClickButton();
		btn21.setName("扫码带提示");
		btn21.setType("scancode_waitmsg");
		btn21.setKey("rselfmenu_2_1");;

		ClickButton btn22 = new ClickButton();
		btn22.setName("扫码推事件");
		btn22.setType("scancode_push");
		btn22.setKey("rselfmenu_2_2");;

		ViewButton btn23 = new ViewButton();
		btn23.setName("我的设备");
		btn23.setType("view");
		btn23.setUrl("https://hw.weixin.qq.com/devicectrl/panel/device-list.html?appid=wx614c453e0d1dcd12");

		ViewButton btn31 = new ViewButton();
		btn31.setName("微社区");
		btn31.setType("view");
		btn31.setUrl("http://whsf.tunnel.mobi/whsf/msg/wsq");


		ClickButton btn32 = new ClickButton();
		btn32.setName("发送位置");
		btn32.setType("location_select");
		btn32.setKey("rselfmenu_3_2");

		//http://tencent://message/?uin=572839485&Site=在线咨询&Menu=yes
		//http://wpa.qq.com/msgrd?v=3&uin=572839485&site=qq&menu=yes

		ViewButton btn33 = new ViewButton();
		btn33.setName("在线咨询");
		btn33.setType("view");
		btn33.setUrl("http://wpa.qq.com/msgrd?v=3&uin=572839485&site=qq&menu=yes");

		ViewButton btn34 = new ViewButton();
		btn34.setName("我的博客");
		btn34.setType("view");
		btn34.setUrl("http://www.cnblogs.com/zyw-205520");

		ClickButton btn35 = new ClickButton();
		btn35.setName("点击事件");
		btn35.setType("click");
		btn35.setKey("rselfmenu_3_5");

		ComButton mainBtn1 = new ComButton();
		mainBtn1.setName("闪蝶商城");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13});

		ComButton mainBtn2 = new ComButton();
		mainBtn2.setName("扫码");
		mainBtn2.setSub_button(new Button[] { btn21, btn22 ,btn23});

		ComButton mainBtn3 = new ComButton();
		mainBtn3.setName("个人中心");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34 ,btn35 });*/

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 *
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { btn11, mainBtn2, mainBtn3 });
		return menu;
	}
}
