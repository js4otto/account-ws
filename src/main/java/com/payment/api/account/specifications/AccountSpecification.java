package com.payment.api.account.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.payment.api.account.v1.domain.Account;

public class AccountSpecification implements Specification<Account> {

	private static final long serialVersionUID = 6609919472494220446L;

	public static Specification<Account> containsAccountName(String accountName) {
		final List<Predicate> predicateList = new ArrayList<>();
		return ((root, query, cb) -> {
			if (!accountName.isEmpty()) {
				predicateList.add(cb.like(root.get("accountName"), "%" + accountName + "%"));
			}
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		});
	}
	
	public static Specification<Account> hasCustomerId(String customerId) {
		final List<Predicate> predicateList = new ArrayList<>();
		return ((root, query, cb) -> {
			if (!customerId.isEmpty()) {
				predicateList.add(cb.equal(root.get("customerId"), customerId));
			}
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		});
	}
	
	@Override
	public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
