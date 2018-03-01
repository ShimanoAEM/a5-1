package com.wechat.backend.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.backend.domain.Collector;

@Repository
@Transactional
public interface WechatCollectorRepository extends CrudRepository<Collector, String>,JpaSpecificationExecutor<Collector>{

	@Query(value = "select * from wechatcollector where open_id=?1", nativeQuery = true)
	public Collector findByOpenId(String openId);
	
}
