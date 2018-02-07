package com.wechat.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import com.wechat.backend.dao.ProductRepository;
import com.wechat.backend.dao.WishlistRepository;
import com.wechat.backend.data.WishlistQuery;
import com.wechat.backend.domain.Product;
import com.wechat.backend.domain.Wishlist;
import com.wechat.backend.service.CustomerWishlistService;

@Service
public class CustomerWishlistServiceImpl implements CustomerWishlistService{

	private static final Logger LOG = Logger.getLogger(CustomerWishlistServiceImpl.class);

	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * save one product in wishlist
	 * @param openId
	 * @param productcode
	 * @return
	 */
	public void saveWishlist(String openId, String productcode){
	
		List<Wishlist> wishes= this.findByOpenId(openId);
		Product product = productRepository.findByCode(Long.valueOf(productcode));
		if(wishes.isEmpty()){
			Wishlist wish = new Wishlist();
			wish.setOpenId(openId);
			wish.setPrice(product.getPrice());
			wish.setDescription(product.getDescription());
			wish.setProductCode(product.getCode());
			wish.setProductName(product.getName());
			wish.setSellerOpenId(product.getOpenId());
			wish.setImage(product.getImage());
			wishlistRepository.save(wish);
		}else{
			//check whether have collect
			WishlistQuery query = new WishlistQuery();
			query.setProductCode(productcode);
			query.setOpenId(openId);
			List<Wishlist> list = wishlistRepository.findAll(getSpec(query));
			if(!ListUtils.isEmpty(list)){
				Wishlist wish = list.get(0);
				wish.setOpenId(openId);
				wish.setPrice(product.getPrice());
				wish.setDescription(product.getDescription());
				wish.setProductCode(product.getCode());
				wish.setProductName(product.getName());
				wish.setSellerOpenId(product.getOpenId());
				wish.setImage(product.getImage());
				wishlistRepository.save(wish);
			}else{
				Wishlist wish = new Wishlist();
				wish.setOpenId(openId);
				wish.setPrice(product.getPrice());
				wish.setDescription(product.getDescription());
				wish.setProductCode(product.getCode());
				wish.setProductName(product.getName());
				wish.setSellerOpenId(product.getOpenId());
				wish.setImage(product.getImage());
				wishlistRepository.save(wish);
			}
			
		}
		
	}
	/**
	 * remove one wishlist from customer 
	 * @param wishId
	 * @return
	 */
	public void removeWishlist(String customerId,String wishId){
		List<Wishlist> wish = wishlistRepository.findAll(new Specification<Wishlist>() {
            @Override
            public Predicate toPredicate(Root<Wishlist> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("wishId"), Long.valueOf(wishId));
                return criteriaBuilder.and(predicate);
            }
        });
		if(!wish.isEmpty()){
			wishlistRepository.delete(wish.get(0));
		}
	}
	
	/**
	 * 
	 * @param openId
	 * @return
	 */
	public List<Wishlist> getMyWishlist(String openId){
		return this.findByOpenId(openId);
	}
	
	public List<Wishlist> findByOpenId(String opneId){
		List<Wishlist> wishes = wishlistRepository.findAll(new Specification<Wishlist>() {
            @Override
            public Predicate toPredicate(Root<Wishlist> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("openId"), opneId);
                return criteriaBuilder.and(predicate);
            }
        });
		return wishes;
	}
	
	public static Specification<Wishlist> getSpec(WishlistQuery wishquery) {
        return new Specification<Wishlist>() {
            @Override
            public Predicate toPredicate(Root<Wishlist> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                // build query condition
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(wishquery.getOpenId())) {
                    predicates.add(cb.equal(root.get("openId"), wishquery.getOpenId()));
                }
                if (StringUtils.isNotBlank(wishquery.getProductCode())) {
                    predicates.add(cb.equal(root.get("productCode"), wishquery.getProductCode()));
                }
                if (StringUtils.isNotBlank(wishquery.getSellerOpenId())) {
                    predicates.add(cb.equal(root.get("sellerOpenId"), wishquery.getSellerOpenId()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        };
    }
	
	
}
