package cn.itsource.pet.controller;

import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.PageBean;
import cn.itsource.basic.utiles.SystemContext;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.service.IShopService;
import cn.itsource.pet.domain.Pet;
import cn.itsource.pet.query.PetQuery;
import cn.itsource.pet.service.IPetService;
import cn.itsource.user.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private IPetService petService;
    @Autowired
    private IShopService shopService;


    /**
     * 宠物上架
     * @param pets
     * @return
     */
    @PostMapping("/onsale")
    public JsonResult onsale(@RequestBody List<Pet> pets){
        try {
            //批量上架产品服务
            petService.onsale(pets);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }
    /**
     * 删除宠物 物理  一般公司不会物理删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Long id){
        try {
            petService.delete(id);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

    //接回宠物 准备上架 寻主
    @PutMapping("/handlePet")
    public JsonResult handlePet(@RequestBody Pet pet){
        try {
            petService.handlePet(pet);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return  JsonResult.me(e.getMessage());
        }
    }

    /**
     * 宠物列表查询
     * @param query
     * @param request
     * @return
     */
    @PostMapping()
    public PageBean<Pet> queryPetPage(@RequestBody PetQuery query, HttpServletRequest request){
        //获取当前登录信息
        LoginInfo loginInfo = SystemContext.getLoginInfo(request);
        //根据登录信息id查询店铺
        Shop shop = shopService.findByLoginInfoId(loginInfo.getId());
        //设置店铺id
        query.setShopId(shop.getId());

        return  petService.findPage(query);
    }

    /**
     * 添加宠物
     * @param pet
     * @param request
     * @return
     */
    @PutMapping()
    public JsonResult petSave(@RequestBody Pet pet,HttpServletRequest request){
        try {
            //获取当前登录信息
            LoginInfo loginInfo = SystemContext.getLoginInfo(request);
            //根据登录信息id查询店铺
            Shop shop = shopService.findByLoginInfoId(loginInfo.getId());
            //设置shop对象
            pet.setShop(shop);
            petService.add(pet);
            return new JsonResult();
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.me(e.getMessage());
        }
    }

//====================门户===============================//
    @PostMapping("/queryHomePage")
    public PageBean<Pet> queryHomePage(@RequestBody PetQuery query) {
        return petService.findPage(query);

    }

    @GetMapping("/{id}")
    public Pet findById(@PathVariable("id") Long id) {
        return petService.findById(id);
    }

}
