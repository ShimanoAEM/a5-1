package com.wechat.backend.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wechat.backend.service.CustomerService;
import com.wechat.backend.utils.CommonUtil;

import net.sf.json.JSONObject;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	private static final String OAUTH_STRING = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	@Value("${wechat.appId}")
	private String appId;
	
	@Value("${wechat.appSecret}")
	private String appSecret;
	
	@Override
	public String getOpenId(String jsCode) {
		String openId = null;
		if (StringUtils.isNoneBlank(jsCode)) {
			String requestUrl = OAUTH_STRING.replace("APPID", appId)
					.replace("SECRET", appSecret).replace("JSCODE", jsCode);
			JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
			openId = (jsonObject != null) ? jsonObject.getString("openid") : StringUtils.EMPTY;
		}
		return openId;
	}

}
