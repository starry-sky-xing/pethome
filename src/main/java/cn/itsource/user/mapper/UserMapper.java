package cn.itsource.user.mapper;

import cn.itsource.user.domain.User;

public interface UserMapper {
    /**
     * 根据电话查找用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);
}
