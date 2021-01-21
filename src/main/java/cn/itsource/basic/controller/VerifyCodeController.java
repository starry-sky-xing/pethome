package cn.itsource.basic.controller;

import cn.itsource.basic.service.IVerifyCodeService;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verifycation")
public class VerifyCodeController {
    @Autowired
    private IVerifyCodeService iVerifyCodeService;
    /**
     * 发送注册验证码
     * 验证码需求：
     *      1.后台随机产生4个字符
     *      2.1分钟以内只能发送1次验证码
     *      3.超过1分钟，但在5分钟以内，发送的验证码依然是第一次产生的验证码字符
     *      4.超过了5分钟以后，产生全新的验证码
     * @return
     */

    @PostMapping("/sendMobileCode")
    public JsonResult sendVerifyCodeController(@RequestBody User user){

        try {
            iVerifyCodeService.sendVerifyCodeController(user.getPhone());
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    /**
     * 绑定微信和手机号的验证码
     * @param user
     * @return
     */
    @PostMapping("/sendBinderMobileCode")
    public JsonResult sendBinderVerifyCodeController(@RequestBody User user){

        try {
            iVerifyCodeService.sendBinderVerifyCodeController(user.getPhone());
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }


}
