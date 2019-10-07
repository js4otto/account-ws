package com.payment.api.account.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.payment.api.account.constants.Status;
import com.payment.api.account.exceptions.BusinessException;
import com.payment.api.account.v1.dto.ErrorDto;
import com.payment.api.account.v1.dto.ResponseDto;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { BindException.class })
	public @ResponseBody ResponseDto<ErrorDto> handleException(BindException e) {
		log.error("Global Validation Exception", e);
		List<ErrorDto> data = e.getBindingResult().getFieldErrors().stream()
				.map(a -> new ErrorDto(a.getField(), a.getDefaultMessage(), null))
				.collect(Collectors.toCollection(ArrayList::new));
		
		return new ResponseDto<ErrorDto>(Status.FAIL, String.valueOf(HttpStatus.BAD_REQUEST.value()), data, "Binding Exception", 0, 0, 0L);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingServletRequestParameterException.class })
	public @ResponseBody ResponseDto<ErrorDto> handleException(MethodArgumentNotValidException e) {
		log.error("Global Validation Exception e", e);
		List<ErrorDto> data = e.getBindingResult().getFieldErrors().stream()
				.map(a -> new ErrorDto(a.getField(), a.getDefaultMessage(), null))
				.collect(Collectors.toCollection(ArrayList::new));
		
		return new ResponseDto<ErrorDto>(Status.FAIL, String.valueOf(HttpStatus.BAD_REQUEST.value()), data, "Method Argument Exception", 0, 0, 0L);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { Exception.class })
	public @ResponseBody ResponseDto<ErrorDto> handleException(Exception e) {
		log.error("Global Exception ", e);
		return new ResponseDto<ErrorDto>(Status.FAIL, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), null, "Internal Server Error", 0, 0, 0L);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { BusinessException.class })
	public @ResponseBody ResponseDto<ErrorDto> handleException(BusinessException e) {
		log.error("Global Validation Exception e", e);
		if (!e.getErrors().isEmpty() && e.getErrors().size() > 0) {
			return new ResponseDto<ErrorDto>(Status.FAIL, String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getErrors(), "Business Exception", 0, 0, 0L);
		}
		return new ResponseDto<ErrorDto>(Status.FAIL, String.valueOf(HttpStatus.BAD_REQUEST.value()), null, "Business Exception", 0, 0, 0L);
	}
	
}
