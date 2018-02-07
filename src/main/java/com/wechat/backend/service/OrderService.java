package com.wechat.backend.service;

import java.util.List;

import com.wechat.backend.domain.Order;

public interface OrderService {

	public Order getOrderInfo(String orderCode);
	
	public List<Order> getOrderList();
}
