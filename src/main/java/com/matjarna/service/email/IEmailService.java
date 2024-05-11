package com.matjarna.service.email;

public interface IEmailService {

	void sendEmail(String to, String subject, String body);

}
