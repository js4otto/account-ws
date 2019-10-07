package com.payment.api.account.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.payment.api.account.v1.dto.ErrorDto;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -5792106429164739445L;
	private List<ErrorDto> errors = new ArrayList<>();

	public BusinessException(List<ErrorDto> errors) {
		this.errors = errors;
	}

	public List<ErrorDto> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDto> errors) {
		this.errors = errors;
	}
}
