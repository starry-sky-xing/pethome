package cn.itsource.pay.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.order.domain.ProductOrder;
import cn.itsource.pay.domain.PayBill;

public interface IPayBillService extends IBaseService<PayBill> {
    //跳转支付宝
    String toAlipay(ProductOrder productOrder);

    //修改支付完成后的状态
    void handleState(String out_trade_no, String trade_no);
}
