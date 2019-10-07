package com.payment.api.account.facade;

import com.payment.api.account.v1.dto.AccountDto;
import com.payment.api.account.v1.dto.PaginationDto;

public interface AccountFacade {

	AccountDto create(String customerId, AccountDto accountDto);
	
	PaginationDto<AccountDto> findByCustomerId(String customerId, int page, int size);
	
	AccountDto update(String customerId, String accountId, AccountDto accountDto);
	
	void delete(String customerId);
	
	void delete(String customerId, String accountId);
}
