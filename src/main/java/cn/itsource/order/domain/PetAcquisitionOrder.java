package cn.itsource.order.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.pet.domain.Pet;
import cn.itsource.user.domain.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 宠物收购订单
 */
@Data
public class PetAcquisitionOrder extends BaseDomain {
    //摘要
    private String digest;
    //1 待报账(垫付) 2 待打款(银行转账)  3 完成
    private Integer state;
    //收购价格
    private BigDecimal price;
    //收购地址
	private String address;
	//订单编号
    private String orderSn;
    //完成时间
    private Date lastcomfirmtime;
    //宠物
    private Pet pet;
    //信息发布者
    private User user;
    //支付类型  1垫付 2银行转账
    private Integer paytype;
    //收购订单对应的店铺
    private Shop shop;
    //员工
    private Employee employee;

}
