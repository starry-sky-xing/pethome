package cn.itsource.pay.mapper;

import cn.itsource.basic.domain.BaseDomain;
import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.pay.domain.AlipayInfo;

public interface AlipayInfoMapper extends BaseMapper<AlipayInfo> {
    //根据商店id查询支付宝信息
    AlipayInfo findByShopId(Long id);

    AlipayInfo findByAppid(String app_id);
}
