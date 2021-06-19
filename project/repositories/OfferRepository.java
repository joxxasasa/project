package com.iktpreobuka.project.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.services.OfferDAOImpl;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer>{
	
	public List<OfferEntity> findByRegularPriceBetween(Double lowerPrice, Double upperPrice);
	public OfferEntity findByBills(Integer id);
	public List<OfferEntity> findAllByCategoryId(Integer categoryId);

}
