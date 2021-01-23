package cn.itsource.user.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

@Data
public class LoginInfo extends BaseDomain {
    //账户名
    private String username;
    //电话
    private String phone;
    //邮件
    private String email;
    //盐值
    private String salt;
    //密码
    private String password;
    //0 商家/平台   1 用户
    private Integer type;
    //状态 0 禁用   1启用
    private Integer disable=1;

}
