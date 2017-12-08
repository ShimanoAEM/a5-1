package com.wechat.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.backend.dao.CustomerRepository;
import com.wechat.backend.dao.OrderRepository;
import com.wechat.backend.dao.ProductRepository;
import com.wechat.backend.domain.Customer;
import com.wechat.backend.domain.Order;
import com.wechat.backend.domain.Product;

@RestController
public class OrderController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public static enum OrderStatus {  
		  NOTPAID, COMPLETED, CANCELLED
	}
	
	@RequestMapping(value = "/placeOrder", method = { RequestMethod.GET, RequestMethod.POST })
	private String placeOrder(@RequestParam("productCode") String productCode ,
			@RequestParam("userOpenId") String userOpenId) {
		Order order = new Order();
		Product product = null;
		Customer releaseUser = null;
		product = productRepository.findByCode(Long.valueOf(productCode));
		if (product != null) {
			releaseUser = customerRepository.findByOpenId(product.getOpenId());
			order.setReleaseUser(releaseUser);
			order.setProduct(product);
			order.setTotalPrice(product.getPrice());
			if (StringUtils.isNotEmpty(userOpenId)) {
				Customer user = null;
				user = customerRepository.findByOpenId(userOpenId);
				if (user == null) {
				   return "fail";
				}
				order.setCustomer(user);
				order.setUserOpenId(userOpenId);
				order.setCreateDate(new Date());
				order.setStatus(OrderStatus.NOTPAID.toString());
				orderRepository.save(order);
			}
		}
		return "success";
	}
	
	@RequestMapping(value = "/completeOrder", method = { RequestMethod.GET, RequestMethod.POST })
	private Order modifyOrder(@RequestParam("code") String code) {
		Order order = null;
		order = orderRepository.findByCode(Long.valueOf(code));
		if (order != null) {
			order.setStatus(OrderStatus.COMPLETED.toString());
			orderRepository.save(order);
			Product product = order.getProduct();
			product.setIsSellOut(Boolean.TRUE);
			productRepository.save(product);
		}
		return order;
	}
	
	@RequestMapping(value = "/showOrders", method = { RequestMethod.GET, RequestMethod.POST })
	private List<Order> showOrders() {
		return (List<Order>) orderRepository.findAll();
	}
	
	@RequestMapping(value = "/orders", method = { RequestMethod.GET, RequestMethod.POST })
	private List<Order> getOrders(@RequestParam("openId") String openId) {
		List<Order> orders = new ArrayList<>();
		orders = orderRepository.findByUserOpenId(openId);
		return orders;
	}
	
	@RequestMapping(value = "/deleteOrders", method = { RequestMethod.GET, RequestMethod.POST })
	private String deleteOrders() {
		orderRepository.deleteAll();
			return "success";
	}
	
	@RequestMapping(value = "/cancelOrder", method = { RequestMethod.GET, RequestMethod.POST })
	private String deleteOrder(@RequestParam("code") String code) {
		Order order = null;
		order = orderRepository.findByCode(Long.valueOf(code));
		if (order != null) {
			order.setStatus(OrderStatus.CANCELLED.toString());
			orderRepository.save(order);
			Product product = order.getProduct();
			if (Boolean.FALSE.equals(product.getIsSellOut())) {
				product.setIsSellOut(Boolean.FALSE);
			}
			return "success";
		}
		return "fail";
	}
	
	@RequestMapping(value = "/getOrder", method = { RequestMethod.GET, RequestMethod.POST })
	private Order getOrder(@RequestParam("code") String code) {
		Order order = null;
		order = orderRepository.findByCode(Long.valueOf(code));
		return order;
	}

}
