package cn.itsource.user.service.impl;

import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.basic.constant.VarifyCodeConstant;
import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.utiles.HttpClientUtils;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.user.constant.WeChatConstant;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.User;
import cn.itsource.user.domain.Wxuser;
import cn.itsource.user.domain.dto.LoginInfoDto;
import cn.itsource.user.mapper.LoginInfoMapper;
import cn.itsource.user.mapper.UserMapper;
import cn.itsource.user.mapper.WeChatMapper;
import cn.itsource.user.service.IWeChatService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class WeChatServiceImpl implements IWeChatService {
    @Autowired
    private WeChatMapper weChatMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 连接微信第三方 处理信息
     * 1.注册过的直接登录 第一次的注册后绑定电话 登录
     * @param code
     * @return
     */
    @Override
    public JsonResult handleCallback(String code) {
        //通过code获取微信的access_token
        String tokenUrl= WeChatConstant.TOKEN_URL.replace("APPID",WeChatConstant.APPID )
                .replace("SECRET", WeChatConstant.SECRET)
                .replace("CODE", code);
        //发送get请求，返回json字符串
        String tokenJsonStr = HttpClientUtils.httpGet(tokenUrl);
        //System.out.println(tokenJsonStr);
        //转成json对象
        JSONObject jsonObject = JSONObject.parseObject(tokenJsonStr);
        //获取access_token
        String access_token = jsonObject.getString("access_token");
        //获取 openid 用户凭证
        String openid = jsonObject.getString("openid");
        //获取微信用户信息url
        String userUrl = WeChatConstant.USER_URL.replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);
        //get请求获取用户信息
        String userInfoJsonStr = HttpClientUtils.httpGet(userUrl);
        //转json对象
        jsonObject = JSONObject.parseObject(userInfoJsonStr);
        //System.out.println(jsonObject);

        //通过 openid 唯一标识查询有没有这个用户 连表查询loginInfo
        Wxuser wxuser = weChatMapper.findByOpenid(openid);

        //map 用于封装响应参数
        Map<String, Object> map = new HashMap<>();
        //如若查询的为空 就是第一次扫描登录 保存微信用户 绑定登录信息
        if(wxuser==null){
            //创建新的用户
            wxuser = new Wxuser();
            //微信用户唯一标识
            wxuser.setOpenid(openid);
            //昵称
            wxuser.setNickname(jsonObject.getString("nickname"));
            //性别
            wxuser.setSex(jsonObject.getInteger("sex"));
            //地址
            wxuser.setAddress(jsonObject.getString("country")+" " +
                    jsonObject.getString("province")+" "
                    +jsonObject.getString("city"));
            //头像
            wxuser.setHeadimgurl(jsonObject.getString("headimgurl"));
            //保存wxuser
            weChatMapper.add(wxuser);
            //登录信息 需要跳转绑定页面 像前端返回apenid
            map.put("openid", openid);
            return JsonResult.ResultObj(map);

        }else {
            //获取微信用户的登录信息
            LoginInfo loginInfo = wxuser.getLoginInfo();
            //如果为空 就是没有绑定电话，需要跳转绑定页面
            if(loginInfo==null){
                //System.out.println("没有绑定用户");
                //跳转绑定页面 向前端返回apenid
                map.put("openid", openid);
                return JsonResult.ResultObj(map);
            }else {
                //如果绑定了用户 就存储登录用户
                String token = UUID.randomUUID().toString();
                //存储登录信息 30分钟
                redisTemplate.opsForValue().set(token,loginInfo,30, TimeUnit.MINUTES);
                //返回 token 前端存入localStorage
                map.put("token", token);
                return JsonResult.ResultObj(map);
            }

        }

    }

    /**
     * 绑定用户
     * @param loginInfoDto 绑定的电话用户
     * @return
     */
    @Override
    public Map<String, Object> binder(LoginInfoDto loginInfoDto) throws CustomException {
        //校验
        checkData(loginInfoDto);
        //查询有没有这个用户 没有就注册一个，有就绑定一起
        LoginInfo loginInfo = loginInfoMapper.findByUsernameAndType(loginInfoDto.getPhone(), loginInfoDto.getType());
        if(StringUtils.isEmpty(loginInfo)){
            //没有的话，就要保存一个登录对象信息
            loginInfo = createLoginInfo(loginInfoDto);
            loginInfoMapper.add(loginInfo);
            //还需要保存一个user信息
            User user = createUser(loginInfo);
            userMapper.add(user);
        }

        //绑定微信 然后登录 根据用户唯一标识绑定登录用户信息
        weChatMapper.binder(loginInfo.getId(),loginInfoDto.getOpenid());

        //可以登录了 将用户存入redis
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token,loginInfo );
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    private User createUser(LoginInfo loginInfo) {
        User user = new User();
        BeanUtils.copyProperties(loginInfo, user);
        //0 禁用 1 启用
        user.setState(PetHomeConstant.OK);
        //登录信息
        user.setLoginInfo(loginInfo);
        return user;
    }

    private LoginInfo createLoginInfo(LoginInfoDto loginInfoDto) {
        LoginInfo loginInfo = new LoginInfo();
        //电话
        loginInfo.setPhone(loginInfoDto.getPhone());
        //0 商家/平台   1 用户
        loginInfo.setType(loginInfoDto.getType());
        //同步微信信息
        Wxuser byOpenid = weChatMapper.findByOpenid(loginInfoDto.getOpenid());
        //同步昵称---用户名
        loginInfo.setUsername(byOpenid.getNickname());
        return loginInfo;
    }

    /**
     * 校验
     * @param loginInfoDto
     */
    private void checkData(LoginInfoDto loginInfoDto) throws CustomException {
        //电话不能为空
        if(StringUtils.isEmpty(loginInfoDto.getPhone())){
            throw new CustomException("电话号码不能为空");
        }
        //验证码不能为空
        if(StringUtils.isEmpty(loginInfoDto.getCode())){
            throw new CustomException("电话号码不能为空");
        }
        //验证码过期
        String valueCode =(String)redisTemplate.opsForValue().get(loginInfoDto.getPhone() + ":" + VarifyCodeConstant.WECHAT_BINDER);
        if(StringUtils.isEmpty(valueCode)){
            throw new CustomException("验证码过期请重新获取！");
        }
        //验证码不正确
        if(!loginInfoDto.getCode().equals(valueCode.split(":")[0])){
            throw new CustomException("验证码错误");
        }

        if(loginInfoDto.getType()==null || StringUtils.isEmpty(loginInfoDto.getOpenid())){
            throw new CustomException("请完善基本信息!!");
        }
    }
}
