package cn.itsource.org.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class Shop extends BaseDomain{
    private String name;//店铺名称
    private String tel;//电话
    private Date registerTime=new Date();//日期
    private Integer state;//启用
    private String address;//地址
    private String logo;//logo
    private Employee admin;//员工
}
