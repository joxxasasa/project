package com.iktpreobuka.project.services;

import com.iktpreobuka.project.models.EmailObject;

public interface EmailService {
	
	
	public void sendVoucher(EmailObject emailObject) throws Exception;

}
