package com.iktpreobuka.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.models.EmailObject;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.services.BillDAO;
import com.iktpreobuka.project.services.BillDAOImpl;
import com.iktpreobuka.project.services.EmailService;
import com.iktpreobuka.project.services.OfferDAO;
import com.iktpreobuka.project.services.VoucherDAO;

@RestController
@RequestMapping(value = "/project")
public class BillControllers {
	
	@Autowired
	public BillRepository billRepository;
	
	@Autowired
	public OfferRepository offerRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	public BillDAO billDAO;
	
	@Autowired
	public OfferDAO offerDAO;
	
	@Autowired
	public VoucherDAO voucherDAO;
	
	@Autowired
	public EmailService emailService;
	
//	• 3.3 u paketu com.iktpreobuka.project.controllers napraviti
//	klasu BillController sa REST endpoint-om koji vraća listu
//	svih računa
//	• putanja /project/bills
	
	@GetMapping("/bills")
	public List<BillEntity> getAll() {
		return (List<BillEntity>) billRepository.findAll();
	}
	
//	• 3.6 kreirati REST endpoint-ove za dodavanje, izmenu i
//	brisanje računa
//	• putanja /project/bills/{offerId}/buyer/{buyerId} (dodavanje)
//	• putanja /project/bills/{id} (izmena i brisanje)
//	• 5.1 proširiti metodu za dodavanje računa tako da se 
//	smanji broj dostupnih ponuda ponude sa računa, odnosno
//	poveća broj kupljenih

	
	@PostMapping("/bills/{offerId}/buyer/{buyerId}") // probaj sa @RequestBody
	public BillEntity addBill(@RequestParam Boolean paymentMade, @RequestParam Boolean paymentCanceled,
			@RequestParam LocalDate billCreated, @PathVariable Integer offerId, @PathVariable Integer buyerId) {
		return billDAO.addBill(paymentMade, paymentCanceled, billCreated, offerId, buyerId);
	}
//	public BillEntity addBill(@RequestParam Boolean paymentMade, @RequestParam Boolean paymentCanceled,
//			@RequestParam LocalDate billCreated, @PathVariable Integer offerId, @PathVariable Integer buyerId) {
//		if (offerRepository.existsById(offerId)) {
//			if (userRepository.existsById(buyerId)) {
//				BillEntity bill = new BillEntity();
//				OfferEntity offer = offerRepository.findById(offerId).get();
//				UserEntity user = userRepository.findById(buyerId).get();
//				bill.setPaymentMade(paymentMade);
//				bill.setPaymentCanceled(paymentCanceled);
//				bill.setBillCreated(billCreated);
//				bill.setOffer(offer);
//				bill.setUser(user);
//				offer.setAvailableOffers(offer.getAvailableOffers() - 1);
//				offer.setBoughtOffers(offer.getBoughtOffers() + 1);
//				offerRepository.save(offer);
//				return billRepository.save(bill);
//			}
//		}
//		return null;
//	}
	
//	• 5.2 proširiti metodu za izmenu računa tako da ukoliko se 
//	račun proglašava otkazanim tada treba povećati broj
//	dostupnih ponuda ponude sa računa, odnosno smanjiti
//	broj kupljenih

	@PutMapping("/bills/{id}")
	public BillEntity changeBill(@PathVariable Integer id, @RequestBody BillEntity changedBill, 
								@RequestParam LocalDate expirationDate, @RequestParam Boolean isUsed, 
								@RequestBody EmailObject emailObject) {
		 billDAO.changeBill(id, changedBill);
		 if(changedBill.getPaymentMade() == true) {
		 voucherDAO.createAndSendVoucher(expirationDate, isUsed, id, emailObject);
		 }
		 return changedBill;
	}
//	public BillEntity changeBill(@PathVariable Integer id, @RequestBody BillEntity changedBill) {
//		BillEntity bill = billRepository.findById(id).get();
//		if(changedBill.getPaymentMade() != null)
//			bill.setPaymentMade(changedBill.getPaymentMade());
//		if(changedBill.getPaymentCanceled() != null) {
//			bill.setPaymentCanceled(changedBill.getPaymentCanceled());
//			if(changedBill.getPaymentCanceled() == true) {
//				bill.getOffer().setAvailableOffers(bill.getOffer().getAvailableOffers() + 1);
//				bill.getOffer().setBoughtOffers(bill.getOffer().getBoughtOffers() - 1);
////				offer.setAvailableOffers(offer.getAvailableOffers() + 1);
////				offer.setBoughtOffers(offer.getBoughtOffers() - 1);
////				offerRepository.save(offer);
//			}
//		}
//		if(changedBill.getBillCreated() != null)
//			bill.setBillCreated(changedBill.getBillCreated());
//		if(changedBill.getOffer() != null)
//			bill.setOffer(changedBill.getOffer());
//		if(changedBill.getUser() != null)
//			bill.setUser(changedBill.getUser());
//		return billRepository.save(bill);
//	}
	
	@DeleteMapping("/bills/{id}")
	public BillEntity delBill(@PathVariable Integer id) {
		if(billRepository.existsById(id)) {
			BillEntity bill = billRepository.findById(id).get();
			billRepository.delete(bill);
			return bill;
		}
		return null;
	}
	
//	• 3.7 kreirati REST endpoint za pronalazak svih računa
//	određenog kupca
//	• putanja /project/bills/findByBuyer/{buyerId}

	@GetMapping("/bills/findByBuyer/{buyerId}")
	public List<BillEntity> getAllByBuyer(@PathVariable Integer buyerId) {
		return  billRepository.findAllByUserId(buyerId);
	}
	
//	• 3.8 kreirati REST endpoint za pronalazak svih računa 
//	određene kategorije
//	• putanja /project/bills/findByCategory/{categoryId}
	
	@GetMapping("/bills/findByCategory/{categoryId}")//Ne radi
	public List<BillEntity> getAllByCategoryId(@PathVariable Integer categoryId) {
		return billRepository.findAllByOfferCategoryId(categoryId);
		
	}
	
	
//	• 3.9 kreirati REST endpoint za pronalazak svih računa koji 
//	su kreirani u odgovarajućem vremenskom periodu
//	• putanja /project/bills/findByDate/{startDate}/and/{endDate}

	@GetMapping("/bills/findByDate/{startDate}/and/{endDate}")
	public List<BillEntity> getAllByDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
		return billDAO.getAllByDate(startDate, endDate);
	}
	
}
