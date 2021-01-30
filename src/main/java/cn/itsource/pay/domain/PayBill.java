package cn.itsource.pay.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.org.domain.Shop;
import cn.itsource.user.domain.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayBill extends BaseDomain {
    //摘要
    private String digest;
    //金额
    private BigDecimal money;
    //支付宝统一支付编号
    private String unionPaySn;
    //1待支付 2已支付 -1取消
    private Integer state=1;
    //最后支付时间
    private Date lastPayTime;
    //支付方式
    private Integer payChannel; //1银联 2微信 3支付宝
    //业务类型
    private String businessType;
    //业务键
    private Long businessKey;
    //完成或者取消的时间
    private Date updateTime;
    //建立时间
    private Date createTime;
    //用户
    private User user;
    //商家
    private Shop shop;

    //订单编号
    private String orderSn;
}
