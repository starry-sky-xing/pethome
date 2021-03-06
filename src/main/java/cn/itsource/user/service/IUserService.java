package cn.itsource.user.service;

import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.service.IBaseService;
import cn.itsource.user.domain.User;

public interface IUserService extends IBaseService<User>{
    //
    void checkPhone(String phone) throws CustomException;
    //更具登录信息 查询用户
    User findByLoginInfoId(Long loginInfoId);
}
