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

import com.iktpreobuka.project.entities.EUuserRole;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.models.EmailObject;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;
import com.iktpreobuka.project.services.VoucherDAO;

@RestController
@RequestMapping("/project")
public class VoucherControllers {
	
	@Autowired
	public VoucherRepository voucherRepository;
	
	@Autowired
	public OfferRepository offerRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public VoucherDAO voucherDAO;
	
//	• 4.3 u paketu com.iktpreobuka.project.controllers napraviti
//	klasu VoucherController sa REST endpoint-om koji vraća
//	listu svih vaučera
//	• putanja /project/vouchers
	
	@GetMapping("/vouchers")
	public List<VoucherEntity> getAll() {
		return (List<VoucherEntity>) voucherRepository.findAll();
	}

//	• 4.6 kreirati REST endpoint-ove za dodavanje, izmenu i
//	brisanje vaučera
//	• putanja /project/vouchers/{offerId}/buyer/{buyerId} 
//	• NAPOMENA: samo korisnik sa ulogom ROLE_CUSTOMER se 
//	može naći kao kupac na vaučeru (u suprotnom ne dozvoliti
//	kreiranje vaučera)
//	• putanja /project/vouchers/{id} (izmena)
//	• putanja /project/vouchers/{id} (brisanje)
	
	@PostMapping("/vouchers/{offerId}/buyer/{buyerId}")
	public VoucherEntity addNewVoucher(@RequestBody VoucherEntity voucher, @PathVariable Integer offerId,
										@PathVariable Integer buyerId) {
		if(offerRepository.existsById(offerId)) {
			if(userRepository.existsById(buyerId)) {
				OfferEntity offer = offerRepository.findById(offerId).get();
				UserEntity user = userRepository.findById(buyerId).get();
				if(user.getUserRole().equals(EUuserRole.ROLE_CUSTOMER)) {
					voucher.setOffer(offer);
					voucher.setUser(user);
					return voucherRepository.save(voucher);
				}
			}
		}
		return null;
	}
	
	@PostMapping("/vouchers/createAndSendVoucher")
	public String createAndSend(@RequestParam LocalDate expirationDate, @RequestParam Boolean isUsed, 
			@RequestParam Integer billId, @RequestBody EmailObject emailObject) {
		voucherDAO.createAndSendVoucher(expirationDate, isUsed, billId, emailObject);
		return "Voucher je kreiran i poslat";
	}
	
	@PutMapping("/vouchers/{id}")
	public VoucherEntity changeVoucher(@PathVariable Integer id, @RequestBody VoucherEntity changedVoucher) {
		VoucherEntity voucher = voucherRepository.findById(id).get();
		if(voucherRepository.existsById(id)) {
			if(changedVoucher.getExpirationDate() != null) {
				voucher.setExpirationDate(changedVoucher.getExpirationDate());
			}
			if(changedVoucher.getIsUsed() != null) {
				voucher.setIsUsed(changedVoucher.getIsUsed());
			}
			if(changedVoucher.getOffer() != null) {
				voucher.setOffer(changedVoucher.getOffer());
			}
			if(changedVoucher.getUser() != null) {
				voucher.setUser(changedVoucher.getUser());
			}
		}
		voucherRepository.save(voucher);
		return voucher;
	}
	
	@DeleteMapping("/vouchers/{id}")
	public VoucherEntity delVoucher(@PathVariable Integer id) {
		if(voucherRepository.existsById(id)) {
			VoucherEntity voucher = voucherRepository.findById(id).get();
			voucherRepository.delete(voucher);
			return voucher;
		}
		return null;
	}
	
//	• 4.7 kreirati REST endpoint za pronalazak svih vaučera
//	određenog kupca
//	• putanja /project/vouchers/findByBuyer/{buyerId}
	 
	@GetMapping("/vouchers/findByBuyer/{buyerId}")
	public List<VoucherEntity> getAllByBuyer(@PathVariable Integer buyerId) {
		return  voucherRepository.findAllByUserId(buyerId);
	}
////	• 4.8 kreirati REST endpoint za pronalazak svih vaučera
////	određene ponude
////	• putanja /project/vouchers/findByOffer/{offerId}
//	
	@GetMapping("/vouchers/findByOffer/{offerId}")
	public List<VoucherEntity> getByOffer(@PathVariable Integer offerId) {
		return voucherRepository.findAllByOfferId(offerId);
	}
////	• 4.9 kreirati REST endpoint za pronalazak svih vaučera
////	koji nisu istekli
////	• putanja /project/vouchers/findNonExpiredVoucher
//	
	@GetMapping("/vouchers/findNonExpiredVoucher")
	public List<VoucherEntity> getAllNonExpiredVouchers() {
		return voucherRepository.findAllByExpirationDateAfter(LocalDate.now());
	}
}
