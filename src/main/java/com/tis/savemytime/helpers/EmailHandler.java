package com.tis.savemytime.helpers;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.tis.savemytime.models.Status;

public class EmailHandler {
	
	private static final Logger logger = Logger.getLogger(EmailHandler.class);
	
	public static Status sendEmail(String noReplyID, String noReplyIDMSG, String sslEnable, String smtpHost, int smtpPort, String from, String to, String subject,
		      String content, String password) throws AddressException, MessagingException, UnsupportedEncodingException {
			Status status = new Status();
		    java.util.Properties props = new java.util.Properties();
		    props.put("mail.smtp.host", smtpHost);
		    props.put("mail.smtp.port", "" + smtpPort);
		    if("true".equalsIgnoreCase(sslEnable))
		    		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
			
		    Session session = EmailHandler.getAuthSession(smtpHost, smtpPort, password, from);
		    logger.debug("Session has been created successfully");
		    MimeMessage msg = new MimeMessage(session);
		      //set message headers
		      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		      msg.addHeader("format", "flowed");
		      msg.addHeader("Content-Transfer-Encoding", "8bit");

		      msg.setFrom(new InternetAddress(noReplyID, noReplyIDMSG));

		      msg.setReplyTo(InternetAddress.parse(noReplyID, false));

		      msg.setSubject(subject, "UTF-8");

		      msg.setContent(content, "text/html");

		      msg.setSentDate(new Date());

		      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	    	  Transport.send(msg);
	    	  logger.debug("Email has been sent successfully to "+to);
	    	  status.setMessage("Successfully sent an email");
	    	  status.setStatus("Success");
		    return status;
	}
	
	private static Session getAuthSession(String smtpHost, int smtpPort, String password, String fromEmail) {
		
              //create Authenticator object to pass in Session.getInstance argument
		java.util.Properties props = new java.util.Properties();
	    props.put("mail.smtp.host", smtpHost);
	    props.put("mail.smtp.port", "" + smtpPort);
	    props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
	    props.put("mail.smtp.auth", "true"); 
	    
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		return session;
	}
}
