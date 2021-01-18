package cn.itsource.org.service.impl;

import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.utiles.MD5Utils;
import cn.itsource.basic.utiles.StrUtils;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.EmployeeMapper;
import cn.itsource.org.mapper.ShopMapper;
import cn.itsource.org.service.IShopService;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.mapper.LoginInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements IShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private  LoginInfoMapper loginInfoMapper;

    @Override
    @Transactional
    public void settled(Shop shop) {
        //添加shop 没有admin_id
        shopMapper.add(shop);
        //获取admin
        Employee admin = shop.getAdmin();
        //生成颜值
        admin.setSalt(StrUtils.getComplexRandomString(10));
        //加密密码
        admin.setPassword(MD5Utils.encrypByMd5(admin.getPassword()+admin.getSalt()));

        //创建登录信息对象
        LoginInfo loginInfo=CreateLoginInfo(admin);
        loginInfoMapper.add(loginInfo); //保存登录对象

        admin.setState(PetHomeConstant.OK);//启用
        admin.setLogininfo(loginInfo);
        admin.setShop(shop); //给shp_id
        employeeMapper.add(admin); //添加数据库 同时获得主键id

        //更新shop 包括admin_id
        shopMapper.update(shop);
    }

    private LoginInfo CreateLoginInfo(Employee admin) {
        LoginInfo loginInfo = new LoginInfo();
        BeanUtils.copyProperties(admin, loginInfo);
        loginInfo.setType(PetHomeConstant.ADMIN);//商家 平台
        return loginInfo;
    }
}
