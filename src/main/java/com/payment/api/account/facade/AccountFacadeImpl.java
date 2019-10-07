package com.payment.api.account.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.api.account.service.AccountService;
import com.payment.api.account.serviceClients.CustomerServiceClient;
import com.payment.api.account.v1.dto.AccountDto;
import com.payment.api.account.v1.dto.CustomerDto;
import com.payment.api.account.v1.dto.PaginationDto;

@Service
public class AccountFacadeImpl implements AccountFacade {

	@Autowired
	private AccountService accountService;
	@Autowired
	private CustomerServiceClient customerServiceClient;
	
//	private CustomerDto customerServiceClientRead(String customerId) { return null; }
	
	/**
	 * Makes a call to customer micro_service to
	 * check if customer exist.
	 * If exist creates a new account for the customer
	 */
	@Override
	public AccountDto create(String customerId, AccountDto accountDto) {
		List<CustomerDto> cusList = customerServiceClient.findByCustomerId(customerId).getBody().getData();
		if (!cusList.isEmpty() && cusList.size() > 0) {
			CustomerDto customerDto = cusList.get(0);
			if (customerDto.getCustomerId().equals(customerId)) {
				accountDto = accountService.create(customerId, accountDto);
			}
		}
		return accountDto;
	}
	
	@Override
	public PaginationDto<AccountDto> findByCustomerId(String customerId, int page, int size) {
		PaginationDto<AccountDto> accountPage = null;
		List<CustomerDto> cusList = customerServiceClient.findByCustomerId(customerId).getBody().getData();
		if (!cusList.isEmpty() && cusList.size() > 0) {
			CustomerDto customerDto = cusList.get(0);
			if (customerDto.getCustomerId().equals(customerId)) {
				accountPage = accountService.findAccountsByCustomerId(customerId, page, size);
			}
		}
		return accountPage;
	}

	@Override
	public AccountDto update(String customerId, String accountId, AccountDto accountDto) {
		List<CustomerDto> cusList = customerServiceClient.findByCustomerId(customerId).getBody().getData();
		if (!cusList.isEmpty() && cusList.size() > 0) {
			CustomerDto customerDto = cusList.get(0);
			if (customerDto.getCustomerId().equals(customerId)) {
				accountDto = accountService.update(customerId, accountId, accountDto);
			}
		}
		return accountDto;
	}

	@Override
	public void delete(String customerId) {
		List<CustomerDto> cusList = customerServiceClient.findByCustomerId(customerId).getBody().getData();
		if (!cusList.isEmpty() && cusList.size() > 0) {
			accountService.delete(customerId);
		}
	}

	@Override
	public void delete(String customerId, String accountId) {
		List<CustomerDto> cusList = customerServiceClient.findByCustomerId(customerId).getBody().getData();
		if (!cusList.isEmpty() && cusList.size() > 0) {
			accountService.delete(customerId, accountId);
		}
	}

}
