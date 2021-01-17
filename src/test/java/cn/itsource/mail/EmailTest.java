package cn.itsource.mail;

import cn.itsource.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

public class EmailTest extends BaseTest {
    @Autowired
    private JavaMailSender javaMailSender;
//    @Value("${spring.mail.username}")
//    private String username; //获取用户名

    @Test
    public void  send(){
        //简单邮件
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("xing_zx@outlook.com");
        //邮件主题
        mailMessage.setSubject("新型冠状病毒防护指南");
        //邮件内容
        mailMessage.setText("好好在家待着.....");
        //收件人
        mailMessage.setTo("1107548308@qq.com");
        //发送
        javaMailSender.send(mailMessage);

    }

    @Test
    public void testSpring() throws MessagingException {
        //复杂邮件
        MimeMessage Message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(Message,true,"utf-8");
        //设置发送人
        mimeMessageHelper.setFrom("xing_zx@outlook.com");
        //邮件主题
        mimeMessageHelper.setSubject("新型冠状病毒防护指南");
        //邮件内容
        mimeMessageHelper.setText("<h1>由于你很吊，所以被牛逼大学所录取！！！" +
                "</h1><img src='http://115.159.217.249:8888/group1/M00/00/5A/rBEAA2ABS1yATenRAADQT3S8ScQ695.jpg' title='大美女'/>",true);
        //收件人
        mimeMessageHelper.setTo("1107548308@qq.com");

        //附件
        mimeMessageHelper.addAttachment("5.png", new File("C:\\Users\\xing\\Pictures\\Camera Roll\\5.png"));
        //发送
        javaMailSender.send(Message);
    }

}
