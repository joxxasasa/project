package com.iktpreobuka.project.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.repositories.OfferRepository;

@Service
public class OfferDAOImpl implements OfferDAO{
//	• 2.1 u servisu zaduženom za rad sa ponudama, napisati 
//	metodu koja za prosleđen ID ponude, vrši izmenu broja 
//	kupljenih/dostupnih ponuda
	
	@Autowired
	private OfferRepository offerRepository;
	
	public OfferEntity changeNoOffers(@PathVariable Integer id, Boolean paymentMade, Boolean paymentCanceled) {
		if(offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			if(paymentMade == true) {
				offer.setAvailableOffers(offer.getAvailableOffers() -1);
				offer.setBoughtOffers(offer.getBoughtOffers() +1);
			}
			if(paymentCanceled == true) {
				offer.setAvailableOffers(offer.getAvailableOffers() +1);
				offer.setBoughtOffers(offer.getBoughtOffers() -1);
			}
			offerRepository.save(offer);
			return offer;
			
		}
		return null;
	}
	
//	napisati metodu u servisu zaduženom za rad sa ponudama koja 
//	proverava da li postoje ponude za datu kategoriju (kategoriju koja 
//	želi da se obriše)
	
	public boolean ifCategoryHasNonExpOffers(Integer categoryId) {
		List<OfferEntity> offers = offerRepository.findAllByCategoryId(categoryId);
		LocalDate date = LocalDate.now();
		for(OfferEntity offer : offers) {
			if(offer.getOfferExpires().isAfter(date)) {
		return true;
			}
	}
		return false;
	}	
	
//	public OfferEntity getJson(String offer, List<MultipartFile> file) {
//		OfferEntity offerJson = new OfferEntity();
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			offerJson = objectMapper.readValue(offer, OfferEntity.class);
//		} catch (IOException err) {
//			System.out.printf("Error", err.toString());
//		}
//		return offerJson;
//	}
	

	private static String UPLOAD_FOLDER = "C:\\SpringTemp\\";
	
	public OfferEntity offerUploadImage(MultipartFile file) throws IOException {
		
			if (file.getOriginalFilename().toLowerCase().endsWith(".jpg")) {
				try {
					byte[] bytes = file.getBytes();
					Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
					Files.write(path, bytes);
					} catch (IOException e) {
					throw e;
					}
			}
		
		return null;
	}
}
