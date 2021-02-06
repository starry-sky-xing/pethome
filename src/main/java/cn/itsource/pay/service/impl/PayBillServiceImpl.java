package cn.itsource.pay.service.impl;

import cn.itsource.basic.config.AlipayConfig;
import cn.itsource.basic.constant.PayConstants;
import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.order.domain.ProductOrder;
import cn.itsource.order.mapper.ProductOrderMapper;
import cn.itsource.pay.domain.AlipayInfo;
import cn.itsource.pay.domain.PayBill;
import cn.itsource.pay.mapper.AlipayInfoMapper;
import cn.itsource.pay.mapper.PayBillMapper;
import cn.itsource.pay.service.IPayBillService;
import cn.itsource.product.mapper.ProductMapper;
import cn.itsource.quartz.service.IQuartzService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ParameterMetaData;
import java.util.Date;


@Service
public class PayBillServiceImpl extends BaseServiceImpl<PayBill> implements IPayBillService {

    @Autowired
    private AlipayInfoMapper alipayInfoMapper;

    @Autowired
    private  PayBillMapper payBillMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private IQuartzService quartzService;
    /**
     * 去付钱啊
     * @param productOrder
     */
    @Override
    public String toAlipay(ProductOrder productOrder) {
        try {
            //根据商店id查询支付信息
            AlipayInfo alipayInfo = alipayInfoMapper.findByShopId(productOrder.getShop().getId());
            AlipayClient alipayClient =  new DefaultAlipayClient(AlipayConfig.gatewayUrl,
                     alipayInfo.getAppid(),alipayInfo.getMerchant_private_key(), "json",
                     AlipayConfig.charset, alipayInfo.getAlipay_public_key(),
                     AlipayConfig.sign_type);  //获得初始化的AlipayClient

            AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
            alipayRequest.setReturnUrl( AlipayConfig.return_url );
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url); //在公共参数中设置回跳和通知地址

            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = productOrder.getOrderSn();
            //付款金额，必填
            String total_amount = productOrder.getPrice().toString();
            //订单名称，必填
            String subject = productOrder.getDigest();
            //商品描述，可空
            String body = productOrder.getDigest();
            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                    + "\"total_amount\":\""+ total_amount +"\","
                    + "\"subject\":\""+ subject +"\","
                    + "\"body\":\""+ body +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 处理订单信息
     * @param out_trade_no 商家订单号
     * @param trade_no 支付宝交易号
     */
    @Override
    @Transactional
    public void handleState(String out_trade_no, String trade_no) {
        //获取支付单对象
        PayBill payBill = payBillMapper.findByOrderSn(out_trade_no);
        //设置完成时间
        payBill.setUpdateTime(new Date());
        //支付单交易号
        payBill.setUnionPaySn(trade_no);

        //修改状态
        payBill.setState(PetHomeConstant.PAYMENT_COMPLETED);
        //修改支付单对象
        payBillMapper.update(payBill);
        //修改订单状态
        switch (payBill.getBusinessType()){
            case PayConstants.BUSINESSTYPE_PRODUCT: //服务

                //获取服务订单
                ProductOrder productOrder = productOrderMapper.findById(payBill.getBusinessKey());
                //支付完成
                productOrder.setState(PetHomeConstant.PAYMENT_COMPLETED);
                //支付订单编号
                productOrder.setPaySn(trade_no);
                //最后时间
                productOrder.setLastConfirmTime(new Date());
                productOrderMapper.update(productOrder);


                //关闭定时器
                quartzService.removeJob(PayConstants.BUSINESSTYPE_PRODUCT+productOrder.getOrderSn());
                break;
            case PayConstants.BUSINESSTYPE_ADOPT:  //宠物
                break;
            case PayConstants.BUSINESSTYPE_GOODS: //商品
                break;
        }
    }
}
