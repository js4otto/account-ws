package com.payment.api.account.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.payment.api.account.exceptions.BusinessException;
import com.payment.api.account.repository.AccountRepository;
import com.payment.api.account.specifications.AccountSpecification;
import com.payment.api.account.v1.domain.Account;
import com.payment.api.account.v1.dto.AccountDto;
import com.payment.api.account.v1.dto.ErrorDto;
import com.payment.api.account.v1.dto.PaginationDto;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public AccountDto create(String customerId, AccountDto accountDto) {
		try {
			String accountId = UUID.randomUUID().toString();
			while(accountRepository.findByAccountId(accountId).isPresent()) {
				accountId = UUID.randomUUID().toString();
			};
			accountDto.setCustomerId(customerId);
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STRICT);
			Account account = modelMapper.map(accountDto, Account.class);
			account.setAccountId(accountId);
			accountRepository.save(account);
			accountDto.setAccountId(accountId);
		} catch(Exception e) {
			throw e;
		}
		return accountDto;
	}

	@Override
	public AccountDto update(String customerId, String accountId, AccountDto accountDto) {
		try {
			Optional<Account> accOpl = accountRepository.findByCustomerIdAndAccountId(customerId, accountId);
			if (!accOpl.isPresent()) {
				
				throw new BusinessException(
						Collections.singletonList(
								new ErrorDto("accountId", "No Account with id: " + accountId + " associated to customer Id: " + customerId, null)));
			}
			ModelMapper modelMapper = new ModelMapper();
			Account account = accOpl.get();
			account.setOtherDetails(accountDto.getOtherDetails());
			accountRepository.save(account);
			accountDto = modelMapper.map(account, AccountDto.class);
		} catch(Exception e) {
			throw e;
		}
		return accountDto;
	}
	
	private Pageable createPageRequest(int page, int size) {
		return PageRequest.of(page, size);
	}

	public PaginationDto<AccountDto> searchByAccountNameForCustomerId(String customerId, String accountName, int page, int size) {
		// TODO Auto-generated method stub
		Page<Account> pages = accountRepository
				.findAll(AccountSpecification.hasCustomerId(customerId)
						.and(AccountSpecification.containsAccountName(accountName)), createPageRequest(page, size));
		PaginationDto<AccountDto> accountsPage = new PaginationDto<>();
		accountsPage.setPage(page);
		accountsPage.setSize(size);
		accountsPage.setTotal(pages.getTotalElements());
		accountsPage.setElements(pages.getContent().stream().map(account -> {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STRICT);
			return modelMapper.map(account, AccountDto.class);
		}).collect(Collectors.toList()));
		return accountsPage;
	}

	@Override
	public PaginationDto<AccountDto> findAccountsByCustomerId(String customerId, int page, int size) {
		Page<Account> pages = accountRepository
				.findAll(AccountSpecification.hasCustomerId(customerId), createPageRequest(page, size));
		PaginationDto<AccountDto> accountsPage = new PaginationDto<>();
		accountsPage.setPage(page);
		accountsPage.setSize(size);
		accountsPage.setTotal(pages.getTotalElements());
		accountsPage.setElements(pages.getContent().stream().map(account -> {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STRICT);
			return modelMapper.map(account, AccountDto.class);
		}).collect(Collectors.toList()));
		return accountsPage;
	}

	@Override
	public void delete(String customerId) {
		Optional<List<Account>> accOpl = accountRepository.findByCustomerId(customerId);
		if (!accOpl.isPresent()) {
			throw new BusinessException(
					Collections.singletonList(
							new ErrorDto("customerId", "No Account associated to customer Id: " + customerId, null)));
		}
		accountRepository.deleteInBatch(accOpl.get());
	}

	@Override
	public void delete(String customerId, String accountId) {
		Optional<Account> accOpl = accountRepository.findByCustomerIdAndAccountId(customerId, accountId);
		if (!accOpl.isPresent()) {
			throw new BusinessException(
					Collections.singletonList(
							new ErrorDto("accountId", "No Account with id: " + accountId + " associated to customer Id: " + customerId, null)));
		}
		accountRepository.delete(accOpl.get());
	}

}
