package cn.itsource.user.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.user.domain.Wxuser;
import org.springframework.stereotype.Service;


public interface IWeChatService{

    //回调处理函数
    void handleCallback(String code);
}
