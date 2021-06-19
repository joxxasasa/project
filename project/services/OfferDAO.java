package com.iktpreobuka.project.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iktpreobuka.project.entities.OfferEntity;

public interface OfferDAO {

	public OfferEntity changeNoOffers(Integer id, Boolean paymentMade, Boolean paymentCanceled);
	public boolean ifCategoryHasNonExpOffers(Integer categoryId);
	//public OfferEntity getJson(String offer, List<MultipartFile> file);
	//String singleFileUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException;
	public OfferEntity offerUploadImage(MultipartFile file) throws IOException;
}
