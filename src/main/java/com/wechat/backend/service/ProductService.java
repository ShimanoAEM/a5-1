package com.wechat.backend.service;

import java.util.List;
import java.util.Map;

import com.wechat.backend.domain.Product;

public interface ProductService {
	
	/**
	 * get product detail info
	 * @param productCode
	 * @return
	 */
	public Product getProductByCode(String productCode);
	
	/**
	 * get products info
	 * @param productCode
	 * @return
	 */
	public List<Product> getProductList();
	
	/**
	 * get Product by condition
	 * @param conditionMap
	 * @return
	 */
	public List<Product> getProductListByCondition(Map conditionMap);
	
	
}
