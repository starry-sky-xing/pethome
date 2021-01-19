package cn.itsource.user.constant;

import springfox.documentation.service.ApiListing;

/**
 * 微信登录需要的
 */
public class WeChatConstant {
    public static final String CODE_URL="https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
    //APPID 应用唯一标识
    public static final String APPID="wxd853562a0548a7d0";
    //请使用urlEncode对链接进行处理 返回
    public static final String REDIRECT_URI = "http://bugtracker.itsource.cn/wechat/callback";
    public static final String SECRET = "4a5d5615f93f24bdba2ba8534642dbb6";


    //获取token的url
    public static final String TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    //获取用户信息的url
    public static final String USER_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
}
