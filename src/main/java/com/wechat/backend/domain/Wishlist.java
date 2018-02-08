package com.wechat.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wechatwishlist")
public class Wishlist {
	@Id
	@GeneratedValue
	private Long wishId;
	
	private String productName;
	
	private Long productCode;
	
	private Double price;
	
	private String description;
	//seller openid
	private String sellerOpenId;
	//visit customer
	private String openId;
	
	private String image;

	public Long getWishId() {
		return wishId;
	}

	public void setWishId(Long wishId) {
		this.wishId = wishId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductCode() {
		return productCode;
	}

	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSellerOpenId() {
		return sellerOpenId;
	}

	public void setSellerOpenId(String sellerOpenId) {
		this.sellerOpenId = sellerOpenId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
