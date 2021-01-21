package cn.itsource.basic.config;

import cn.itsource.user.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PetHomeWebMvcConfigurer implements WebMvcConfigurer{

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")  //登录放行
                .excludePathPatterns("/shop/settlement") //店铺入驻
                .excludePathPatterns("/dfs/**")  //文件放行
                .excludePathPatterns("/verifycation/**") //短信放行
                .excludePathPatterns("/user/**")//user 放行
                .excludePathPatterns("/wechat/**");//微信扫码放行
    }
}
