package cn.itsource.product.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.PageBean;
import cn.itsource.product.domain.Product;
import cn.itsource.product.query.ProductQuery;
import cn.itsource.product.service.IProductService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    
    @PutMapping
    public JsonResult save(@RequestBody Product product){
        try {
            if(product.getId()==null){
                productService.add(product);
            }else {
                productService.update(product);
            }
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        try {
            productService.delete(id);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
    //批量删除
    @PostMapping("/batchRemove")
    public JsonResult delete(@RequestBody List<Product> products){
        //System.out.println(product);
        try {
            productService.BatchRemove(products);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    @PostMapping
    public PageBean<Product> findPage(@RequestBody ProductQuery productQuery){
        return productService.findPage(productQuery);
    }

    @PostMapping("/onsale")
    public JsonResult onsale(@RequestBody List<Product> products){
        try {
            //批量上架产品服务
            productService.onsale(products);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
    @PostMapping("/offsale")
    public JsonResult offsale(@RequestBody List<Product> products){
        try {
            productService.offsale(products);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

//    ============================门户网站==========================
    @PostMapping("/queryHomePage")
    public PageBean<Product> queryHomePage(@RequestBody ProductQuery productQuery){
        return productService.findPage(productQuery);

    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id){
        return productService.findById(id);
    }

    
}
