package cn.itsource.order.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.org.domain.Shop;
import cn.itsource.user.domain.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductOrder extends BaseDomain {
    //摘要
    private String digest;
    //状态   待支付1 待消费2 待确认3 完成4 取消-1
    private Integer state=1;
    //价格
    private BigDecimal price;
    //支付方式  1银联   2 微信  3支付宝
    private Integer payType;
    //订单编号
    private String orderSn;
    //支付单号
    private String paySn;
    //最后支付时间
    private Date lastPayTime;
    //最后确认时间
    private Date lastConfirmTime;
    //用户
    private User user;
    //店铺
    private Shop shop;



}
