package com.payment.api.account.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.api.account.constants.Status;
import com.payment.api.account.facade.AccountFacade;
import com.payment.api.account.v1.dto.AccountDto;
import com.payment.api.account.v1.dto.PaginationDto;
import com.payment.api.account.v1.dto.ResponseDto;
import com.payment.api.account.validators.OnCreate;
import com.payment.api.account.validators.OnUpdate;


@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountFacade accountFacade;
	
	/**
	 * Fetches all accounts of a customer
	 * @param customerId
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<ResponseDto<AccountDto>> findAccountsByCustomerId(
			@PathVariable String customerId, 
			@RequestParam("page") int page, 
			@RequestParam("size") int size) {
		PaginationDto<AccountDto> data = accountFacade.findByCustomerId(customerId, page, size);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<AccountDto>(
						Status.SUCCESS, 
						String.valueOf(HttpStatus.OK.value()), 
						data.getElements(), 
						"Operation was successful", 
						data.getPage(), data.getSize(), data.getTotal()));
	}
	
	/**
	 * Creates a new account for a Customer
	 * @param customerId
	 * @param accountDto
	 * @return
	 */
	@PostMapping("/{customerId}/accounts")
	public ResponseEntity<ResponseDto<AccountDto>> create(
			@PathVariable String customerId,
			@Validated({ OnCreate.class }) @RequestBody AccountDto accountDto) {
		List<AccountDto> data = Collections.singletonList(accountFacade.create(customerId, accountDto));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<AccountDto>(
						Status.SUCCESS, 
						String.valueOf(HttpStatus.OK.value()), 
						data, 
						"Operation was successful", 0, 0, 0L));
	}
	/**
	 * Updates the other Details field,
	 * The other details field is a string of
	 * JSON object. 
	 * This action replaces the record contained
	 * in the other details field
	 * @param accountDto
	 * @return
	 */
	@PostMapping("/customers/{customerId}/{accountId}")
	public ResponseEntity<ResponseDto<AccountDto>> update(
			@PathVariable String customerId,
			@PathVariable String accountId,
			@Validated({ OnUpdate.class }) @RequestBody AccountDto accountDto) {
		List<AccountDto> data = Collections.singletonList(accountFacade.update(customerId, accountId, accountDto));
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto<AccountDto>(
						Status.SUCCESS, 
						String.valueOf(HttpStatus.OK.value()), 
						data, 
						"Operation was successful", 0, 0, 0L));
	}
	
	/**
	 * Deletes all accounts
	 * of the customer passed by parameter
	 * @param customerId
	 */
	@DeleteMapping("/customers/{customerId}")
	public @ResponseBody void delete(@PathVariable String customerId) {
		accountFacade.delete(customerId);
	}
	
	/**
	 * Deletes specified accounts
	 * of the customer passed by parameter
	 * @param customerId
	 */
	@DeleteMapping("/customers/{customerId}/{accountId}")
	public @ResponseBody void delete(@PathVariable String customerId, @PathVariable String accountId) {
		accountFacade.delete(customerId, accountId);
	}
}
