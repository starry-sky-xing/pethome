package cn.itsource.user.service;

import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.service.IBaseService;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.dto.LoginInfoDto;

public interface ILoginInfoService extends IBaseService<LoginInfo> {
    /**
     * 注册
     * @param loginInfoDto
     */
    void Register(LoginInfoDto loginInfoDto) throws CustomException;

    /**
     *
     * @param loginInfoDto
     * @return
     * @throws CustomException
     */
    JsonResult login(LoginInfoDto loginInfoDto) throws CustomException;
}
