package cn.itsource.order.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.user.domain.User;
import lombok.Data;

import java.util.Date;

/**
 * 用户地址
 */
@Data
public class UserAddress extends BaseDomain {
    private Date createTime;
    private Date updateTime;
    //联系人
    private String contacts;
    //地址定位到街道
    private String areaCode;
    //地址 详细地址
    private String address;
    //用户对象
    private User user;
    //areaCode+address
    private String fullAddress;
    //电话号码
    private String phone;
    //备用电话号码
    private String phoneBack;
    //固定电话号码
    private String tel;
    //邮政编号
    private String postCode;
    //邮箱
    private String email;
}
