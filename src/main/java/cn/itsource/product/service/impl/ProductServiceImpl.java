package cn.itsource.product.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductDetail;
import cn.itsource.product.mapper.ProductDetailMapper;
import cn.itsource.product.mapper.ProductMapper;
import cn.itsource.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public void add(Product product) {
        //添加服务
        super.add(product);

        ProductDetail productDetail = product.getProductDetail();
        productDetail.setProduct(product);
        //添加服务详情
        productDetailMapper.add(productDetail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        //删除服务
        super.delete(id);
        //删除服务详情
        productDetailMapper.deleteByProductId(id);
    }

    @Override
    @Transactional
    public void BatchRemove(List<Product> products) {
        //批量删除
        super.BatchRemove(products);
        //批量删除详细
        productDetailMapper.BatchRemoveByProducts(products);
    }

    @Override
    @Transactional
    public void update(Product product) {
        //更新
        super.update(product);
        //更新 详细
        //获取服务详情
        ProductDetail productDetail = product.getProductDetail();
        productDetail.setProduct(product);
        //修改服务详情
        productDetailMapper.updateByProduct(productDetail);

    }

    //上架
    @Override
    public void onsale(List<Product> products) {
        productMapper.onsale(products);
    }

    //下架
    @Override
    public void offsale(List<Product> products) {
        productMapper.offsale(products);
    }
}
