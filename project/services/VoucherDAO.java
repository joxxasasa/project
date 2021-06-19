package com.iktpreobuka.project.services;

import java.time.LocalDate;

import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.models.EmailObject;

public interface VoucherDAO {
	
	public VoucherEntity createAndSendVoucher(LocalDate expirationDate, Boolean isUsed, Integer billId, EmailObject emailObject);

}
