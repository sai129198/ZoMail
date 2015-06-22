package com.chenzuhuang.zomail.core;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtil {
	private static XMLUtil xmlUtil = XMLUtil.getXMLUtil(MailUtil.class.getClassLoader().getResourceAsStream("zomail.xml"));
	private static String fromEmail = xmlUtil.getParam("email");
	private static String password = xmlUtil.getParam("password");
	private static String auth = xmlUtil.getParam("auth");
	private static String host = xmlUtil.getParam("host");
	
	public static boolean sendEmail(String toEmail, String title, String content, String contentType){
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.host", host);

		Session session = Session.getInstance(properties, new Authenticator(){
			@Override
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipient(RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(title);
			
			//On different system, you may use different encoding.
			//String new_content = new String(content.getBytes("GBK"), "utf-8");
			message.setContent(content, "text/html;charset=utf-8");
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public static void main(String[] args) {
		sendEmail("564923716@qq.com","title", "<p>content</p>","text/html;charset=utf-8");
	}

}
