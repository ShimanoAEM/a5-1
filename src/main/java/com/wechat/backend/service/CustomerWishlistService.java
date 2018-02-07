package com.wechat.backend.service;

import java.util.List;

import com.wechat.backend.domain.Wishlist;

public interface CustomerWishlistService {

	/**
	 * save one product in wishlist
	 * @param openId
	 * @param productcode
	 * @return
	 */
	public void saveWishlist(String openId, String productcode);
	/**
	 * remove one wishlist from customer 
	 * @param wishId
	 * @return
	 */
	public void removeWishlist(String customerId,String wishId);
	
	/**
	 * 
	 * @param openId
	 * @return
	 */
	public List<Wishlist> getMyWishlist(String openId);
}
