package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;

import com.iktpreobuka.project.entities.BillEntity;

public interface BillDAO {
	
	
	public BillEntity addBill(Boolean paymentMade, Boolean paymentCanceled, LocalDate billCreated, Integer offerId, Integer buyerId);
	public BillEntity changeBill(Integer id, BillEntity changedBill);
	public List<BillEntity> getAllByDate(LocalDate startDate, LocalDate endDate);
	public boolean areBillsActiveByCategory(Integer categoryId);
	public List<BillEntity> cancelBills(Integer offerId);

}
