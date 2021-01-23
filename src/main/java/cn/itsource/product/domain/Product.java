package cn.itsource.product.domain;

import cn.itsource.basic.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product extends BaseDomain {
    //服务名
    private String name;
    //服务图片
    private String resources;
    //售价
    private BigDecimal saleprice;
    //下架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date offsaletime=new Date();
    //上架时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date onsaletime;
    //状态 默认下架
    private Integer state=0;
    //成本价
    private BigDecimal costprice;
    //创建服务时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime=new Date();
    //订单数量
    private Long salecount;

    //服务详细
    private ProductDetail productDetail;

}
