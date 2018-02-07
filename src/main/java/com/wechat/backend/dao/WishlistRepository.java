package com.wechat.backend.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.wechat.backend.domain.Wishlist;

public interface WishlistRepository extends CrudRepository<Wishlist, String>,JpaSpecificationExecutor<Wishlist> {

	/**
	 * Find all Wishlist for one customer
	 * @param openId
	 * @return
	 */
	public List<Wishlist> findByOpenId(Specification<Wishlist> specification);
	
	/**
	 * Check whether product collected
	 * @param openId
	 * @param prouctcode
	 * @return
	 */
	public List<Wishlist> findAll(Specification<Wishlist> specification);
	

}
