package com.admin.study.util;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
   
    public class Email  {
         
        @Autowired
        private JavaMailSender  mailSender;
    	
        public void SendEmail(String email, String subject, String content) throws Exception {
             
//        	SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(email);
//            message.setSubject(subject);
//            message.setText(content);
//            mailSender.send(message);
        	
            MimeMessage msg = mailSender.createMimeMessage();
            try {
                msg.setSubject(subject);//제목
                msg.setText(content);//내용
                msg.addRecipient(RecipientType.TO, new InternetAddress(email));
            }catch(MessagingException e) {
                System.out.println("MessagingException");
                e.printStackTrace();
            }
            try {
            	mailSender.send(msg);
            }catch(MailException e) {
                System.out.println("MailException발생");
                e.printStackTrace();
            }
        }
}
