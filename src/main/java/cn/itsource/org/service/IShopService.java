package cn.itsource.org.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;

public interface IShopService extends IBaseService<Shop>{
    /**
     * 上架入驻
     * @param shop
     */
    void settled(Shop shop);

    /**
     * 根据登录信息查询商家
     * @param loginInfoId
     * @return
     */
    Shop findByLoginInfoId(Long loginInfoId);

}
