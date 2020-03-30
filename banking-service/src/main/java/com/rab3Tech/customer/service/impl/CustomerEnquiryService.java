package com.rab3Tech.customer.service.impl;

import java.util.List;

import com.rab3Tech.vo.CustomerSavingVO;

public interface CustomerEnquiryService {

	public CustomerSavingVO save(CustomerSavingVO customerSavingVO);

	CustomerSavingVO findById(int csaid);

	List<CustomerSavingVO> findAll();

	void deleteById(int csaid);

	boolean emailNotExist(String email);

	String updateEnquiryRegId(int csaid, String ucrid);

	CustomerSavingVO changeEnquiryStatus(int csaid, String status);

}
