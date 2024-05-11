package com.matjarna.service.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private String port;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String starttlsEnable;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;

	@Value("${spring.mail.properties.mail.smtp.ssl.protocols}")
	private String sslProtocols;

	@Value("${spring.mail.properties.mail.smtp.ssl.trust}")
	private String sslTrust;

	@Async
	@Override
	public void sendEmail(String to, String subject, String body) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.ssl.protocols", sslProtocols);
		props.put("mail.smtp.ssl.trust", sslTrust);
/*
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(body, "text/html");
			Transport.send(msg);
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
*/
	}

}