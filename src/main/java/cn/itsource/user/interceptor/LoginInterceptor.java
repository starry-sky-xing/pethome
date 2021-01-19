package cn.itsource.user.interceptor;

import cn.itsource.user.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor{

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        //获取头信息
        String token = request.getHeader("token");
        //token位空证明没有登录
        if(StringUtils.isEmpty(token)){
            //跳转登录
            writeJson(response); //告诉前端没有登录
            return false;
        }
        //判断token有没有过期
        LoginInfo loginInfo = (LoginInfo) redisTemplate.opsForValue().get(token);
        if(loginInfo==null){ //过期
            //跳转登录
            writeJson(response); ////告诉前端没有登录
            return false;
        }

        //延长登录信息存在时间
        redisTemplate.opsForValue().set(token, loginInfo,30, TimeUnit.MINUTES);
        return true;
    }

    public void writeJson(HttpServletResponse response) throws IOException {

        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":false,\"msg\":\"noLogin\"}");
    }
}
