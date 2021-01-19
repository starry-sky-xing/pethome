package cn.itsource.user.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.user.domain.Wxuser;

public interface WeChatMapper extends BaseMapper<Wxuser>{

    //更具openid查询用户
    Wxuser findByOpenid(String openid);
}
