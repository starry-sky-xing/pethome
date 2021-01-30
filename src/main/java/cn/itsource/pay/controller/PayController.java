package cn.itsource.pay.controller;

import cn.itsource.basic.config.AlipayConfig;
import cn.itsource.pay.domain.AlipayInfo;
import cn.itsource.pay.service.IAlipayInfoService;
import cn.itsource.pay.service.IPayBillService;
import cn.itsource.pay.service.impl.PayBillServiceImpl;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IAlipayInfoService alipayInfoService;

    @Autowired
    private IPayBillService payBillService;
    /**
     * 支付宝同步请求
     * 成功与否
     * @param request
     * @return
     */
    @RequestMapping("/returnUrl")
    public String returnUrl(HttpServletRequest request){

        try {
            //获取支付宝GET过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            //把支付宝传递的参数封装为一个map集合
            Map<String,String[]> requestParams = request.getParameterMap();

            //循环迭代map
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                //获取map的key值
                String name = (String) iter.next();
                //通过key获取对应的值
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }//拼接字符
                params.put(name, valueStr);
            }
            //获取appid
            String app_id = params.get("app_id");
            //获取支付信息对象
            AlipayInfo alipayInfo = alipayInfoService.loadByAppid(app_id);
            //验签(校验数据在传输过程中，是否被破环，如果被破坏，验签失败，如果数据没有被破环，验签成功)
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayInfo.getAlipay_public_key(), AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
            //——请在这里编写您的程序（以下代码仅作参考）——
            if(signVerified) {
                //跳转到成功界面
                return "redirect:http://localhost:8082/success.html";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "redirect:http://localhost:8082/error.html";
    }

    /**
     * 异步请求
     * @return
     */
    @RequestMapping("/notifyUrl")
    public String notifyUrl(HttpServletRequest request){

        try {
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map<String,String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            //获取appid
            String app_id = params.get("app_id");
            //获取支付信息对象
            AlipayInfo alipayInfo = alipayInfoService.loadByAppid(app_id);
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayInfo.getAlipay_public_key(), AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

            //——请在这里编写您的程序（以下代码仅作参考）——

            /* 实际验证过程建议商户务必添加以下校验：
            1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
            */
            if(signVerified) {//验证成功
                //商户订单号
                String out_trade_no = request.getParameter("out_trade_no");
                //支付宝交易号
                String trade_no = request.getParameter("trade_no");
                //交易状态
                String trade_status = request.getParameter("trade_status");
                //交易成功
                if (trade_status.equals("TRADE_SUCCESS")){
                    //修改状态
                    payBillService.handleState(out_trade_no, trade_no);
                    return "success";
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failure";
    }
}
