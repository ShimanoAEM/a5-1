package com.wechat.backend.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wechatorder")
public class Order {

	@Id@GeneratedValue(strategy = GenerationType.AUTO)
	private Long code;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wechatcustomer_openId")
	private Customer customer;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wechatproduct_code")
	private Product product;
	
	private Double totalPrice;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="releaseUser_openId")
	private Customer releaseUser;
	
	private String userOpenId;
	
	private Date createDate;
	
	private String status;

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Customer getReleaseUser() {
		return releaseUser;
	}

	public void setReleaseUser(Customer releaseUser) {
		this.releaseUser = releaseUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
