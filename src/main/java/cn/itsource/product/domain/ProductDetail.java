package cn.itsource.product.domain;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.product.domain.Product;
import lombok.Data;

@Data
public class ProductDetail extends BaseDomain{
    //服务id
    private Product product;
    //服务详细
    private String intro;
    //预约注意事项
    private String orderNotice;



}
