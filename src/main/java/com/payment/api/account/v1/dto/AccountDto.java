package com.payment.api.account.v1.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.payment.api.account.validators.OnCreate;

import lombok.Data;

@Data
public class AccountDto implements Serializable {

	private static final long serialVersionUID = 3074866807887331179L;
	private String accountId;
	@NotNull(groups = { OnCreate.class }, message = "Account Name cannot be null")
	@NotEmpty(groups = { OnCreate.class }, message = "Account Name cannot be empty")
	private String accountName;
	@NotNull(groups = { OnCreate.class }, message = "Current Balance cannot be null")
	@NotEmpty(groups = { OnCreate.class }, message = "Current Balance cannot be empty")
	private String currentBalance;
	@NotNull(groups = { OnCreate.class }, message = "Other Details cannot be null")
	@NotEmpty(groups = { OnCreate.class }, message = "Other Details cannot be empty")
	private String otherDetails;
	private String customerId;
	private CustomerDto customer;
}
