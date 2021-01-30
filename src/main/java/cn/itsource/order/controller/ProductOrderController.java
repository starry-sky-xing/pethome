package cn.itsource.order.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.SystemContext;
import cn.itsource.order.service.IProductOrderService;
import cn.itsource.user.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {

    @Autowired
    private IProductOrderService productOrderService;

    @PostMapping("/submitOrder")
    public JsonResult submitOrder(@RequestBody Map<String,Object> param, HttpServletRequest request){
        try {
            LoginInfo loginInfo = SystemContext.getLoginInfo(request);
            //返回的是支付宝支付页面
            String submitOrder = productOrderService.submitOrder(param, loginInfo);
            return JsonResult.ResultObj(submitOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
}
