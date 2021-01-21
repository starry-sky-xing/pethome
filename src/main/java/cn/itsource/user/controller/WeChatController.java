package cn.itsource.user.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.constant.WeChatConstant;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.dto.LoginInfoDto;
import cn.itsource.user.service.IWeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private IWeChatService iWeChatService;
    /**
     * 跳转微信扫码登录
     */
    @RequestMapping("toLogin")
    public String toLogin(){

      String code_url = WeChatConstant.CODE_URL.replace("APPID",WeChatConstant.APPID )
              .replace("REDIRECT_URI",WeChatConstant.REDIRECT_URI );
      //重定向到指定路径
      return "redirect:"+code_url;
    }

    /**
     *
     * 获取回调数据
     * @param code 授权码
     * @return
     */
    @RequestMapping("/callback")
    public String callback(String code){
        return "redirect:http://localhost:8082/callback.html?code="+code;
    }

    /**
     * 处理回调数据，通过code获取token
     * @return 处理结果
     */
    @PostMapping("/handleCallback")
    @ResponseBody
    public JsonResult handleCallback(@RequestBody Map<String,String> param){
        try {
            return iWeChatService.handleCallback(param.get("code"));
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    /**
     * 将微信绑定一个用户
     * @param loginInfoDto 要绑定的用户临时信息
     * @return
     */
    @PostMapping("/binder")
    @ResponseBody
    public JsonResult handleCallback(@RequestBody LoginInfoDto loginInfoDto){
        try {
            Map<String, Object> map = iWeChatService.binder(loginInfoDto);
            return JsonResult.ResultObj(map);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
}
