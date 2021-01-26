package cn.itsource.basic.utiles;

import cn.itsource.App;
import cn.itsource.user.domain.LoginInfo;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;


public class SystemContext {
    public static LoginInfo getLoginInfo(HttpServletRequest request){

        //获取RedisTemplate
        RedisTemplate redisTemplate = App.redisTemplate();

        //获取token
        String token = request.getHeader("token");
        //在redis中获取登录信息
        LoginInfo loginInfo = (LoginInfo) redisTemplate.opsForValue().get(token);
        return loginInfo;
    }
}
