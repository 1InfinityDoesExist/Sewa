package com.patel.org.sewa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.patel.org.sewa.entity.SMSMessage;

@Repository
public interface SMSRepository extends MongoRepository<SMSMessage, String> {

	SMSMessage findSMSMessageBySid(String messageSid);

}
