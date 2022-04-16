package com.patel.org.sewa.sms;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patel.org.sewa.entity.SMSMessage;
import com.patel.org.sewa.repository.SMSRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SMSService {

	private static final String ACCOUNT_SID = "ACa552b203dccff68ec182f443f7ea5fd3";
	private static final String AUTH_TOKEN = "7b44210e8c4595a70b9d9a72afb2060c";
	private static final String FROM = "18646353849";
	private static final String CALLBACK_URI = "https://sengagement.herokuapp.com/MessageStatus";

	@Autowired
	private SMSRepository smsRepository;

	public void sendSMS(String to, String msg) {
		log.info("----Sending sms via twillio----");
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message
				.creator(new com.twilio.type.PhoneNumber(to), new com.twilio.type.PhoneNumber(FROM), msg)
				.setStatusCallback(URI.create(CALLBACK_URI)).create();

		log.info("----Message : {}", message.toString());

		// Persisting sms msg to the database.
		smsRepository.save(SMSMessage.builder().account_sid(message.getAccountSid()).sid(message.getSid())
				.status(message.getStatus().toString()).to(message.getTo()).from(message.getFrom().toString()).build());

	}

}
