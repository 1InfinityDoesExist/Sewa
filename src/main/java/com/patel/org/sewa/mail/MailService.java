package com.patel.org.sewa.mail;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {

	@Value("${sendgrid.api.key}")
	private String sendGridApiKey;

	@Value("${sendgrid.mail.from}")
	private String sendGridFrom;

	public void sendMail(String emailTo, String emailSubject, String emailBody) {
		log.info("---Method to send email.-----");
		Email from = new Email(sendGridFrom);
		Email to = new Email(emailTo);
		Content content = new Content("text/plain", emailBody);
		Mail mail = new Mail(from, emailSubject, to, content);

		log.info("-----SendGripApiKey : {}", sendGridApiKey);
		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			log.info("-----Response Status Code : {}", response.getStatusCode());
		} catch (IOException ex) {
		}
	}
}
