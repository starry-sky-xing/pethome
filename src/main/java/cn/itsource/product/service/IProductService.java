package cn.itsource.product.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.product.domain.Product;

import java.util.List;

public interface IProductService extends IBaseService<Product>{


    //上架
    void onsale(List<Product> products);

    void offsale(List<Product> products);
}
