package cn.itsource.basic.service.impl;

import cn.itsource.basic.constant.VarifyCodeConstant;
import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.service.IVerifyCodeService;
import cn.itsource.basic.utiles.SendMsgVerifyCodeUtil;
import cn.itsource.basic.utiles.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 发送注册验证码
     * 验证码需求：
     *      1.后台随机产生4个字符
     *      2.1分钟以内只能发送1次验证码
     *      3.超过1分钟，但在5分钟以内，发送的验证码依然是第一次产生的验证码字符
     *      4.超过了5分钟以后，产生全新的验证码
     * @return
     */

    @Override
    public void sendVerifyCodeController(String phone) throws CustomException {
        //产生6位随机数字验证码
        String value = StrUtils.getRandomString(6);
        //从redis取出验证吗 v=value:时间戳
        String valueCode =(String) redisTemplate.opsForValue().get(phone +":"+ VarifyCodeConstant.USER_RES);
        //如果没有超过5分钟 发送上次一样的验证码
        if(!StringUtils.isEmpty(valueCode)){
            //获取存的时候的时间戳
            String currentTime= (String) valueCode.split(":")[1];
            //判断间隔有没有一分钟 一分钟不能多次提交 60s
            if(System.currentTimeMillis()-Long.valueOf(currentTime)<=60*1000){
                throw new CustomException("一分钟内不能发送多次短信，请稍后再试");
            }
            //：分隔 取value
            value=valueCode.split(":")[0];
        }

        //将验证码存入redis
        //k=phone:userres v=value:时间戳  有效期5分钟
        redisTemplate.opsForValue().set(phone +":"+ VarifyCodeConstant.USER_RES,
                value+":"+System.currentTimeMillis(),5, TimeUnit.MINUTES);
        //发送手机验证码
        String context = "尊敬的用户，您的验证码为:" + value + ",请您在5分钟以内完成注册!!";
        //发送短信
        //SendMsgVerifyCodeUtil.send(phone, context);
        System.out.println(context);

    }
}
