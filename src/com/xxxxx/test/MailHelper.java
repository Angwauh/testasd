package com.xxxxx.test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailHelper {
    public static boolean sendemail(String host, String from, String pwd, String to,
                                    String subject, String content, List<String> files){
        //1.创建参数配置，用于连接邮件服务器的参数配置
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.host",host);
        properties.setProperty("mail.smtp.auth","true");
        //2.根据配置创建会话对象，用于和邮件服务器交互
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        //3.创建一封邮件
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(from,from,"utf-8"));//发件人
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO,new InternetAddress[]{new InternetAddress(to
                    ,to,"utf-8")});//收件人
            mimeMessage.setSubject(subject,"utf-8");//邮件主题
            if(files.size()>0){
                MimeBodyPart part = new MimeBodyPart();
                part.setContent(content,"text/html;charset=UTF-8");
                //创建容器描述数据关系
                MimeMultipart mp = new MimeMultipart();
                mp.addBodyPart(part);
                for(String file:files){
                    //创建邮件附件
                    MimeBodyPart attach = new MimeBodyPart();
                    DataHandler dataHandler = new DataHandler(new FileDataSource(file));
                    attach.setDataHandler(dataHandler);
                    attach.setFileName(dataHandler.getName());
                    mp.addBodyPart(attach);
                }
                mp.setSubType("mixed");
                mimeMessage.setContent(mp,"text/html;charset=UTF-8");

            }
            else{
                mimeMessage.setContent(content,"text/html;charset=UTF-8");
            }
            //1.设置发送时间
            mimeMessage.setSentDate(new Date());
            //2.保存设置
            mimeMessage.saveChanges();
            //3.根据session获取邮件传输对象
            Transport transport = session.getTransport();
            //4.使用邮箱和授权码连接服务器
            transport.connect(from,pwd);
            //5.发送
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

            transport.close();
            System.out.println("*******邮件发送成功*******");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

}
