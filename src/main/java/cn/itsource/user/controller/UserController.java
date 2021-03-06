package cn.itsource.user.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.domain.User;
import cn.itsource.user.domain.dto.LoginInfoDto;
import cn.itsource.user.service.ILoginInfoService;
import cn.itsource.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ILoginInfoService iLoginInfoService;
    @PostMapping("/checkPhone")
    public JsonResult checkPhone(@RequestBody User user){
        try {
            //验证电话
            iUserService.checkPhone(user.getPhone());
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
    //用户注册
    @PostMapping("/register")
    public JsonResult userRegister(@RequestBody LoginInfoDto loginInfoDto){
        try {
            iLoginInfoService.Register(loginInfoDto);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

}
