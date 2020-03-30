package com.rab3Tech.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3Tech.customer.service.impl.CustomerEnquiryService;
import com.rab3Tech.vo.CustomerSavingVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerController {

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	@GetMapping(value = "/customers/enquiry/{csaid}")
	public CustomerSavingVO getCustomerById(@PathVariable int csaid) {
		CustomerSavingVO response = customerEnquiryService.findById(csaid);
		return response;

	}

	@PostMapping(value = "customers/enquiry")
	public CustomerSavingVO saveCustomer(@RequestBody CustomerSavingVO customerSavingVO) {
		CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
		return response;
	}

	@GetMapping(value = "customers/enquiry")
	public List<CustomerSavingVO> getAllCustomersList() {

		List<CustomerSavingVO> response = customerEnquiryService.findAll();
		return response;
	}

	@DeleteMapping(value = "/customers/enquiry/{csaid}")
	public void deleteById(@PathVariable int csaid) {
		customerEnquiryService.deleteById(csaid);
	}
}
