package cn.itsource.user.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.itsource.basic.constant.PetHomeConstant;
import cn.itsource.basic.constant.VarifyCodeConstant;
import cn.itsource.basic.exception.CustomException;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.utiles.JsonResult;
import cn.itsource.basic.utiles.MD5Utils;
import cn.itsource.basic.utiles.StrUtils;
import cn.itsource.user.domain.LoginInfo;
import cn.itsource.user.domain.User;
import cn.itsource.user.domain.dto.LoginInfoDto;
import cn.itsource.user.mapper.LoginInfoMapper;
import cn.itsource.user.mapper.UserMapper;
import cn.itsource.user.service.ILoginInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginInfoServiceImpl extends BaseServiceImpl<LoginInfo> implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     *用户注册
     * @param loginInfoDto 临时对象
     */
    @Override
    public void Register(LoginInfoDto loginInfoDto) throws CustomException {
        //校验
        CheckData(loginInfoDto);
        //存注册信息
        LoginInfo loginInfo=CreatLoginInfo(loginInfoDto);
        loginInfoMapper.add(loginInfo);
        //存用户信息
        User user=CreatUser(loginInfo);
        userMapper.add(user);
    }

    /**
     * 这个是用户登录
     * @param loginInfoDto
     * @return map 携带token
     * @throws CustomException
     */
    @Override
    public JsonResult login(LoginInfoDto loginInfoDto) throws CustomException {
        //登录检验
        CheckLoginData(loginInfoDto);
        //根据用户名/电话/邮箱 查询数据库
       LoginInfo loginInfo = loginInfoMapper.findByUsernameAndType(loginInfoDto.getUsername(),loginInfoDto.getType());
        //System.out.println("============================"+loginInfo.getId());
       //如果查询为空 用户不存在
        if(loginInfo==null){
            throw new CustomException("用户不存在");
        }
        //比较密码是否正确 加密后和数据的对比
        if(!MD5Utils.encrypByMd5(loginInfoDto.getPassword()+loginInfo.getSalt()).equals(loginInfo.getPassword())){
            //bu不正确
            throw new CustomException("密码错误");
        }
        //将登录的用户明码存入redis  随机一个token 用于解决会话跟踪  有效期是30分钟，  key：token   value：登录用户对象
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token,loginInfo,30, TimeUnit.MINUTES);
        //返回
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return JsonResult.ResultObj(map);
    }

    /**
     * 登录校验
     * @param loginInfoDto
     */
    private void CheckLoginData(LoginInfoDto loginInfoDto) throws CustomException {
        //用户名不为空
        if(StringUtils.isEmpty(loginInfoDto.getUsername())){
            throw new CustomException("用户名不能为空");
        }
        //密码不为空
        if(StringUtils.isEmpty(loginInfoDto.getPassword())){
            throw new CustomException("用户密码不能为空");
        }
        //类型不为空
        if(StringUtils.isEmpty(loginInfoDto.getType())){
            throw new CustomException("请完善登录信息");
        }
        //
    }

    /**
     * 创建用户信息对象
     * @param loginInfo
     * @return user
     */
    private User CreatUser(LoginInfo loginInfo) {
        //创建一个user
        User user = new User();
        //拷贝属性
        BeanUtils.copyProperties(loginInfo,user);
        //0 禁用 1 启用
        user.setState(PetHomeConstant.OK);
        //登录信息
        user.setLoginInfo(loginInfo);
        return user;
    }

    /**
     * 创建注册信息对象
     * @param loginInfoDto 临时登录信息
     * @return loginInfo
     */
    private LoginInfo CreatLoginInfo(LoginInfoDto loginInfoDto) {
        //创建loginInfo对象
        LoginInfo loginInfo = new LoginInfo();
//        //电话
        loginInfo.setPhone(loginInfoDto.getPhone());
//        //盐值
        loginInfo.setSalt(StrUtils.getComplexRandomString(10));//十位随机数
//      //密码 md5加密 加盐salt
        loginInfo.setPassword(MD5Utils.encrypByMd5(loginInfoDto.getPassword()+loginInfo.getSalt()));
//        //0 商家/平台   1 用户
        loginInfo.setType(loginInfoDto.getType());
        return loginInfo;
    }

    /**
     * 用户注册校验
     * @param loginInfoDto
     */
    private void CheckData(LoginInfoDto loginInfoDto) throws CustomException {
        //手机号不为空
        if(StringUtils.isEmpty(loginInfoDto.getPhone())){
            throw new CustomException("手机号不能为空");
        }
        //手机号已存在
        User user = userMapper.findByPhone(loginInfoDto.getPhone());
        if(user!=null){
            throw new CustomException("手机号已注册");
        }
        //验证码不为空
        if(StringUtils.isEmpty(loginInfoDto.getCode())){
            throw new CustomException("验证码不能为空");
        }
        //验证码已过期
        String valueCode=(String)redisTemplate.opsForValue().get(loginInfoDto.getPhone()+":"+ VarifyCodeConstant.USER_RES);
        if(StringUtils.isEmpty(valueCode)){
            throw new CustomException("验证码已过期，请重新获取");
        }
        //验证码错误
        if(!loginInfoDto.getCode().equals(valueCode.split(":")[0])){
            throw new CustomException("验证码错误");
        }
        //密码不为空
        if(StringUtils.isEmpty(loginInfoDto.getPassword())){
            throw new CustomException("密码不能为空");
        }
        //二次密码不能为空
        if(StringUtils.isEmpty(loginInfoDto.getPasswordRepeat())){
            throw new CustomException("二次密码不能为空");
        }
        //两次输入密码不同
        if(!loginInfoDto.getPassword().equals(loginInfoDto.getPasswordRepeat())){
            throw new CustomException("二次输入的密码不同");
        }
    }
}
