package cn.itsource.product.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.product.domain.Product;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    //上架多个服务
    void onsale(List<Product> products);

    void offsale(List<Product> products);
}
