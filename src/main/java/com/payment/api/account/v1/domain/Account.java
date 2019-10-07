package com.payment.api.account.v1.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ACCOUNT")
@Data
public class Account implements Serializable {

	private static final long serialVersionUID = -6982871026638926050L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String accountId;
	@Column(nullable = false)
	private String accountName;
	@Column()
	private String currentBalance;
	@Column
	private String otherDetails;
	@Column(nullable = false)
	private String customerId;
}
