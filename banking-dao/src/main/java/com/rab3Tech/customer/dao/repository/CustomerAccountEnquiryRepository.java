package com.rab3Tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3Tech.dao.entity.CustomerSaving;

public interface CustomerAccountEnquiryRepository extends JpaRepository<CustomerSaving, Integer> {

	Optional<CustomerSaving> findByEmail(String email);

	

	

}
