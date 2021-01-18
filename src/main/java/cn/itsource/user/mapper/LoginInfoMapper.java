package cn.itsource.user.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.user.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

public interface LoginInfoMapper extends BaseMapper<LoginInfo> {
    //根据用户名/电话/邮箱  类型 查找登录对象
    LoginInfo findByUsernameAndType(@Param("username") String username,@Param("type") Integer type);
}
