package com.wechat.backend.controller;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.backend.dao.CustomerRepository;
import com.wechat.backend.dao.WechatCollectorRepository;
import com.wechat.backend.domain.Collector;
import com.wechat.backend.domain.Customer;

@RestController
public class WechatCollectorController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WechatCollectorRepository wechatCollectorRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@RequestMapping(value = "/findCollectors", method = { RequestMethod.GET, RequestMethod.POST }) // 获取关注的卖家
	public Set<Customer> findCollectors(@Param("openId") String openId) {
		Collector collector = wechatCollectorRepository.findByOpenId(openId);
		return collector.getCollectors();
	}
	
	@RequestMapping(value = "/addCollector", method = { RequestMethod.GET, RequestMethod.POST }) // 关注卖家
	public String addCollectors(@Param("openId") String openId , @Param("collectOpenId") String collectOpenId) {
		if (!openId.equals(collectOpenId)) {
			Customer customer = customerRepository.findByOpenId(collectOpenId);
			Collector collector = null;
			collector = wechatCollectorRepository.findByOpenId(openId);
			Set<Customer> collectors = new HashSet<>();
			if (collector == null) {
				collector = new Collector();
				collector.setOpenId(openId);
			}
			if (collector.getCollectors() != null && collector.getCollectors().size() > 0) {
				collectors = collector.getCollectors();
			}
			if (customer != null) {
				collectors.add(customer);
			}
			collector.setCollectors(collectors);
			wechatCollectorRepository.save(collector);
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/removeAllCollectors", method = { RequestMethod.GET, RequestMethod.POST }) // 取消卖家关注
	public String removeAllCollectors() {
		wechatCollectorRepository.deleteAll();
		return "success";
	}
	
	@RequestMapping(value = "/removeCollector", method = { RequestMethod.GET, RequestMethod.POST }) // 取消卖家关注
	public String removeCollector(@Param("openId") String openId , @Param("collectOpenId") String collectOpenId) {
		Customer customer = customerRepository.findByOpenId(collectOpenId);
		Collector collector = null;
		collector = wechatCollectorRepository.findByOpenId(openId);
		if (collector == null) {
			collector = new Collector();
			collector.setOpenId(openId);
		}
		Set<Customer> collectors = collector.getCollectors();
		collectors.remove(customer);
		collector.setCollectors(collectors);
		wechatCollectorRepository.save(collector);
		return "success";
	}
	
	@RequestMapping(value = "/checkCollector", method = { RequestMethod.GET, RequestMethod.POST }) // 校验是否关注
	public boolean checkCollector(@Param("openId") String openId , @Param("collectOpenId") String collectOpenId) {
		Customer customer = customerRepository.findByOpenId(collectOpenId);
		Collector collector = wechatCollectorRepository.findByOpenId(openId);
		if (customer != null && collector != null && collector.getCollectors().contains(customer)) {
			return true;
		}else {
			return false;
		}
	}
}
