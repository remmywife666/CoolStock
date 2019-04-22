package com.example.administrator.coolstock.RegisteredP;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailThread extends Thread {

    /**
     * mainAddress收件人地址
     * content邮件内容
     * subject邮件标题
     */
    private String mailAddress;
    private String content;
    private String subject;

    public SendMailThread(String mailAddress,String content,String subject){
        super();
        this.mailAddress=mailAddress;
        this.content=content;
        this.subject=subject;
    }
    @Override
    public void run(){
        super.run();
        sendMail(mailAddress,subject,content);
    }

    public void sendMail(String mailAddress,String subject,String content){
        //配置发送邮件的环境属性
        final Properties props=new Properties();
        //表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.host","smtp.163.com");
        //smtp登陆的账号、密码；需开启smpt登陆
        props.put("mail.user","m15093887346@163.com");
        //访问SMTP服务时需要提供的密码，不是邮箱的登陆密码，是smtp服务的密码
        props.put("mail.password","LxLFS4wAGrl7LpRm");
        //构建授权信息，用于进行SMTP身份验证
        Authenticator authenticator =new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //用户名、密码
                String userName=props.getProperty("mail.user");
                String password=props.getProperty("mail.password");
                return new PasswordAuthentication(userName,password);
            }
        };

        //使用环境属性和授权信息，创建邮件会话
        Session mailSession =Session.getInstance(props,authenticator);
        //创建邮件消息
        MimeMessage message=new MimeMessage(mailSession);
        //设置发件人
        try{
            InternetAddress form=new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            //设置收件人
            InternetAddress to=new InternetAddress(mailAddress);
            message.setRecipient(RecipientType.TO,to);

            //设置邮件标题
            message.setSubject(subject);
            //设置邮件的内容体
            message.setContent(content,"text/html;charset=UTF-8");
            //发送邮件
            Transport.send(message);

        }catch (MessagingException e){
            e.printStackTrace();
        }
    }


}
