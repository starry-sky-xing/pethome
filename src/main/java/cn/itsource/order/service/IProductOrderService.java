package cn.itsource.order.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.order.domain.ProductOrder;
import cn.itsource.user.domain.LoginInfo;

import java.util.Map;

public interface IProductOrderService extends IBaseService<ProductOrder>{
    /**
     * 服务订单
     * @param param
     * @param loginInfo 登录信息
     */
    void submitOrder(Map<String, Object> param, LoginInfo loginInfo);
}
