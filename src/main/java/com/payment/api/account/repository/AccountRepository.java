package com.payment.api.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.payment.api.account.v1.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account>{

	Optional<Account> findByAccountId(String accountId);
	
	Optional<List<Account>> findByCustomerId(String customerId);
	
	Optional<Account> findByCustomerIdAndAccountId(String customerId, String accountId);
}
