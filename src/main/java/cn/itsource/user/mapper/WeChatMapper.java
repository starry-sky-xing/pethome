package cn.itsource.user.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.user.domain.Wxuser;
import org.apache.ibatis.annotations.Param;

public interface WeChatMapper extends BaseMapper<Wxuser>{

    //更具openid查询用户
    Wxuser findByOpenid(String openid);
    //绑定微信用户
    void binder(@Param("loginInfo_id") Long loginInfo_id,@Param("openid") String openid);
}
