package com.patel.org.sewa.controller.webhooks;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.patel.org.sewa.entity.SMSMessage;
import com.patel.org.sewa.repository.SMSRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SMSWebhooks {

	@Autowired
	private SMSRepository smsRepository;

	/**
	 * Twillio MSG CallBack URL
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/MessageStatus", method = RequestMethod.POST)
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("-----SMS webhook url called-----");
		String messageSid = request.getParameter("MessageSid");
		String messageStatus = request.getParameter("MessageStatus");
		log.info("-----MessageSID : {}, messageStatus : {}", messageSid, messageStatus);

		SMSMessage smsMessage = smsRepository.findSMSMessageBySid(messageSid);
		log.info("-----SMSMessage : {}", smsMessage);
		if (!ObjectUtils.isEmpty(smsMessage)) {
			smsMessage.setStatus(messageStatus);
			smsRepository.save(smsMessage);
		}
	}
}
