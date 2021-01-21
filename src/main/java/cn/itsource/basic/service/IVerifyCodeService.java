package cn.itsource.basic.service;

import cn.itsource.basic.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface IVerifyCodeService {
    /**
     * 发送短验证码
     * @param phone
     */
    void sendVerifyCodeController(String phone) throws CustomException;


    void sendBinderVerifyCodeController(String phone) throws CustomException;
}
