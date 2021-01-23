package cn.itsource.product.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductDetail;

import java.util.List;

public interface ProductDetailMapper extends BaseMapper<ProductDetail> {
    //根据服务删除详细
    void deleteByProductId(Long id);
    //批量删除详细
    void BatchRemoveByProducts(List<Product> products);

    void updateByProduct(ProductDetail productDetail);
}
