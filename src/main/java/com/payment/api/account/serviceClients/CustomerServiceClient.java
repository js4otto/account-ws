package com.payment.api.account.serviceClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.payment.api.account.v1.dto.CustomerDto;
import com.payment.api.account.v1.dto.ResponseDto;

@FeignClient(name = "customers-ws")
public interface CustomerServiceClient {

	@GetMapping("/customers/{customerId}")
	public ResponseEntity<ResponseDto<CustomerDto>> findByCustomerId(@PathVariable String customerId);
}
