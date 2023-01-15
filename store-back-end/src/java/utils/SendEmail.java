package com.xzr.glassfishDemo.utils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class SendEmail {

    static final String smtphost = "smtp.qq.com";// 发送邮件的服务商
    static final String from = "xingzhouren@qq.com";// 邮件发送人的邮件地址
    static final String username = "xingzhouren@qq.com";//发件人的邮件帐户
    static final String password = "rwtudeegpmaieahg";//发件人的邮件密码

    public static boolean sendEmail(String email, String code) {

        //定义Properties对象,设置环境信息
        Properties props = System.getProperties();
        //设置邮件服务器的地址
        props.setProperty("mail.smtp.host", smtphost); // 指定的smtp服务器
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");//设置发送邮件使用的协议
        //创建Session对象,session对象表示整个邮件的环境信息
        Session session1 = Session.getInstance(props);
        //设置输出调试信息
//        session1.setDebug(true);
        try {
            //Message的实例对象表示一封电子邮件
            MimeMessage message = new MimeMessage(session1);
            //设置发件人的地址
            message.setFrom(new InternetAddress(from));
            //设置主题
            message.setSubject("HOWAREYOU Verification Email");
            //设置邮件的文本内容
            message.setText("Dear User, \nWelcome to use HOWAREYOU\n" +
                    "\n" +
                    "Your verification code is " + code + "\n" +
                    "The validity period of this verification code is only 1 hour, please enter it as soon as possible, if it is not your operation, please ignore this email, thank you for using \n" +
                    "\n" +
                    "HOWAREYOU Group Press");
            //message.setContent(("Dear Users, Welcome to use"),"text/html;charset=utf-8");

            //设置附件
            //message.setDataHandler(dh);

            //从session的环境中获取发送邮件的对象
            Transport transport=session1.getTransport();
            //连接邮件服务器
            transport.connect(smtphost,25, username, password);
            //设置收件人地址,并发送消息
            transport.sendMessage(message,new Address[]{new InternetAddress(email)});
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getRandomCode(){
        StringBuilder ret = new StringBuilder();
        for(int i=0;i<6;i++){
            Random r = new Random();
            int ran1 = r.nextInt(10);
            ret.append(ran1);
        }
        return ret.toString();
    }
}
