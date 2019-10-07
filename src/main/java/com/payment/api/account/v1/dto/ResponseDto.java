package com.payment.api.account.v1.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
	
	private String status;
	private String code;
	private List<T> data;
	private String message;

	private Integer page;
	private Integer size;
	private Long total;
}
