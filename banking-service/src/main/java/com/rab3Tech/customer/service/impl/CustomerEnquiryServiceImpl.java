package com.rab3Tech.customer.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.rab3Tech.admin.dao.repository.AccountStatusRepository;
import com.rab3Tech.admin.dao.repository.AccountTypeRepository;
import com.rab3Tech.customer.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3Tech.dao.entity.AccountStatus;
import com.rab3Tech.dao.entity.AccountType;
import com.rab3Tech.dao.entity.CustomerSaving;
import com.rab3Tech.email.service.EmailService;
import com.rab3Tech.service.exception.BankServiceException;
import com.rab3Tech.utils.Utils;
import com.rab3Tech.vo.CustomerSavingVO;
import com.rab3Tech.vo.EmailVO;

@Service
@Transactional
public class CustomerEnquiryServiceImpl implements CustomerEnquiryService {

	@Autowired
	private CustomerAccountEnquiryRepository customerAccountEnquiryRepository;

	@Autowired
	private AccountTypeRepository accountTyperepository;

	@Autowired
	private AccountStatusRepository accountStatusRepository;

	@Autowired
	private EmailService emailService;

	@Value("${bank.from.email:ghimire.bikash30@gmail.com}")
	private String fromEmail;

	@Override
	public CustomerSavingVO save(CustomerSavingVO customerSavingVO) {

		CustomerSaving customerSaving = new CustomerSaving();
		customerSavingVO.setDoa(new Date());
		customerSavingVO.setAppref("M-" + Utils.genRandomAlphaNum());
		boolean b = TransactionSynchronizationManager.isActualTransactionActive();
		if (b) {
			System.out.println("Transaction is working!!");
		}
		BeanUtils.copyProperties(customerSavingVO, customerSaving, new String[] { "accType", "status" });

		Optional<AccountType> accountType = accountTyperepository.findByName(customerSavingVO.getAccType());
		if (accountType.isPresent()) {
			customerSaving.setAccType(accountType.get());
			;
		} else {
			throw new BankServiceException("Hey this " + customerSavingVO.getAccType() + " account type is not valid!");
		}

		AccountStatus accountStatus = new AccountStatus();
		accountStatus.setId(1);
		customerSaving.setStatus(accountStatus);

		CustomerSaving saveEntity = customerAccountEnquiryRepository.save(customerSaving);
		customerSavingVO.setCsaid(saveEntity.getCsaid());

		System.out.println("Email sending...." + LocalDateTime.now());
		emailService.sendEnquiryEmail(new EmailVO(customerSavingVO.getEmail(), fromEmail, null,
				"Hello! your account enquiry is submitted successfully.", customerSavingVO.getName()));
		System.out.println("Email done..." + LocalDateTime.now());

		return customerSavingVO;
	}

	@Override
	public CustomerSavingVO findById(int csaid) {

		CustomerSaving customerSaving = customerAccountEnquiryRepository.findById(csaid).get();
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		BeanUtils.copyProperties(customerSaving, customerSavingVO, new String[] { "accType", "status" });
		customerSavingVO.setAccType(customerSaving.getAccType().getName());
		customerSavingVO.setStatus(customerSaving.getStatus().getName());
		return customerSavingVO;
	}

	@Override
	public List<CustomerSavingVO> findAll() {

		List<CustomerSaving> customerSavingList = customerAccountEnquiryRepository.findAll();
		return convertEntityIntoVO(customerSavingList);

	}

	private List<CustomerSavingVO> convertEntityIntoVO(List<CustomerSaving> customerSavingList) {

		List<CustomerSavingVO> customerSavingVOs = new ArrayList<CustomerSavingVO>();
		for (CustomerSaving cse : customerSavingList) {
			CustomerSavingVO customerSavingVO = new CustomerSavingVO();
			BeanUtils.copyProperties(cse, customerSavingVO, new String[] { "accType", "status" });
			customerSavingVO.setAccType(cse.getAccType().getName());
			customerSavingVO.setStatus(cse.getStatus().getName());
			customerSavingVOs.add(customerSavingVO);
		}

		return customerSavingVOs;
	}

//	@Override
//	public CustomerSavingVO updateCustomers(int csaid, CustomerSavingVO customerSavingVO) {
//
//		Optional<CustomerSavingEntity> customerSavingEntity = customerAccountEnquiryRepository.findById(csaid);
//		CustomerSavingEntity customerSavingEntity2 = customerSavingEntity.get();
//		customerSavingEntity2.setDoa(new Date());
//		customerSavingEntity2.setAppref(Utils.genRandomAlphaNum());
//		BeanUtils.copyProperties(customerSavingVO, customerSavingEntity2);
//		CustomerSavingEntity savingEntity2 = customerAccountEnquiryRepository.save(customerSavingEntity2);
//		customerSavingVO.setCsaid(savingEntity2.getCsaid());
//		return customerSavingVO;
//
//	}

	@Override
	public void deleteById(int csaid) {
		customerAccountEnquiryRepository.deleteById(csaid);
	}

	@Override
	public boolean emailNotExist(String email) {

		Optional<CustomerSaving> optional = customerAccountEnquiryRepository.findByEmail(email);
		if (optional.isPresent()) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public String updateEnquiryRegId(int csaid, String ucrid) {
		CustomerSaving customerSaving = customerAccountEnquiryRepository.findById(csaid).get();
		customerSaving.setUcrid(ucrid);
		return "done";
	}

	@Override
	public CustomerSavingVO changeEnquiryStatus(int csaid, String status) {
		CustomerSaving customerSavingEntity = customerAccountEnquiryRepository.findById(csaid).get();
		// status = APPROVED
		AccountStatus accountStatus = accountStatusRepository.findByName(status).get();
		// Updating account status
		customerSavingEntity.setStatus(accountStatus);
		// Sending Back customer enquiry
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		BeanUtils.copyProperties(customerSavingEntity, customerSavingVO, new String[] { "accType", "status" });
		customerSavingVO.setAccType(customerSavingEntity.getAccType().getName());
		customerSavingVO.setStatus(customerSavingEntity.getStatus().getName());
		return customerSavingVO;

	}

}
