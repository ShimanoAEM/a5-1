package com.wechat.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wechat.backend.domain.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

		List<Order> findByUserOpenId(String openId);
		
		Order findByCode(Long code);
}
