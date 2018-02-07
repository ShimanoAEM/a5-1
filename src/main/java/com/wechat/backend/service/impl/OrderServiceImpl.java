package com.wechat.backend.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.backend.dao.OrderRepository;
import com.wechat.backend.domain.Order;
import com.wechat.backend.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderRepository orderRepository;
	

	List<Order> findByUserOpenId(String openId){
		return orderRepository.findByUserOpenId(openId);
	}
	
	Order findByCode(String code){
		return orderRepository.findOne(code);
	}

	@Override
	public Order getOrderInfo(String orderCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrderList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
