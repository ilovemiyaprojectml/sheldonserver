package com.fisglobal.waho.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	//FOR TESTING WHILE WAITING FOR THE FIS SMTP RELAY SETUP
	public static boolean email(String subject, String body, String recipientp) {
		final String username = "ilovemiyaprojectml@gmail.com";
        final String password = "pacificrimming69";
        final String recipient = recipientp;

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        //prop.put("mail.smtp.ehlo", "false");
        //prop.put("mail.smtp.ssl.enable", "false");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ilovemiyaprojectml@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    //InternetAddress.parse("Emmanuel.Corpuz@fisglobal.com, AllProfits_team@fisglobal.com, Amelia.Grefalda@fisglobal.com, Ruberson.Tria@fisglobal.com")
                    InternetAddress.parse(recipient)
            );
            message.setSubject(subject);
            System.out.println(body);
            message.setText(body);

            Transport.send(message);

            System.out.println("Done");
            
            return true;


        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
		
	}
	
	public static void main(String[] args) {
		email("test", "test", "emmanuelcorpuz@yahoo.com");
	}

	//THIS IS THE OFFICIAL CODE
	/*public static void email(String subject, String body, String recipientp) {
		final String username = "AllProfits_SVNBuildAlert@fisglobal.com";
        final String password = "";
        final String recipient = recipientp;

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smarthost.fisglobal.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.auth", "false");
        prop.put("mail.smtp.ehlo", "false");
        prop.put("mail.smtp.ssl.enable", "false");
        //prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("AllProfits_SVNBuildAlert@fisglobal.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    //InternetAddress.parse("Emmanuel.Corpuz@fisglobal.com, AllProfits_team@fisglobal.com, Amelia.Grefalda@fisglobal.com, Ruberson.Tria@fisglobal.com")
                    InternetAddress.parse(recipient)
            );
            message.setSubject(subject);
            System.out.println(body);
            message.setText(body);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}*/
}
