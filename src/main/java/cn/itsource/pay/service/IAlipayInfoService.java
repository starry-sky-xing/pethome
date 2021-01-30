package cn.itsource.pay.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.pay.domain.AlipayInfo;

public interface IAlipayInfoService  extends IBaseService<AlipayInfo>{
    AlipayInfo loadByAppid(String app_id);
}
