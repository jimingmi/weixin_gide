package com.javen.common;

import java.io.File;
import com.javen.common.model._MappingKit;
import com.javen.controller.AjaxController;
import com.javen.controller.AjaxFileContorlller;
import com.javen.controller.ConstellationController;
import com.javen.controller.FileController;
import com.javen.controller.JSSDKController;
import com.javen.controller.LoginController;
import com.javen.controller.MainController;
import com.javen.controller.TUserController;
import com.javen.controller.VideoController;
import com.javen.controller.WeeklyPreferencesController;
import com.javen.controller.WpBackController;
import com.javen.model.Login;
import com.javen.model.Weeklypreferences;
import com.javen.weixin.controller.RedPackApiController;
import com.javen.weixin.controller.WeiXinOauthController;
import com.javen.weixin.controller.WeixinApiController;
import com.javen.weixin.controller.WeixinMsgController;
import com.javen.weixin.controller.WeixinPayController;
import com.javen.weixin.controller.WeixinTransfersController;
import com.javen.weixin.user.UserController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

/**
 * @author Javen
 */
public class APPConfig extends JFinalConfig{
	static Log log = Log.getLog(WeixinMsgController.class);

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	/*public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}*/
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		/*loadProp("javen_config_pro.txt", "javen_config.txt");*/

		PropKit.use("javen_config_pro.txt");
		//PropKit.use("javen_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
		//设置上传文件保存的路径
		me.setBaseUploadPath(PathKit.getWebRootPath()+File.separator+"uploads");
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(me.getDevMode());

	}

	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
		//微信
		me.add("/msg", WeixinMsgController.class);
		me.add("/api", WeixinApiController.class);
		me.add("/oauth", WeiXinOauthController.class);
		me.add("/jssdk", JSSDKController.class,"/view");
		//可以去掉 /front
		me.add("/pay", WeixinPayController.class,"/view");

		me.add("/", LoginController.class,"/WEB-INF/jsp");
		me.add("/main",MainController.class,"/WEB-INF/jsp");
		me.add("/weeklyPreferences",WeeklyPreferencesController.class,"/front");
		me.add("/video",VideoController.class,"/front");
		me.add("/wpback",WpBackController.class,"/WEB-INF/jsp");


		me.add("/tuser", TUserController.class,"/back");
		me.add("/ajax", AjaxController.class);
		me.add("/constellation", ConstellationController.class,"/front");
		me.add("/wxuser", UserController.class,"/front");
		me.add("/file", FileController.class,"/front");
		me.add("/ajaxfile", AjaxFileContorlller.class,"/front");
		me.add("/read",RedPackApiController.class);
		me.add("/transfers",WeixinTransfersController.class);
	}

	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = APPConfig.createDruidPlugin();
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);

		arp.addMapping("user", Login.class);
		arp.addMapping("weeklypreferences", "wp_id",Weeklypreferences.class);

		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);

		/*C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.addMapping("course", Course.class);
		arp.addMapping("orders", Order.class);
		arp.addMapping("users","id", Users.class);
		arp.addMapping("Tuser", TUser.class);
		arp.addMapping("stock", Stock.class);
		arp.addMapping("idea", Idea.class);
		arp.setShowSql(true);
		me.add(arp);

		// ehcahce插件配置
		me.add(new EhCachePlugin());


		SchedulerPlugin sp = new SchedulerPlugin("job.properties");
		me.add(sp);*/
	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {

	}

	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {

	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);//启动配置项
	}
	@Override
	public void configEngine( Engine arg0 ) {
		// TODO Auto-generated method stub

	}

}
