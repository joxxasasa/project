package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.services.BillDAO;
import com.iktpreobuka.project.services.OfferDAO;

@RestController
@RequestMapping(value = "/project")
public class CategoryControllers {
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	@Autowired
	public OfferDAO offerDAO;
	
	@Autowired
	public BillDAO billDAO;
	
//	List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
//	public List<CategoryEntity> getDB() {
//		
//		if(categories.size() == 0) {
//			categories.add(new CategoryEntity(1, "music", "description 1"));
//			categories.add(new CategoryEntity(2, "food", "description 2"));
//			categories.add(new CategoryEntity(3, "entertainment", "description 3"));
//		}
//		return categories;
//	}
	
//	• 2.3 kreirati REST endpoint koji vraća listu svih kategorija
//	• putanja /project/categories
	
//	@GetMapping("/categories")
//	public List<CategoryEntity> getCategories() {
//		return getDB();
//	}
	
	@GetMapping("/categories")
	public List<CategoryEntity> getCategories() {
		return (List<CategoryEntity>) categoryRepository.findAll();
	}
	
//	• 2.4 kreirati REST endpoint koji omogućava dodavanje 
//	nove kategorije
//	• putanja /project/categories
//	• metoda treba da vrati dodatu kategoriju
	
//	@PostMapping("/categories")
//	public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
//		List<CategoryEntity> categories = getDB();
//			newCategory.setId((new Random()).nextInt());
//			categories.add(newCategory);
//			return newCategory;
//	}
	
	@PostMapping("/categories")
	public CategoryEntity addCategory(@RequestBody CategoryEntity newCategory) {
		return categoryRepository.save(newCategory);
	}
	
//	• 2.5 kreirati REST endpoint koji omogućava izmenu 
//	postojeće kategorije
//	• putanja /project/categories/{id}
//	• ukoliko je prosleđen ID koji ne pripada nijednoj kategoriji metoda 
//	treba da vrati null, a u suprotnom vraća podatke kategorije sa 
//	izmenjenim vrednostima

//	@PutMapping("/categories/{id}")
//	public CategoryEntity changeCategory(@PathVariable Integer id, @RequestBody CategoryEntity newCategory) {
//		for(CategoryEntity category : getDB()) {
//			if(category.getId().equals(id)) {
//				if(category.getCategoryName() != null)
//				category.setCategoryName(newCategory.getCategoryName());
//				if(category.getCategoryDesc() != null)
//				category.setCategoryDesc(newCategory.getCategoryDesc());
//				return category;
//			}
//		}
//		return null;
//	}
	
	@PutMapping("/categories/{id}")
	public CategoryEntity changeCategory(@PathVariable Integer id, @RequestBody CategoryEntity changedCategory) {
		if(categoryRepository.existsById(id)) {
			CategoryEntity category = categoryRepository.findById(id).get();
			if(category.getCategoryName() != null)
				category.setCategoryName(changedCategory.getCategoryName());
				if(category.getCategoryDesc() != null)
				category.setCategoryDesc(changedCategory.getCategoryDesc());
				categoryRepository.save(category);
				return category;
		}
		return null;
	}
	
//	• 2.6 kreirati REST endpoint koji omogućava brisanje 
//	postojeće kategorije
//	• putanja /project/categories/{id}
//	• ukoliko je prosleđen ID koji ne pripada nijednoj kategoriji metoda 
//	treba da vrati null, a u suprotnom vraća podatke o kategoriji koja je 
//	obrisana
	
//	@DeleteMapping("/categories/{id}")
//	public CategoryEntity deleteCategory(@PathVariable Integer id) {
//		List<CategoryEntity> categories = getDB();
//		Iterator<CategoryEntity> it = categories.iterator();
//		while(it.hasNext()) {
//			CategoryEntity category = it.next();
//			if(category.getId().equals(id));
//			it.remove();
//			return category;
//		}
//		return null;
//	}
	
	@DeleteMapping("/categories/{categoryId}")
	public CategoryEntity delCategory(@PathVariable Integer categoryId) {
		if(categoryRepository.existsById(categoryId)) {
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		boolean chkOffer = offerDAO.ifCategoryHasNonExpOffers(categoryId);
		boolean chkBill = billDAO.areBillsActiveByCategory(categoryId);
		
		if(chkOffer || chkBill) {
		return null;
		}
		categoryRepository.delete(category);
		}
		return null;
	}
	
//	• 2.7 kreirati REST endpoint koji vraća kategoriju po 
//	vrednosti prosleđenog ID-a
//	• putanja /project/categories/{id}
//	• u slučaju da ne postoji kategorija sa traženom vrednošću ID-a 
//	vratiti null
	
//	@GetMapping("/categories/{id}")
//	public CategoryEntity getById(@PathVariable Integer id) {
//		for(CategoryEntity category : getDB()) {
//			if(category.getId().equals(id)) {
//				return category;
//			}
//		}
//		return null;
//	}
	
	@GetMapping("/categories/{id}")
	public CategoryEntity getById(@PathVariable Integer id) {
		return categoryRepository.findById(id).get();
	}
	
}
