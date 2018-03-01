package com.wechat.backend.domain;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "wechatcollector")
public class Collector {

	@Id
	private String openId;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="collectors")
	private Set<Customer> collectors;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Set<Customer> getCollectors() {
		return collectors;
	}

	public void setCollectors(Set<Customer> collectors) {
		this.collectors = collectors;
	}

	
}
