package com.wechat.backend.service;

public interface WechatService {

	void selectUserByOpenId(String openid, String headurl, String nickname, String sex, String country,  
            String province, String city);
}
