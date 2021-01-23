package cn.itsource.user.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.domain.dto.LoginInfoDto;
import cn.itsource.user.service.ILoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginInfoController {
    @Autowired
    private ILoginInfoService iLoginInfoService;

    @PostMapping("/login")
    public JsonResult userRegister(@RequestBody LoginInfoDto loginInfoDto){
        try {
          return  iLoginInfoService.login(loginInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
}
