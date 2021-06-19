package com.iktpreobuka.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer>{

	public List<VoucherEntity> findAllByUserId(Integer id);
	public List<VoucherEntity> findAllByOfferId(Integer id);
	public List<VoucherEntity> findAllByExpirationDateAfter(LocalDate date);
}
