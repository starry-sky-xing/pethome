package cn.itsource.pet.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.org.domain.Shop;
import cn.itsource.user.domain.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchMasterMsg extends BaseDomain {
    //宠物名
    private String name;
    //年龄
    private Integer age;
    //性别 公 母
    private Integer gender;
    //毛色
    private String coatColor;
    //图片
    private String resources;
    //类型
    private PetType petType;
    //价格
    private BigDecimal price;
    //地址
    private String address;
    //状态
    private Integer state=0;
    //标题
    private String title;
    //用户
    private User user;
    //店铺
    private Shop shop;

}
