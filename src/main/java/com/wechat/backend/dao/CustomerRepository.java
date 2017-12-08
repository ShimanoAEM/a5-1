package com.wechat.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.wechat.backend.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{

	Customer findByOpenId(String openId);
}
