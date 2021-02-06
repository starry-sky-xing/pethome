package cn.itsource.order.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.order.domain.ProductOrder;

public interface ProductOrderMapper extends BaseMapper<ProductOrder>{
    ProductOrder findByOrderSn(String orderSn);
}
