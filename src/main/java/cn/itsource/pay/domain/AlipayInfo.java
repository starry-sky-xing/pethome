package cn.itsource.pay.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

@Data
public class AlipayInfo extends BaseDomain {
    //商家私钥
    private String merchant_private_key;
    //应用appid
    private String appid;
    //支付宝公钥
    private String alipay_public_key;
    //店铺id
    private Long shop_id;
    //店铺名字
    private String shopName;

}
