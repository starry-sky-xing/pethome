package cn.itsource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@MapperScan("cn.itsource.*.mapper")
public class App {

    private  static ConfigurableApplicationContext configurableApplicationContext;
    public static void main(String[] args) {
        configurableApplicationContext = SpringApplication.run(App.class, args);
    }

    public static RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = configurableApplicationContext.getBean("redisTemplate", RedisTemplate.class);
        return redisTemplate;
    }
}
