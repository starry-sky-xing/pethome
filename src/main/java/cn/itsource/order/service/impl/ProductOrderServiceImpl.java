package cn.itsource.order.service.impl;

import cn.itsource.basic.constant.PayConstants;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.utiles.CodeGenerateUtils;
import cn.itsource.basic.utiles.DistanceUtil;
import cn.itsource.order.domain.OrderAddress;
import cn.itsource.order.domain.ProductOrder;
import cn.itsource.order.domain.ProductOrderDetail;
import cn.itsource.order.domain.UserAddress;
import cn.itsource.order.mapper.OrderAddressMapper;
import cn.itsource.order.mapper.ProductOrderDetailMapper;
import cn.itsource.order.mapper.ProductOrderMapper;
import cn.itsource.order.mapper.UserAddressMapper;
import cn.itsource.order.service.IProductOrderService;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.ShopMapper;
import cn.itsource.pay.domain.PayBill;
import cn.itsource.pay.mapper.PayBillMapper;
import cn.itsource.pay.service.IPayBillService;
import cn.itsource.product.domain.Product;
import cn.itsource.product.mapper.ProductMapper;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.User;
import cn.itsource.user.mapper.UserMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductOrderServiceImpl extends BaseServiceImpl<ProductOrder> implements IProductOrderService {

    @Autowired
    private UserAddressMapper userAddressMapper; //用户地址

    @Autowired
    private OrderAddressMapper orderAddressMapper;//订单地址
    @Autowired
    private ProductOrderMapper productOrderMapper;//服务订单
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductOrderDetailMapper productOrderDetailMapper;

    //注入支付单mapper
    @Autowired
    private PayBillMapper payBillMapper;

    //paybill实现
    @Autowired
    private IPayBillService payBillService;


    /**
     *
     * @param param
     * addressId: 1, 1 银联支付   2微信支付     3支付宝支付
     * payType: 3, //选中的服务 支付宝
     * products: [{id: 16, num: 1}, {id: 66, num: 2}]
     * @param loginInfo 登录信息
     * @return 支付宝的支付页面
     */
    @Override
    @Transactional
    public String submitOrder(Map<String, Object> param, LoginInfo loginInfo) {
        //获取用户地址id
        long addressId = Long.parseLong(param.get("addressId").toString());
        //根据地址id获取地址对象
        UserAddress userAddress = userAddressMapper.findById(addressId);
        //根据登录信息查询用户
        User user = userMapper.findByLoginInfoId(loginInfo.getId());
        //创建订单地址
        OrderAddress orderAddress = createOrderAddress(userAddress,user);
        //保存定单地址
        orderAddressMapper.add(orderAddress);

        //创建服务订单
        ProductOrder productOrder=createProductOrder(orderAddress,user);
        //保存服务订单（价格是服务详情表）
        productOrderMapper.add(productOrder);
        //h获取所有服务详情
        List<Map<String,Integer>> productOrderDetails=(List<Map<String,Integer>>) param.get("products");
        //创建服务详情
        List<ProductOrderDetail> productOrderDetail = createProductOrderDetail(productOrderDetails, productOrder);
        productOrderDetailMapper.batchSave(productOrderDetail);

        //更新服务订单
        productOrderMapper.update(productOrder);

        Integer payType = (Integer) param.get("payType");
        //创建支付信息
        PayBill payBill = createPayBill(productOrder,payType);
        //保存支付信息
        payBillMapper.add(payBill);
        switch (payType){
            case 1:
                //银联支付
                break;
            case 2:
                //微信支付
                break;
            case 3:
                //支付宝
               return payBillService.toAlipay(productOrder);

        }
        return  null;

    }

    /**
     * 创建服务订单的支付信息
     * @return
     */
    private PayBill createPayBill(ProductOrder productOrder,Integer payType) {
        PayBill payBill = new PayBill();
        //摘要
        payBill.setDigest(productOrder.getDigest());
        //金额
        payBill.setMoney(productOrder.getPrice());
        //最后支付时间
        payBill.setLastPayTime(productOrder.getLastPayTime());
        //支付方式
        payBill.setPayChannel(payType);
        //业务类型
        payBill.setBusinessType(PayConstants.BUSINESSTYPE_PRODUCT);
        //业务键
        payBill.setBusinessKey(productOrder.getId());
        //用户
        payBill.setUser(productOrder.getUser());
        //商家
        payBill.setShop(productOrder.getShop());
        //订单编号
        payBill.setOrderSn(productOrder.getOrderSn());
        return payBill;
    }

    /**
     * 创建服务详情 多个服务
     * @param productOrderDetails
     */
    private List<ProductOrderDetail> createProductOrderDetail(List<Map<String, Integer>> productOrderDetails,ProductOrder productOrder) {
        //一个list存储新的明细
        List<ProductOrderDetail> list = new ArrayList<>();
        //定义一个变量专门存储算出来的金额
        BigDecimal totalAmount=new BigDecimal("0");
        //定义一个摘要
        StringBuilder orderdDigest=new StringBuilder("[服务订单]");
        //遍历
        for (Map<String,Integer> map:productOrderDetails) {
            ProductOrderDetail productOrderDetail = new ProductOrderDetail();
            //连接的订单
            productOrderDetail.setOrder(productOrder);
            //服务数量
            productOrderDetail.setSalecount(map.get("num"));
            //根据id查询服务
            Product product = productMapper.findById(map.get("id").longValue());
            //服务的id
            productOrderDetail.setProduct(product);
            list.add(productOrderDetail);

            //计算累加金额
            totalAmount=totalAmount.add(
                    product.getSaleprice().multiply(new BigDecimal(map.get("num").toString()))
            );

            //拼接摘要
            orderdDigest=orderdDigest.append("购买"+map.get("num")+"次"+product.getName()+"精品洗澡折扣套餐服务！");
        }
        //设置总金额
        productOrder.setPrice(totalAmount);
        //设置摘要
        productOrder.setDigest(orderdDigest.toString());
        return list;
    }

    /**
     * 创建服务订单
     * @return
     */
    private ProductOrder createProductOrder(OrderAddress orderAddress,User user) {
        ProductOrder productOrder = new ProductOrder();
        //摘要
        //productOrder.setDigest("[服务订单]购买1次精品洗澡折扣套餐服务！");
        //订单编号
        productOrder.setOrderSn(orderAddress.getOrderSn());
        //最后支付时间 15分钟
        productOrder.setLastPayTime(DateUtils.addMinutes(new Date(),15 ));
        //用户
        productOrder.setUser(user);
        //店铺
        //获取附近的店铺
        Shop nearestShop = DistanceUtil.getNearestShop(DistanceUtil.getPoint(orderAddress.getAddress()), shopMapper.findAll());
        productOrder.setShop(nearestShop);
        return productOrder;
    }

    /**
     * 创建订单地址
     * @return
     */
    private OrderAddress createOrderAddress(UserAddress userAddress,User user) {
        OrderAddress orderAddress = new OrderAddress();
        //将用户的地址拷贝到订单地址
        BeanUtils.copyProperties(userAddress, orderAddress);
        //设置订单编号
        orderAddress.setOrderSn(CodeGenerateUtils.generateOrderSn(user.getId()));
        return orderAddress;
    }
}
