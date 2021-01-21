package cn.itsource.user.service;

import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.domain.dto.LoginInfoDto;

import java.util.Map;


public interface IWeChatService{

    //回调处理函数
    JsonResult handleCallback(String code);
    //绑定用户
    Map<String, Object> binder(LoginInfoDto loginInfo) throws CustomException;
}
