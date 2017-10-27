package com.javen.weixin.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.javen.entity.PayAttach;
import com.javen.kit.ZxingKit;
import com.javen.model.Order;
import com.javen.utils.StringUtils;
import com.javen.vo.AjaxResult;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.api.PaymentApi.TradeType;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.jfinal.weixin.sdk.utils.JsonUtils;

/**
 * @author Javen
 * 2016年3月19日
 */
public class WeixinPayController extends ApiController {
	
	
	static Log log=Log.getLog(WeixinApiController.class);
	private AjaxResult ajax = new AjaxResult();
	//商户相关资料
	String appid = PropKit.get("appId");
	String partner = PropKit.get("mch_id");
	String paternerKey = PropKit.get("paternerKey");
	String notify_url = PropKit.get("domain")+"/pay/pay_notify";
	
	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
	 * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		/**
		 *  是否对消息进行加密，对应于微信平台的消息加解密方式：
		 *  1：true进行加密且必须配置 encodingAesKey
		 *  2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}
	
	public void index() {
		
		// openId，采用 网页授权获取 access_token API：SnsAccessTokenApi获取
		String openId=getPara("openId");
		String total_fee=getPara("total_fee");
		if (StrKit.isBlank(total_fee)) {
			ajax.addError("请输入数字金额");
			renderJson(ajax);
			return;
		}
		// 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", partner);
		params.put("body", "Javen微信公众号极速开发");
		String out_trade_no=System.currentTimeMillis()+"";
		params.put("out_trade_no", out_trade_no);
		int price=((int)(Float.valueOf(total_fee)*100));
		params.put("total_fee", price+"");
		params.put("attach", JsonKit.toJson(new PayAttach(out_trade_no, 2, 3)));
		
		String ip = IpKit.getRealIp(getRequest());
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		
		params.put("spbill_create_ip", ip);
		params.put("trade_type", TradeType.JSAPI.name());
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("notify_url", notify_url);
		params.put("openid", openId);

		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);
		
		String xmlResult = PaymentApi.pushOrder(params);
		
		System.out.println(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			ajax.addError(return_msg);
			renderJson(ajax);
			return;
		}
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			ajax.addError(return_msg);
			renderJson(ajax);
			return;
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		String prepay_id = result.get("prepay_id");
		
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appId", appid);
		packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
		packageParams.put("nonceStr", System.currentTimeMillis() + "");
		packageParams.put("package", "prepay_id=" + prepay_id);
		packageParams.put("signType", "MD5");
		String packageSign = PaymentKit.createSign(packageParams, paternerKey);
		packageParams.put("paySign", packageSign);
		
		String jsonStr = JsonUtils.toJson(packageParams);
		ajax.success(jsonStr);
		renderJson(ajax);
	}
	
	public void pay_notify() {
		//获取所有的参数
		StringBuffer sbf=new StringBuffer();
				 
		Enumeration<String>  en=getParaNames();
		while (en.hasMoreElements()) {
			Object o= en.nextElement();
			sbf.append(o.toString()+"="+getPara(o.toString()));
		}
		
		log.error("支付通知参数："+sbf.toString());
		
		// 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
		String xmlMsg = HttpKit.readData(getRequest());
		System.out.println("支付通知="+xmlMsg);
		Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
		
		String appid  = params.get("appid");
		//商户号
		String mch_id  = params.get("mch_id");
		String result_code  = params.get("result_code");
		String openId      = params.get("openid");
		//交易类型
		String trade_type      = params.get("trade_type");
		//付款银行
		String bank_type      = params.get("bank_type");
		// 总金额
		String total_fee     = params.get("total_fee");
		//现金支付金额
		String cash_fee     = params.get("cash_fee");
		// 微信支付订单号
		String transaction_id      = params.get("transaction_id");
		// 商户订单号
		String out_trade_no      = params.get("out_trade_no");
		// 支付完成时间，格式为yyyyMMddHHmmss
		String time_end      = params.get("time_end");
		
		/////////////////////////////以下是附加参数///////////////////////////////////
		
		String attach      = params.get("attach");
		String fee_type      = params.get("fee_type");
		String is_subscribe      = params.get("is_subscribe");
		String err_code      = params.get("err_code");
		String err_code_des      = params.get("err_code_des");
		
		
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 避免已经成功、关闭、退款的订单被再次更新
		Order order = Order.dao.getOrderByTransactionId(transaction_id);
		if (order==null) {
			if(PaymentKit.verifyNotify(params, paternerKey)){
				if (("SUCCESS").equals(result_code)) {
					//更新订单信息
					log.warn("更新订单信息:"+attach);
					
					//发送通知等
					
					Map<String, String> xml = new HashMap<String, String>();
					xml.put("return_code", "SUCCESS");
					xml.put("return_msg", "OK");
					renderText(PaymentKit.toXml(xml));
					return;
				}
			}
		}
		renderText("");
	}
	
	public String getCodeUrl(){
		String url="weixin://wxpay/bizpayurl?sign=XXXXX&appid=XXXXX&mch_id=XXXXX&product_id=XXXXX&time_stamp=XXXXX&nonce_str=XXXXX";
		String product_id="001";
		String timeStamp=Long.toString(System.currentTimeMillis() / 1000);
		String nonceStr=Long.toString(System.currentTimeMillis());
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", partner);
		packageParams.put("product_id",product_id);
		packageParams.put("time_stamp", timeStamp);
		packageParams.put("nonce_str", nonceStr);
		String packageSign = PaymentKit.createSign(packageParams, paternerKey);
		
		return StringUtils.replace(url, "XXXXX", packageSign,appid,partner,product_id,timeStamp,nonceStr);
	}
	
	/**
	 * 生成支付二维码（模式一）并在页面上显示
	 */
	public void scanCode1(){
		//获取扫码支付（模式一）url
		String qrCodeUrl=getCodeUrl();
		System.out.println(qrCodeUrl);
		//生成二维码保存的路径
		String name = "payQRCode.png";
		Boolean encode = ZxingKit.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200,
				PathKit.getWebRootPath()+File.separator+"view"+File.separator+name );
		if (encode) {
			//在页面上显示
			setAttr("payQRCode", name);
			render("payQRCode.jsp");
		}
	}
	
	public void test(){
		renderText(getCodeUrl());
		
	}
	/**
	 * 扫码支付模式一回调
	 */
	public void wxpay(){
		try {
			HttpServletRequest request = getRequest();
			 /**
			 * 获取用户扫描二维码后，微信返回的信息
			 */
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
			    outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String result  = new String(outSteam.toByteArray(),"utf-8");
			System.out.println("callBack_xml>>>"+result);
			/**
			 * 获取返回的信息内容中各个参数的值
			 */
			Map<String, String> map = PaymentKit.xmlToMap(result);
			for (String key : map.keySet()) {
				   System.out.println("key= "+ key + " and value= " + map.get(key));
			}
			
			String appid=map.get("appid");
			String openid = map.get("openid");
			String mch_id = map.get("mch_id");
			String is_subscribe = map.get("is_subscribe");
			String nonce_str = map.get("nonce_str");
			String product_id = map.get("product_id");
			String sign = map.get("sign");
			Map<String, String> packageParams = new HashMap<String, String>();
			packageParams.put("appid", appid);
			packageParams.put("openid", openid);
			packageParams.put("mch_id",mch_id);
			packageParams.put("is_subscribe",is_subscribe);
			packageParams.put("nonce_str",nonce_str);
			packageParams.put("product_id", product_id);
			
			String packageSign = PaymentKit.createSign(packageParams, paternerKey);
			// 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("appid", appid);
			params.put("mch_id", mch_id);
			params.put("body", "测试扫码支付");
			String out_trade_no=Long.toString(System.currentTimeMillis());
			params.put("out_trade_no", out_trade_no);
			int price=((int)(Float.valueOf(10)*100));
			params.put("total_fee", price+"");
			params.put("attach", out_trade_no);
			
			String ip = IpKit.getRealIp(getRequest());
			if (StrKit.isBlank(ip)) {
				ip = "127.0.0.1";
			}
			
			params.put("spbill_create_ip", ip);
			params.put("trade_type", TradeType.NATIVE.name());
			params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
			params.put("notify_url", notify_url);
			params.put("openid", openid);

			String paysign = PaymentKit.createSign(params, paternerKey);
			params.put("sign", paysign);
			
			String xmlResult = PaymentApi.pushOrder(params);
			
			System.out.println("prepay_xml>>>"+xmlResult);
			
			/**
	         * 发送信息给微信服务器
	         */
			Map<String, String> payResult = PaymentKit.xmlToMap(xmlResult);
			
			String return_code = payResult.get("return_code");
			String result_code = payResult.get("result_code");
			
			if (StrKit.notBlank(return_code) && StrKit.notBlank(result_code) && return_code.equalsIgnoreCase("SUCCESS")&&result_code.equalsIgnoreCase("SUCCESS")) {
				// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
				String prepay_id = payResult.get("prepay_id");
				
				Map<String, String> prepayParams = new HashMap<String, String>();
				prepayParams.put("return_code", "SUCCESS");
				prepayParams.put("appId", appid);
				prepayParams.put("mch_id", mch_id);
				prepayParams.put("nonceStr", System.currentTimeMillis() + "");
				prepayParams.put("prepay_id", prepay_id);
				String prepaySign = null;
				if (sign.equals(packageSign)) {
					prepayParams.put("result_code", "SUCCESS");
				}else {
					prepayParams.put("result_code", "FAIL");
					prepayParams.put("err_code_des", "订单失效");   //result_code为FAIL时，添加该键值对，value值是微信告诉客户的信息
				}
				prepaySign = PaymentKit.createSign(prepayParams, paternerKey);
				prepayParams.put("sign", prepaySign);
				String xml = PaymentKit.toXml(prepayParams);
				log.error(xml);
				renderText(xml);
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 扫码支付模式二
	 */
	public void scanCode2() {
		
		String openId="o_pncsidC-pRRfCP4zj98h6slREw";
		String total_fee="2";
		
		// 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", partner);
		params.put("body", "Javen微信公众号极速开发");
		String out_trade_no=System.currentTimeMillis()+"";
		params.put("out_trade_no", out_trade_no);
		int price=((int)(Float.valueOf(total_fee)*100));
		params.put("total_fee", price+"");
		params.put("attach", JsonKit.toJson(new PayAttach(out_trade_no, 2, 3)));
		
		String ip = IpKit.getRealIp(getRequest());
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		
		params.put("spbill_create_ip", ip);
		params.put("trade_type", TradeType.NATIVE.name());
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("notify_url", notify_url);
		params.put("openid", openId);

		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);
		
		String xmlResult = PaymentApi.pushOrder(params);
		
		System.out.println(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			System.out.println(return_msg);
			renderText(xmlResult);
			return;
		}
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			System.out.println(return_msg);
			renderText(xmlResult);
			return;
		}
		//生成预付订单success
		
		String qrCodeUrl = result.get("code_url");
		String name = "payQRCode1.png";
		Boolean encode = ZxingKit.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200,
				PathKit.getWebRootPath()+File.separator+"view"+File.separator+name );
		if (encode) {
			//在页面上显示
			setAttr("payQRCode", name);
			render("payQRCode.jsp");
		}
	}

	/**
	 * 刷卡支付
	 * 文档：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=5_1
	 */
	public void micropay(){
		String url="https://api.mch.weixin.qq.com/pay/micropay";
		
		String total_fee="1";
		String auth_code = getPara("auth_code");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", partner);
		params.put("device_info", "javen205");//终端设备号
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("body", "刷卡支付测试");
//		params.put("detail", "json字符串");//非必须
		params.put("attach", "javen205");//附加参数非必须
		String out_trade_no=System.currentTimeMillis()+"";
		params.put("out_trade_no", out_trade_no);
		params.put("total_fee", total_fee);
		
		String ip = IpKit.getRealIp(getRequest());
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		
		params.put("spbill_create_ip", ip);
		params.put("auth_code", auth_code);
		
		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);
		
		String xmlResult = HttpUtils.post(url, PaymentKit.toXml(params));
		//同步返回结果
		System.out.println("xmlResult:"+xmlResult);
		
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		String return_code = result.get("return_code");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			//通讯失败 
			String err_code = result.get("err_code");
			//用户支付中，需要输入密码
			if (err_code.equals("USERPAYING")) {
				//等待5秒后调用【查询订单API】https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_2
				
			}
			renderText("通讯失败>>"+xmlResult);
			return;
		}
		
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			//支付失败
			renderText("支付失败>>"+xmlResult);
			return;
		}
		
		//支付成功 
		
		renderText(xmlResult);
	}
	/**
	 * 微信APP支付
	 */
	public void appPay(){
		//不用设置授权目录域名
		//统一下单地址 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1#
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("mch_id", partner);
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("body", "Javen微信公众号极速开发");
		String out_trade_no=System.currentTimeMillis()+"";
		params.put("attach", "custom json");
		params.put("out_trade_no", out_trade_no);
		int price=10000;
		params.put("total_fee", price+"");
		
		String ip = IpKit.getRealIp(getRequest());
		if (StrKit.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		
		params.put("spbill_create_ip", ip);
		params.put("notify_url", notify_url);
		params.put("trade_type", "APP");

		String sign = PaymentKit.createSign(params, paternerKey);
		params.put("sign", sign);
		
		String xmlResult = PaymentApi.pushOrder(params);
		
System.out.println(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = result.get("return_code");
		String return_msg = result.get("return_msg");
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
			ajax.addError(return_msg);
			renderJson(ajax);
			return;
		}
		String result_code = result.get("result_code");
		if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			ajax.addError(return_msg);
			renderJson(ajax);
			return;
		}
		// 以下字段在return_code 和result_code都为SUCCESS的时候有返回
		String prepay_id = result.get("prepay_id");
		//封装调起微信支付的参数 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partner);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("package", "Sign=WXPay");
		packageParams.put("noncestr", System.currentTimeMillis() + "");
		packageParams.put("timestamp", System.currentTimeMillis() / 1000 + "");
		String packageSign = PaymentKit.createSign(packageParams, paternerKey);
		packageParams.put("sign", packageSign);
		
		String jsonStr = JsonUtils.toJson(packageParams);
System.out.println("最新返回apk的参数:"+jsonStr);
		renderJson(jsonStr);
	}
}


