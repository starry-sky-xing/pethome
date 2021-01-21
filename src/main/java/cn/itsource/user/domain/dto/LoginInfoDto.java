package cn.itsource.user.domain.dto;

import lombok.Data;

/**
 * 登录临时信息
 */
@Data
public class LoginInfoDto {
    //用户名
    private String username;
    //电话
    private String phone;
    //验证码
    private String code;
    //密码
    private String password;
    //重复密码
    private String passwordRepeat;
    //类型
    private Integer type;

    //微信用户的openid
    private String openid;
}
