package cn.itsource.org.mapper;


import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.org.domain.Shop;

public interface ShopMapper extends BaseMapper<Shop>{

    /**
     * 根据登录信息查询店铺
     * @param loginInfoId
     * @return
     */
    Shop findByLoginInfoId(Long loginInfoId);
}
