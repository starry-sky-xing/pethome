package cn.itsource.pet.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.utiles.DistanceUtil;
import cn.itsource.basic.utiles.Point;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.ShopMapper;
import cn.itsource.pet.domain.SearchMasterMsg;
import cn.itsource.pet.mapper.SearchMasterMsgMapper;
import cn.itsource.pet.service.ISearchMasterMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchMasterMsgServiceImpl extends BaseServiceImpl<SearchMasterMsg> implements ISearchMasterMsgService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    @Override
    @Transactional
    public void publish(SearchMasterMsg searchMasterMsg) {
        //System.out.println(searchMasterMsg);
        //根据收货地址，把信息分发到最近的门店
        //把地址转为经纬杜
        Point point = DistanceUtil.getPoint(searchMasterMsg.getAddress());
        //根据地址经纬度，获取最近的店铺
        Shop nearestShop = DistanceUtil.getNearestShop(point, shopMapper.findAll());
        searchMasterMsg.setShop(nearestShop);
        //保存寻主信息
        searchMasterMsgMapper.add(searchMasterMsg);
    }
}
