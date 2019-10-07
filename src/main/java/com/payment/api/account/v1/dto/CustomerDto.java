package com.payment.api.account.v1.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerDto implements Serializable {
	

	private static final long serialVersionUID = 294499629684164913L;
	private String customerId;
	private String customerName;
	private String otherDetails;
	private String customerLoginId;
}
