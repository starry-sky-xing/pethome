package cn.itsource.basic.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	



	// 服务器异步通知页面路径 （告诉支付宝异步请求的url地址） 注意：此url地址，必须是能被外网所访问到的
    //http://172.16.2.254/alipay/notifyUrl
	public static String notify_url = " http://j6k9am.natappfree.cc/pay/notifyUrl";

	// 页面跳转同步通知页面路径(告诉支付宝同步请求的url地址)
	public static String return_url = " http://j6k9am.natappfree.cc/pay/returnUrl";

	// 签名方式（数据传输采用非对称加密）
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关接口
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";


	


}

