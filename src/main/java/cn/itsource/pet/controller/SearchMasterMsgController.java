package cn.itsource.pet.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.PageBean;
import cn.itsource.basic.utiles.SystemContext;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.service.IShopService;
import cn.itsource.pet.domain.SearchMasterMsg;
import cn.itsource.pet.query.SearchMasterMsgQuery;
import cn.itsource.pet.service.ISearchMasterMsgService;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.User;
import cn.itsource.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/searchMasterMsg")
public class SearchMasterMsgController {
    @Autowired
    private ISearchMasterMsgService searchMasterMsgService;


    @Autowired
    private IUserService userService;

    @Autowired
    private IShopService shopService;

    //用户提交寻主信息
    @PostMapping("/publish")
    public JsonResult searchMasterMsgPublish(@RequestBody SearchMasterMsg searchMasterMsg, HttpServletRequest request){
        try {
            LoginInfo loginInfo = SystemContext.getLoginInfo(request);
            User user=userService.findByLoginInfoId(loginInfo.getId());
            searchMasterMsg.setUser(user);  //查到当前用户

            searchMasterMsgService.publish(searchMasterMsg);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    /**
     * 查询待处理寻主信息
     * @param query
     * @return
     */
    @PostMapping("/queryPendingMessagePage")
    public PageBean<SearchMasterMsg> queryPendingMessagePage(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request){
        //获取当前登录信息
        LoginInfo loginInfo = SystemContext.getLoginInfo(request);

        //根据登录信息id查询店铺
        Shop shop = shopService.findByLoginInfoId(loginInfo.getId());
        //设置店铺id
        query.setShopId(shop.getId());

        return  searchMasterMsgService.findPage(query);
    }


}
