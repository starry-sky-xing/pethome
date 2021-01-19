package cn.itsource.user.service.impl;

import cn.itsource.basic.utiles.HttpClientUtils;
import cn.itsource.user.constant.WeChatConstant;
import cn.itsource.user.domain.Wxuser;
import cn.itsource.user.mapper.WeChatMapper;
import cn.itsource.user.service.IWeChatService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeChatServiceImpl implements IWeChatService {
    @Autowired
    private WeChatMapper weChatMapper;

    @Override
    public void handleCallback(String code) {
        //通过code获取token
        String tokenUrl= WeChatConstant.TOKEN_URL.replace("APPID",WeChatConstant.APPID )
                .replace("SECRET", WeChatConstant.SECRET)
                .replace("CODE", code);
        //发送get请求，返回json字符串
        String tokenJsonStr = HttpClientUtils.httpGet(tokenUrl);
        //System.out.println(tokenJsonStr);
        //转成json对象
        JSONObject jsonObject = JSONObject.parseObject(tokenJsonStr);
        //获取access_token
        String access_token = jsonObject.getString("access_token");
        //获取 openid 用户凭证
        String openid = jsonObject.getString("openid");
        //获取微信用户信息url
        String userUrl = WeChatConstant.USER_URL.replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);
        //get请求获取用户信息
        String userInfoJsonStr = HttpClientUtils.httpGet(userUrl);
        //转json对象
        jsonObject = JSONObject.parseObject(userInfoJsonStr);
        //System.out.println(jsonObject);

        //通过 openid 唯一标识查询有没有这个用户 连表查询loginInfo
        Wxuser wxuser = weChatMapper.findByOpenid(openid);
        //如若查询的为空 就是第一次扫描登录 保存微信用户 绑定登录信息
        if(wxuser==null){
            //创建新的用户
            wxuser = new Wxuser();
            //微信用户唯一标识
            wxuser.setOpenid(openid);
            //昵称
            wxuser.setNickname(jsonObject.getString("nickname"));
            //性别
            wxuser.setSex(jsonObject.getInteger("sex"));
            //地址
            wxuser.setAddress(jsonObject.getString("country")+" " +
                    jsonObject.getString("province")+" "
                    +jsonObject.getString("city"));
            //头像
            wxuser.setHeadimgurl(jsonObject.getString("headimgurl"));
            //保存wxuser
            weChatMapper.add(wxuser);
            //登录信息 需要跳转绑定页面


        }else { //如果有用户 就判断有没有绑定电话，然后登录

        }


    }
}
