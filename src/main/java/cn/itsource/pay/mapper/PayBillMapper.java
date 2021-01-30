package cn.itsource.pay.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.pay.domain.PayBill;

public interface PayBillMapper extends BaseMapper<PayBill> {
    PayBill findByOrderSn(String out_trade_no);
}
