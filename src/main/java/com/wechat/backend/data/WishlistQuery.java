package com.wechat.backend.data;

public class WishlistQuery {
	private String openId;
	private String productCode;
	private String sellerOpenId;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getSellerOpenId() {
		return sellerOpenId;
	}
	public void setSellerOpenId(String sellerOpenId) {
		this.sellerOpenId = sellerOpenId;
	}
	
}
