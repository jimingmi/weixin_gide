package com.javen.interceptor;

import java.util.UUID;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;
import com.jfinal.weixin.sdk.api.JsTicketApi.JsApiType;

/**
 * @author Javen
 * 2016年5月13日
 */
public class JSSDKInterceptor   implements Interceptor{
	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
	 * ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));

		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey",
				"setting it in config file"));
		return ac;
	}
	
	@Override
	public void intercept(Invocation inv) {
		inv.invoke();
		Controller controller = inv.getController();
		ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
		JsTicket jsApiTicket = JsTicketApi.getTicket(JsApiType.jsapi);
		String jsapi_ticket = jsApiTicket.getTicket();
		String nonce_str = create_nonce_str();
		// 注意 URL 一定要动态获取，不能 hardcode.
		String url = "http://" + controller.getRequest().getServerName() // 服务器地址
				// + ":"
				// + getRequest().getServerPort() //端口号
				+ controller.getRequest().getContextPath() // 项目名称
				+ controller.getRequest().getServletPath();// 请求页面或其他地址
		String qs = controller.getRequest().getQueryString(); // 参数
		if (qs != null) {
			url = url + "?" + (controller.getRequest().getQueryString());
		}
		System.out.println("url>>>>" + url);
		String timestamp = create_timestamp();
		// 这里参数的顺序要按照 key 值 ASCII 码升序排序
		//注意这里参数名必须全部小写，且必须有序
		String  str = "jsapi_ticket=" + jsapi_ticket +
        "&noncestr=" + nonce_str +
        "&timestamp=" + timestamp +
        "&url=" + url;

		String signature = HashKit.sha1(str);

		System.out.println("appId " + ApiConfigKit.getApiConfig().getAppId()
				+ "  nonceStr " + nonce_str + " timestamp " + timestamp);
		System.out.println("url " + url + " signature " + signature);
		System.out.println("nonceStr " + nonce_str + " timestamp " + timestamp);
		System.out.println(" jsapi_ticket " + jsapi_ticket);
		System.out.println("nonce_str  " + nonce_str);
		
		controller.setAttr("appId", ApiConfigKit.getApiConfig().getAppId());
		controller.setAttr("nonceStr", nonce_str);
		controller.setAttr("timestamp", timestamp);
		controller.setAttr("url", url);
		controller.setAttr("signature", signature);
		controller.setAttr("jsapi_ticket", jsapi_ticket);
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}
}
