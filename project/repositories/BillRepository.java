package com.iktpreobuka.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project.entities.BillEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer>{

	public List<BillEntity> findAllByUserId(Integer Id);
	public List<BillEntity> findAllByOfferCategoryId(Integer categoryId);
	public List<BillEntity> findAllByBillCreatedBetween(LocalDate startDate, LocalDate endDate);
	public List<BillEntity> findAllByOfferId(Integer id);
}
