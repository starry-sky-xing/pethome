package cn.itsource.order.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.order.domain.ProductOrderDetail;

import java.util.List;

public interface ProductOrderDetailMapper extends BaseMapper<ProductOrderDetail> {
    //添加多个
    void batchSave(List<ProductOrderDetail> productOrderDetail);

}
