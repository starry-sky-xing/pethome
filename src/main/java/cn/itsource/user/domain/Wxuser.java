package cn.itsource.user.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

@Data
public class Wxuser extends BaseDomain{
    //微信用户唯一标识
    private String openid;
    //昵称
    private String nickname;
    //性别
    private Integer sex;
    //地址
    private String address;
    //头像
    private String headimgurl;
    //登录信息
    private LoginInfo loginInfo;
}
