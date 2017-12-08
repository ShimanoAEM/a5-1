package com.wechat.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wechat.backend.domain.Product;



public interface ProductRepository  extends CrudRepository<Product, String>{
	

	Product findByCode(Long code);
	
	List<Product> findByOpenId(String openId);

}
