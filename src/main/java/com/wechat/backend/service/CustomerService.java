package com.wechat.backend.service;

import java.util.List;
import java.util.Map;

import com.wechat.backend.domain.Customer;
import com.wechat.backend.domain.Wishlist;

public interface CustomerService{

	String getOpenId(String jsCode);
	
	/**
	 * get Customer detail info
	 * @param customerId
	 * @return
	 */
	public Customer getCustomerInfo(String openId);
	
	/**
	 * get Customer list 
	 * @return
	 */
	public List<Customer> getCustomerList(Integer page,Integer size,Map customerCondition);
	
}
