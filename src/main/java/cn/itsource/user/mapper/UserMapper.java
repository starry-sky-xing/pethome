package cn.itsource.user.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.user.domain.User;

public interface UserMapper extends BaseMapper<User>{
    /**
     * 根据电话查找用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);
}
