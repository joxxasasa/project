package com.iktpreobuka.project.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.models.EmailObject;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;

@Service
public class VoucherDAOImpl implements VoucherDAO{
	
	@Autowired
	public BillRepository billRepository;
	
	@Autowired
	public VoucherRepository voucherRepository;
	
	@Autowired
	public EmailService emailService;

//	4.1 omogućiti kreiranje vaučera kada se atribut računa 
//	paymentMade postavi na true
//	• u okviru servisa zaduženog za rad sa vaučerima, napisati metodu 
//	koja vrši kreiranje vaučera na osnovu prosleđenog računa
//	• pozvati je u okviru metode za izmenu računa u BillController-u
	
	public VoucherEntity createAndSendVoucher(LocalDate expirationDate, Boolean isUsed, Integer billId, EmailObject emailObject) {
		
			VoucherEntity voucher = new VoucherEntity();
			BillEntity bill = billRepository.findById(billId).get();
			voucher.setExpirationDate(expirationDate);
			voucher.setIsUsed(isUsed);
			voucher.setOffer(bill.getOffer());
			voucher.setUser(bill.getUser());
			voucherRepository.save(voucher);
			
			try {
				emailService.sendVoucher(emailObject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return voucher;
	}

}
