package com.wechat.backend.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wechatwishlist")
public class Wishlist {
	@Id
	@GeneratedValue
	private Long wishId;

	private String openId;
	
	//private String image;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wechatcustomer_openId")
	private Customer customer;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wechatproduct_code")
	private Product product;

	public Long getWishId() {
		return wishId;
	}

	public void setWishId(Long wishId) {
		this.wishId = wishId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
}
