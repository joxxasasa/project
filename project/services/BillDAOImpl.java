package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.iktpreobuka.project.controllers.BillControllers;
import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.EUofferStatus;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;

@Service
public class BillDAOImpl implements BillDAO{
	
	@Autowired
	private BillControllers billControllers;

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OfferDAO offerDAO;
	
	@Autowired
	private UserRepository userRepository;
	
	public BillEntity addBill(@RequestParam Boolean paymentMade, @RequestParam Boolean paymentCanceled,
			@RequestParam LocalDate billCreated, @PathVariable Integer offerId, @PathVariable Integer buyerId) {
		if (offerRepository.existsById(offerId)) {
			if (userRepository.existsById(buyerId)) {
				BillEntity bill = new BillEntity();
				OfferEntity offer = offerRepository.findById(offerId).get();
				UserEntity user = userRepository.findById(buyerId).get();
				bill.setPaymentMade(paymentMade);
				bill.setPaymentCanceled(paymentCanceled);
				bill.setBillCreated(billCreated);
				bill.setOffer(offer);
				bill.setUser(user);
				offerDAO.changeNoOffers(offer.getId(), bill.getPaymentMade(), bill.getPaymentCanceled());
				offerRepository.save(offer);
				return billRepository.save(bill);
			}
		}
		return null;
	}
	
	@PutMapping("/bills/{id}")
	public BillEntity changeBill(@PathVariable Integer id, @RequestBody BillEntity changedBill) {
		BillEntity bill = billRepository.findById(id).get();
		if(changedBill.getPaymentMade() != null)
			bill.setPaymentMade(changedBill.getPaymentMade());
		if(changedBill.getPaymentCanceled() != null) 
			bill.setPaymentCanceled(changedBill.getPaymentCanceled());
		if(changedBill.getBillCreated() != null)
			bill.setBillCreated(changedBill.getBillCreated());
		if(changedBill.getOffer() != null)
			bill.setOffer(changedBill.getOffer());
		if(changedBill.getUser() != null)
			bill.setUser(changedBill.getUser());
		billRepository.save(bill);
		offerDAO.changeNoOffers(bill.getOffer().getId(), bill.getPaymentMade(), bill.getPaymentCanceled());
		offerRepository.save(bill.getOffer());
		return billRepository.save(bill);
	}
	
	public List<BillEntity> getAllByDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
		return billRepository.findAllByBillCreatedBetween(startDate, endDate);
	}
	
//	napisati metodu u servisu zaduženom za rad sa računima koja 
//	proverava da li postoje aktivni računi za datu kategoriju
	
	public boolean areBillsActiveByCategory(Integer categoryId) {
		List<BillEntity> bills = billRepository.findAllByOfferCategoryId(categoryId);
		for(BillEntity bill : bills) {
			if(!bill.getPaymentMade() && !bill.getPaymentCanceled()) {
				return true;
			}
		}
		return false;
	}
	
//	3.3 ukoliko se ponuda proglasi isteklom potrebno je 
//	otkazati sve račune koji sadrže tu ponudu
//	• u okviru servisa zaduženog za rad sa računima napisati metodu 
//	koja otkazuje sve račune odgovarajuće ponude
//	• pozvati je u okviru metode za promenu statusa ponude u 
//	OfferController-u
	
	public List<BillEntity> cancelBills(Integer offerId) {
		List<BillEntity> bills = billRepository.findAllByOfferId(offerId);
		for(BillEntity bill : bills) {
				bill.setPaymentCanceled(true);
				billRepository.save(bill);
//				return bills;
		}
		return null;
	}
	
	

}
