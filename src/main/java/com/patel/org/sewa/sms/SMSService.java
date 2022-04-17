package com.patel.org.sewa.sms;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.patel.org.sewa.entity.SMSMessage;
import com.patel.org.sewa.repository.SMSRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SMSService {

	@Value("${twillio.sms.account_sid}")
	private String ACCOUNT_SID;

	@Value("${twillio.sms.oauth_token}")
	private String AUTH_TOKEN;

	@Value("${twillio.sms.from}")
	private String FROM;

	@Value("${twillio.sms.callback_uri}")
	private String CALLBACK_URI;

	@Autowired
	private SMSRepository smsRepository;

	public void sendSMS(String to, String msg) {

		log.info(" {} , {}, {}, {}", CALLBACK_URI, FROM, AUTH_TOKEN, ACCOUNT_SID);
		log.info("----Sending sms via twillio----");
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message
				.creator(new com.twilio.type.PhoneNumber(to), new com.twilio.type.PhoneNumber(FROM), msg)
				.setStatusCallback(URI.create(CALLBACK_URI))
				.create();

		log.info("----Message : {}", message.toString());

		// Persisting sms msg to the database.
		smsRepository.save(SMSMessage.builder().account_sid(message.getAccountSid()).sid(message.getSid())
				.status(message.getStatus().toString()).to(message.getTo()).from(message.getFrom().toString()).build());

	}

}
