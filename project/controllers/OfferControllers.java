package com.iktpreobuka.project.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.project.entities.EUofferStatus;
import com.iktpreobuka.project.entities.EUuserRole;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.services.BillDAO;
import com.iktpreobuka.project.services.OfferDAO;

@RestController
@RequestMapping("/project")
public class OfferControllers {
	
	@Autowired
	public OfferRepository offerRepository;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	public OfferDAO offerDAO;
	
	@Autowired
	public BillDAO billDAO;
	
//	• 3.2 u paketu com.iktpreobuka.project.controllers napraviti 
//	klasu OfferController sa metodom get DB() koja vraća 
//	listu svih ponuda
	
//	List<OfferEntity> offers = new ArrayList<OfferEntity>();
//	public List<OfferEntity> getDB() {
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		cal.add(Calendar.DATE, 5);
//
//		if(offers.size() == 0) {
//			offers.add(new OfferEntity(1,"2 tickets for Killers concert", "Enjoy!!!", 
//					new Date(), cal.getTime(), 100000.00, 6500.00, " ", 10, 0, EUofferStatus.WAIT_FOR_APPROVING ));
//			offers.add(new OfferEntity(2, "VIVAX 24LE76T2", "Don't miss this fantastic offer!", new 
//					Date(),cal.getTime(), 200000.00, 16500.00, " ", 5, 0, EUofferStatus.WAIT_FOR_APPROVING));
//			offers.add(new OfferEntity(3, "Dinner for two in Aqua Doria", "Excellent offer", new 
//					Date(), cal.getTime(), 6000.00, 3500.00, " ", 4, 0, EUofferStatus.WAIT_FOR_APPROVING));
//		}
//		return offers;
//	}
	
//	• 3.3 kreirati REST endpoint koja vraća listu svih ponuda
//	• putanja /project/offers
	
//	@GetMapping("/offers")
//	public List<OfferEntity> getOffers() {
//		return getDB();
//	}
	
	@GetMapping("/offers")
	public List<OfferEntity> getOffers() {
		return (List<OfferEntity>) offerRepository.findAll();
	}
	
//	• 3.4 kreirati REST endpoint koji omogućava dodavanje 
//	nove ponude
//	• putanja /project/offers
//	• metoda treba da vrati dodatu ponudu
	
//	@PostMapping("/offers")
//	public OfferEntity addOffer(@RequestBody OfferEntity newOffer) {
//		List<OfferEntity> offers = getDB();
//		newOffer.setId((new Random()).nextInt());
//		offers.add(newOffer);
//		return newOffer;
//	}

//	• 2.3 omogućiti dodavanje kategorije i korisnika koji je 
//	kreirao ponudu
//	• izmeniti prethodnu putanju za dodavanje ponude
//	• putanja /project/offers/{categoryId}/seller/{sellerId} 
//	• NAPOMENA: samo korisnik sa ulogom ROLE_SELLER ima pravo
//	da bude postavljen kao onaj ko je kreirao/napravio ponudu (u 
//	suprotnom ne dozvoliti kreiranje ponude); Kao datum kreiranja
//	ponude postaviti trenutni datum i ponuda ističe za 10 dana od dana 
//	kreiranja
	
	@PostMapping("/offers/{categoryId}/seller/{sellerId}")
	public OfferEntity addOffer(/*@RequestParam Integer availableOffers, @RequestParam Integer boughtOffers, @RequestParam String offerName, 
							@RequestParam String offerDesc, @RequestParam Date offerExpires, @RequestParam Date offerCreated, 
							@RequestParam String imagePath, @RequestParam double regularPrice, @RequestParam double actionPrice, 
							@RequestParam EUofferStatus offerStatus, */@PathVariable Integer categoryId, @PathVariable Integer sellerId,
							@RequestBody OfferEntity offer) {
		if(categoryRepository.existsById(categoryId)) {
			if(userRepository.existsById(sellerId)) {
				UserEntity user = userRepository.findById(sellerId).get();
				if(user.getUserRole().equals(EUuserRole.ROLE_SELLER)) {
					offer.setUser(user);
					offer.setCategory(categoryRepository.findById(categoryId).get());
					offer.setOfferCreated(LocalDate.now());
					offer.setOfferExpires(LocalDate.now().plusDays(10));
					offerRepository.save(offer);
					return offer;
					
				}
			}
		}
//		OfferEntity offer = new OfferEntity();
//		CategoryEntity category = categoryRepository.findById(categoryId).get();
//		UserEntity user = userRepository.findById(sellerId).get();
//		Date date = new Date(System.currentTimeMillis());
//		Date dateAfter10Days = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10));
//		offer.setAvailableOffers(availableOffers);
//		offer.setBoughtOffers(boughtOffers);
//		offer.setOfferName(offerName);
//		offer.setOfferDesc(offerDesc);
//		offer.setOfferExpires(dateAfter10Days);
//		offer.setOfferCreated(date);
//		offer.setImagePath(imagePath);
//		offer.setRegularPrice(regularPrice);
//		offer.setActionPrice(actionPrice);
//		offer.setOfferStatus(offerStatus);
//		offer.setCategory(category);
//		user.getUserRole();
//		if(EUuserRole.ROLE_SELLER != null) {
//		offer.setUser(user);
//		} else {
//			return null;
//		}
//		offerRepository.save(offer);
//		return offer;
		return null;
	}
	
//	• 3.5 kreirati REST endpoint koji omogućava izmenu 
//	postojeće ponude
//	• putanja /project/offers/{id}
//	• ukoliko je prosleđen ID koji ne pripada nijednoj ponudi treba da 
//	vrati null, a u suprotnom vraća podatke ponude sa izmenjenim 
//	vrednostima
//	• NAPOMENA: u okviru ove metode ne menjati vrednost atributa
//	offer status
	
//	@PutMapping("/offers/{id}")
//	public OfferEntity changeOffer(@PathVariable Integer id, @RequestBody OfferEntity changedOffer) {
//		for(OfferEntity offer : getDB()) {
//			if(offer.getId().equals(id)) {
//				if(offer.getOfferName() != null)
//					offer.setOfferName(changedOffer.getOfferName());
//				if(offer.getOfferDesc() != null)
//					offer.setOfferDesc(changedOffer.getOfferDesc());
//				if(offer.getOfferCreated() !=null)
//					offer.setOfferCreated(changedOffer.getOfferCreated());
//				if(offer.getOfferExpires() != null)
//					offer.setOfferExpires(changedOffer.getOfferExpires());
//				if(offer.getRegularPrice() != 0)
//					offer.setRegularPrice(changedOffer.getRegularPrice());
//				if(offer.getActionPrice() != 0)
//					offer.setActionPrice(changedOffer.getActionPrice());
//				if(offer.getImagePath() != null)
//					offer.setImagePath(changedOffer.getImagePath());
//				if(offer.getAvailableOffers() != null)
//					offer.setAvailableOffers(changedOffer.getAvailableOffers());
//				if(offer.getBoughtOffers() != null)
//					offer.setBoughtOffers(changedOffer.getBoughtOffers());
//				return offer;
//			}
//		}
//		return null;
//	}
	
//	• 2.4 omogućiti izmenu kategorije ponude
//	• izmeniti prethodnu putanju za izmenu ponude
//	• putanja /project/offers/{id}/category/{categoryId} 
	
	@PutMapping("/offers/{id}")
	public OfferEntity changeOffer(@PathVariable Integer id, @RequestBody OfferEntity changedOffer) {
		if(offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			if(offer.getOfferName() != null)
				offer.setOfferName(changedOffer.getOfferName());
			if(offer.getOfferDesc() != null)
				offer.setOfferDesc(changedOffer.getOfferDesc());
			if(offer.getOfferCreated() !=null)
				offer.setOfferCreated(changedOffer.getOfferCreated());
			if(offer.getOfferExpires() != null)
				offer.setOfferExpires(changedOffer.getOfferExpires());
			if(offer.getRegularPrice() != 0)
				offer.setRegularPrice(changedOffer.getRegularPrice());
			if(offer.getActionPrice() != 0)
				offer.setActionPrice(changedOffer.getActionPrice());
			if(offer.getImagePath() != null)
				offer.setImagePath(changedOffer.getImagePath());
			if(offer.getAvailableOffers() != null)
				offer.setAvailableOffers(changedOffer.getAvailableOffers());
			if(offer.getBoughtOffers() != null)
				offer.setBoughtOffers(changedOffer.getBoughtOffers());
			offerRepository.save(offer);
			return offer;
		}
		return null;
	}
	
	@PutMapping("/offers/{id}/category/{categoryId}")
	public OfferEntity changeOfferCategory(@PathVariable Integer id, @PathVariable Integer categoryId) {
		if(offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			if(categoryRepository.existsById(categoryId)) {
				offer.setCategory(categoryRepository.findById(categoryId).get());
				offerRepository.save(offer);
				return offer;
			}
			
		}
		return null;
	}
	
//	• 3.6 kreirati REST endpoint koji omogućava brisanje 
//	postojeće ponude
//	• putanja /project/offers/{id}
//	• ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda 
//	treba da vrati null, a u suprotnom vraća podatke o ponudi koja je 
//	obrisana
	
//	@DeleteMapping("/offers/{id}")
//	public OfferEntity deleteOffer(@PathVariable Integer id) {
//		List<OfferEntity> offers = getDB();
//		Iterator<OfferEntity> it = offers.iterator();
//		while(it.hasNext()) {
//			OfferEntity offer = it.next();
//			if(offer.getId().equals(id)) {
//				it.remove();
//				return offer;
//			}
//		}
//		return null;
//	}
	
	@DeleteMapping("/offers/{id}")
	public OfferEntity delOffer(@PathVariable Integer id) {
		if(offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offerRepository.delete(offer);
			return offer;
		}
		return null;
	}
	
//	• 3.7 kreirati REST endpoint koji vraća ponudu po vrednosti 
//	prosleđenog ID-a
//	• putanja /project/offers/{id}
//	• u slučaju da ne postoji ponuda sa traženom vrednošću ID-a vratiti 
//	null
	
//	@GetMapping("/offers/{id}")
//	public OfferEntity getById(@PathVariable Integer id) {
//		List<OfferEntity> offers = getDB();
//		for(OfferEntity offer : offers) {
//			if(offer.getId().equals(id)) {
//				return offer;
//			}
//		}
//		return null;
//	}
	
	@GetMapping("/offers/{id}")
	public OfferEntity getById(@PathVariable Integer id) {
		return offerRepository.findById(id).get();
	}
	
//	• 3.8 kreirati REST endpoint koji omogućava promenu
//	vrednosti atributa offer status postojeće ponude
//	• putanja /project/offers/changeOffer/{id}/status/{status}
//	• ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda 
//	treba da vrati null, a u suprotnom vraća podatke o ponudi koja je 
//	obrisana
	
//	@PutMapping("/offers/changeOffer/{id}/status/{status}")
//	public OfferEntity changeOfferStatus(@PathVariable Integer id, @PathVariable EUofferStatus status) {
//		for(OfferEntity offer : getDB()) {
//			if(offer.getId().equals(id)) {
//				offer.setOfferStatus(status);
//				return offer;
//			}
//		}
//		return null;
//	}
	
	@PutMapping("/offers/changeOffer/{id}/status/{newStatus}")
	public OfferEntity changeOfferStatus(@PathVariable Integer id, @PathVariable EUofferStatus newStatus) {
		if(offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offer.setOfferStatus(newStatus);
			offerRepository.save(offer);
			if(offer.getOfferStatus() == EUofferStatus.DECLINED) {
				billDAO.cancelBills(id);
			}
			return offer;
		}
		return null;
	}
	
//	• 3.9 kreirati REST endpoint koji omogućava pronalazak svih
//	ponuda čija se akcijska cena nalazi u odgovarajućem
//	rasponu
//	• putanja /project/offers/findByPrice/{lowerPrice}/and/{upperPrice}
	
//	@GetMapping("/offers/findByPrice/{lowerPrice}/and/{upperPrice}")
//	public List<OfferEntity> getByPrice(@PathVariable double lowerPrice, @PathVariable double upperPrice) {
//		List<OfferEntity> offers = getDB();
//		List<OfferEntity> retList = new ArrayList<OfferEntity>();
//		for(OfferEntity offer : offers) {
//			if(offer.getRegularPrice() > lowerPrice && offer.getRegularPrice() < upperPrice) {
//				retList.add(offer);
//			}
//		}
//		return retList;
//	}
	
	@GetMapping("/offers/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> getByPrice(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		return offerRepository.findByRegularPriceBetween(lowerPrice, upperPrice);
	}
	
	@GetMapping("/offers/findAllByCategory/{categoryId}")
	public List<OfferEntity> getAllByCategory(@PathVariable Integer categoryId) {
		return offerRepository.findAllByCategoryId(categoryId);
	}
	
//	3.2 kreirati REST endpoint koji omogućava upload slike 
//	za kreiranu ponudu
//	• putanja /project/offers/uploadImage/{id}
//	• metoda treba da vrati izmenjenu ponudu, a ukoliko je prosleđen ID 
//	nepostojeće ponude vratiti null
	
	
	@PutMapping(value = "/offers/uploadImage/{id}")
	public OfferEntity uploadImage(@PathVariable Integer id, 
									@RequestParam("file") MultipartFile file) throws IOException {
		if(offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offerDAO.offerUploadImage(file);
			offer.setImagePath(file.getOriginalFilename());
			
		return offerRepository.save(offer);
		}
		return null;
	}
}