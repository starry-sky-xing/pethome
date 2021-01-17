package cn.itsource.user.service.impl;

import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.user.domain.User;
import cn.itsource.user.mapper.UserMapper;
import cn.itsource.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void checkPhone(String phone) throws CustomException {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)){
            throw  new CustomException("手机号不能为空！");
        }
        //根据手机号查找用户
        User user=userMapper.findByPhone(phone);
        if(user!=null){
            //如果由用户 则手机已被注册
            throw new CustomException("手机号已被注册！");
        }
    }
}
