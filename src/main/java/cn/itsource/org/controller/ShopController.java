package cn.itsource.org.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.service.IEmployeeService;
import cn.itsource.org.service.IShopService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private IShopService iShopService;


    //商家入驻
    @PostMapping("/settlement")
    public JsonResult shopReg(@RequestBody Shop shop){
        try {
            iShopService.settled(shop);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

}
