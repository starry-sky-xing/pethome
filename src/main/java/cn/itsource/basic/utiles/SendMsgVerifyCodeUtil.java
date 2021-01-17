package cn.itsource.basic.utiles;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMsgVerifyCodeUtil {
    private static final String UID = "starrysky";
    private static final String KEY = "d41d8cd98f00b204e980";

    /**
     *
     * @param phone 手机号码
     * @param context 短信内容
     */
    public static void send(String phone,String context) {
        PostMethod post=null;
        try {
            //创建Http客户端
            HttpClient client = new HttpClient();
            //创建一个post方法
            post = new PostMethod("http://utf8.api.smschinese.cn");
            post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
            NameValuePair[] data ={ new NameValuePair("Uid",UID),
                    new NameValuePair("Key", KEY),
                    new NameValuePair("smsMob",phone),
                    new NameValuePair("smsText",context)};
            //设置post
            post.setRequestBody(data);
            //post提交
            client.executeMethod(post);


            //获取响应头信息
            Header[] headers = post.getResponseHeaders();
            //获取状态码
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:"+statusCode);
            //循环打印头信息
            for(Header h : headers)
            {
                System.out.println(h.toString());
            }
            //获取响应体
            String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
            System.out.println(result); //打印返回消息状态

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(post!=null){
                //结束资源
                post.releaseConnection();
            }


        }


    }

}