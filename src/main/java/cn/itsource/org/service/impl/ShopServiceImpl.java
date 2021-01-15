package cn.itsource.org.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.EmployeeMapper;
import cn.itsource.org.mapper.ShopMapper;
import cn.itsource.org.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements IShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public void settled(Shop shop) {
        //添加shop 没有admin_id
        shopMapper.add(shop);
        //获取admin
        Employee admin = shop.getAdmin();
        admin.setShop(shop); //给shp_id
        employeeMapper.add(admin); //添加数据库 同时获得主键id

        //更新shop 包括admin_id
        shopMapper.update(shop);
    }
}
