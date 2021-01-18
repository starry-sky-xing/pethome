package cn.itsource.org.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.user.domain.LoginInfo;
import lombok.Data;

@Data
public class Employee extends BaseDomain{

    private String username;
    private String email;
    private String phone;
    private String salt;//颜值
    private String password;
    private Integer age;
    private Integer state;
    private Department department;
    private LoginInfo logininfo;
    private Shop shop;

}
