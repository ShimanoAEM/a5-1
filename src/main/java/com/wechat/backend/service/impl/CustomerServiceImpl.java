package com.wechat.backend.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wechat.backend.dao.CustomerRepository;
import com.wechat.backend.domain.Customer;
import com.wechat.backend.service.CustomerService;
import com.wechat.backend.utils.CommonUtil;

import net.sf.json.JSONObject;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	private static final String OAUTH_STRING = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	private static final Logger LOG = Logger.getLogger(CustomerServiceImpl.class);
	
	@Value("${wechat.appId}")
	private String appId;
	
	@Value("${wechat.appSecret}")
	private String appSecret;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public String getOpenId(String jsCode) {
		String openId = null;
		if (StringUtils.isNoneBlank(jsCode)) {
			String requestUrl = OAUTH_STRING.replace("APPID", appId)
					.replace("SECRET", appSecret).replace("JSCODE", jsCode);
			JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
			openId = (jsonObject != null) ? jsonObject.getString("openid") : StringUtils.EMPTY;
			LOG.info("The openId is......"+openId);
		}
		return openId;
	}

	/**
	 * get Customer detail info
	 * @param customerId
	 * @return
	 */
	public Customer getCustomerInfo(String customerId){
		return customerRepository.findByOpenId(customerId);
	}
	
	/**
	 * get Customer list 
	 * @return
	 */
	public List<Customer> getCustomerList(Integer page,Integer size, Map customerCondition){
		//Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "openId"); 
		
		//Page<Customer> customerPage = customerRepository.findAll(new Specification<Customer>(){  
       //     @Override  
       //     public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {  
      //          Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), customerCondition.getName());  
       //         Predicate p2 = criteriaBuilder.equal(root.get("isbn").as(String.class), customerCondition.getIsbn());  
       //         Predicate p3 = criteriaBuilder.equal(root.get("author").as(String.class), customerCondition.getAuthor());  
      //          query.where(criteriaBuilder.and(p1,p2,p3));  
      //          return query.getRestriction();  
     //       }  
      //  },pageable);  
		return  null;
		//return customerRepository.findByCondition(pageable,customerCondition);
	}
	
}
