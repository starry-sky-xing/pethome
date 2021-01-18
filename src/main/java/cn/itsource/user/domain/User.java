package cn.itsource.user.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class User extends BaseDomain{
    //用户名
    private String username;
    //邮件
    private String email;
    //电话
    private String phone;
    //盐值
    private String salt;
    //密码
    private String password;
    //0 禁用 1 启用
    private Integer state;
    //年龄
    private Integer age;
    //时间
    private Date createtime =new Date();
    //头像
    private String headImg;
    //登录信息
    private  LoginInfo loginInfo;

}
