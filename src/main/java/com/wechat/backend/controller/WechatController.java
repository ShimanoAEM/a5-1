package com.wechat.backend.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.backend.dao.CustomerRepository;
import com.wechat.backend.dao.ProductRepository;
import com.wechat.backend.domain.Customer;
import com.wechat.backend.domain.Product;
import com.wechat.backend.domain.Wishlist;
import com.wechat.backend.service.CustomerService;
import com.wechat.backend.service.CustomerWishlistService;

@RestController
public class WechatController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWishlistService customerWishlistService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Value("${spring.file.imageupload}")
	private String imageuploadpath;
	
	@RequestMapping(value = "/updateUserInfo", method = { RequestMethod.GET, RequestMethod.POST }) // 更新用户信息
	public String updateUserInfo(@Param("openId") String openId, @RequestParam("mobileNum") String mobileNum,
			@RequestParam("name") String name, @RequestParam("project") String project,
			@RequestParam("seat") String seat,@RequestParam("wechatCode") String wechatCode) {
		Customer customer = null;
		if (StringUtils.isNotEmpty(openId)) {
			customer = customerRepository.findByOpenId(openId);
			if (customer == null) {
				customer = new Customer();
				customer.setOpenId(openId);
			}
			customer.setMobileNum(Long.valueOf(mobileNum));
			customer.setName(name);
			customer.setProject(project);
			customer.setSeat(seat);
			customer.setWechatCode(wechatCode);
			customerRepository.save(customer);
			return "success";
		}
		return "fail";
	}
	
	@RequestMapping(value = "/getProduct", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public Product getProduct(@RequestParam("productCode") String productCode) {
		Product product = null;
		product = productRepository.findByCode(Long.valueOf(productCode));
		if (product != null) {
			product.setViewNum(product.getViewNum() != null ? product.getViewNum() + 1 : 0);
			productRepository.save(product);
		}
		return product;
	}
	
	@RequestMapping(value = "/deleteProduct", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public String deleteProduct(@RequestParam("productCode") String productCode) {
		Product product = null;
		product = productRepository.findByCode(Long.valueOf(productCode));
		if(product != null){
			productRepository.delete(product);
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/deleteProducts", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public String deleteAllProducts() {
		productRepository.deleteAll();
		return "success";
	}
	
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public Customer getUserInfo(@RequestParam("openId") String openId) {
		Customer customer = null;
		if (StringUtils.isNotEmpty(openId)) {
			customer = customerRepository.findByOpenId(openId);
		}
		return customer;
	}
	
	@RequestMapping(value = "/getOpenId", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户openid
	public String getOpenId(@RequestParam("code") String code) {
		//System.out.println("=============="+code);
		String openId = customerService.getOpenId(code);
		Customer customer = null;
		customer = customerRepository.findByOpenId(openId);
		if (customer == null) {
			customer = new Customer();
			customer.setOpenId(openId);
			customerRepository.save(customer);
		}
		return openId;
	}
	
	@RequestMapping(value = "/saveAvatar", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveUserAvatar(@RequestParam("openId") String openId , 
			@RequestParam("avatar") String avatarUrl , 
			@RequestParam("wechatName") String wechatName) {
		Customer customer = null;
		customer = customerRepository.findByOpenId(openId);
		if (customer == null) {
			customer = new Customer();
			customer.setOpenId(openId);
			customer.setAvatarUrl(avatarUrl);
			customer.setWechatName(wechatName);
			customerRepository.save(customer);
		}else {
			customer.setAvatarUrl(avatarUrl);
			customer.setWechatName(wechatName);
			customerRepository.save(customer);
		}
		return openId;
	}
	
	@RequestMapping(value = "/getReleasedProducts", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public List<Product> getUploadProductsByUser(@RequestParam("openId") String openId) {
		List<Product> products = new ArrayList<>();
		if (StringUtils.isNotEmpty(openId)) {
			products = productRepository.findByOpenId(openId);
		}
		return products;
	}
	
	@RequestMapping(value ="/showProducts" , method = {RequestMethod.GET, RequestMethod.POST})
	public List<Product> showAll(){
		return (List<Product>) productRepository.findAll();
	}
	
	@RequestMapping(value = "/releaseProduct", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public String releaseProduct(@RequestParam("openId") String openId , @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("image") String image, @RequestParam("originalPrice") String originalPrice,
			@RequestParam("price") String price , @RequestParam("platformKeeping") String platformKeeping ,HttpServletRequest request) throws Exception{
		if (StringUtils.isNotEmpty(openId) && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(description) 
				&& StringUtils.isNotEmpty(originalPrice) && StringUtils.isNotEmpty(price) 
				&& StringUtils.isNotEmpty(platformKeeping) && StringUtils.isNotEmpty(image)) {
			String prefixName = image;
			if (prefixName.contains("wxfile://")) {
			    prefixName=prefixName.replace("wxfile://", "");
			}
			String realFilePath = getFilePath(request,prefixName);
			Product product = new Product();
			product.setImage(realFilePath);
			product.setCode((new Date()).getTime());
			product.setName(name);
			product.setDescription(description);
			product.setOpenId(openId);
			product.setOriginalPrice(Double.valueOf(originalPrice));
			product.setPrice(Double.valueOf(price));
			product.setReleaseDate(new Date());
			product.setPlatformKeeping(Boolean.valueOf(platformKeeping));
			product.setIsSellOut(Boolean.FALSE);
			productRepository.save(product);
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping(value = "/modifyRelease", method = {RequestMethod.GET, RequestMethod.POST})
	public Product updateProduct(@RequestParam("productCode") String productCode , String name , String description , Double originalPrice ,Double price){
		Product product = productRepository.findByCode(Long.valueOf(productCode));
		product.setName(name);
		product.setDescription(description);
		product.setOriginalPrice(originalPrice);
		product.setPrice(price);
		productRepository.save(product);
		return product;
		
	}
	
	@SuppressWarnings("deprecation")
	private String getFilePath(HttpServletRequest request ,String prefixName) throws Exception {
		String filePath = "";
		//获取文件需要上传到的路径
        String path = imageuploadpath + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = prefixName;
        String destPath = path + fileName;
        filePath = destPath;
		return filePath;
	}
	
	/**
	 * review customer own wishlist
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/myWishlist", method = { RequestMethod.GET, RequestMethod.POST }) // 获取用户信息
	public List<Wishlist> getMyWishlist(@Param("openId") String openId) {
		List<Wishlist> wishlists = new ArrayList<Wishlist>();
		if (StringUtils.isNotEmpty(openId)) {
			wishlists = customerWishlistService.getMyWishlist(openId);
			return wishlists;
		}
		return wishlists;
	}
	
	/**
	 * add product to my wishlist
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/saveWishlist", method = { RequestMethod.GET, RequestMethod.POST }) 
	public String saveWishlist(@Param("openId") String openId, @RequestParam("productCode") String productcode)
	{
		logger.info("The openId is ......"+openId);
		if (StringUtils.isNotEmpty(openId)) {
			customerWishlistService.saveWishlist(openId, productcode);
			return "success";
		}
		return "fail";
	}
	
	/**
	 * remove my wish list 
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/removeMywishlist", method = { RequestMethod.GET, RequestMethod.POST })
	public String getUploadProductsByUser(@Param("openId") String openId,
			@RequestParam("wishid") String wishid) {
		
		if (StringUtils.isNotEmpty(openId)) {
			customerWishlistService.removeWishlist(openId, wishid);
			return "success";
		}
		return "fail";
	}
}
