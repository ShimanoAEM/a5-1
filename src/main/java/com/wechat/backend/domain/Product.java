package com.wechat.backend.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "wechatproduct")
public class Product {

	@Id
	private Long code;
	
	private String name;
	
	private String description;
	
	private String image;
	
	private Double originalPrice;
	
	private Double price;
	
	private Boolean platformKeeping;
	
	private String openId;
	
	private Date releaseDate;
	
	private Boolean isSellOut;
	
	private Boolean isOnline;
	
	private Long viewNum;
	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Boolean getPlatformKeeping() {
		return platformKeeping;
	}

	public void setPlatformKeeping(Boolean platformKeeping) {
		this.platformKeeping = platformKeeping;
	}

	public Double getPrice() {
		return price;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Boolean getIsSellOut() {
		return isSellOut;
	}

	public void setIsSellOut(Boolean isSellOut) {
		this.isSellOut = isSellOut;
	}

	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

}
