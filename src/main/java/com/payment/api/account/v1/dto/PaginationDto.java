package com.payment.api.account.v1.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginationDto<T> {

	private List<T> elements;
	private Integer page;
	private Long total;
	private Integer size;
	
}
