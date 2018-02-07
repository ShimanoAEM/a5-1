package com.wechat.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wechat.backend.dao.ProductRepository;
import com.wechat.backend.domain.Product;
import com.wechat.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	/**
	 * get product detail info
	 * @param productCode
	 * @return
	 */
	public Product getProductByCode(String productCode){
		return productRepository.findByCode(Long.valueOf(productCode));
	}
	
	/**
	 * get products info
	 * @param productCode
	 * @return
	 */
	public List<Product> getProductList(){
		//TODO
		//return productRepository.findByOpenId("");
		return null;
	}
	
	/**
	 * get Product by condition
	 * @param conditionMap
	 * @return
	 */
	public List<Product> getProductListByCondition(Map conditionMap){
		//TODO
		return null;
	}
	
}
