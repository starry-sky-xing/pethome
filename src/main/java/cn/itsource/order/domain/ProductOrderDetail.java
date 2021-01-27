package cn.itsource.order.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.product.domain.Product;
import lombok.Data;

import java.util.Date;

@Data
public class ProductOrderDetail extends BaseDomain {

    //服务产品
    private Product product;
    //销售数量
    private Integer salecount;
    //服务订单
    private ProductOrder order;


}
