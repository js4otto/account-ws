package com.payment.api.account.service;

import com.payment.api.account.v1.dto.AccountDto;
import com.payment.api.account.v1.dto.PaginationDto;

public interface AccountService {

	public AccountDto create(String customerId, AccountDto accountDto);
	
	public AccountDto update(String customerId, String accountId, AccountDto accountDto);
	
	public PaginationDto<AccountDto> findAccountsByCustomerId(String customerId, int page, int size);
	
	public void delete(String customerId);
	
	public void delete(String customerId, String accountId);
}
