package cn.itsource.user.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class User extends BaseDomain{
    private String username;
    private String email;
    private String phone;
    private String salt;
    private String password;
    private Integer state;
    private Integer age;
    private Date createtime =new Date();
    private String headImg;
}
