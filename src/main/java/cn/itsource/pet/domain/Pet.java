package cn.itsource.pet.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.org.domain.Shop;
import cn.itsource.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Pet extends BaseDomain {
    //宠物名字
    private String name;
    //宠物图片
    private String resources;
    //售价
    private BigDecimal saleprice;
    //下架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date offsaletime=new Date();
    //上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date onsaletime;
    //状态 0下架 1上架
    private Integer state=0;
    //成本价
    private BigDecimal costprice;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime=new Date();
    //宠物类型
    private PetType petType;

    //宠物来源 1.寻主   2 街边  3.养殖场  4.配种
    private Integer sourceType;

    //宠物店铺
    private Shop shop;
    //用户
    private User user;
    //寻主信息
    private SearchMasterMsg searchMasterMsg;
    //宠物详细
    private PetDetail detail = new PetDetail();

    //收购订单的支付类型 应该在dto里面 为了方便就写在这里
    private Integer paytype;

}
