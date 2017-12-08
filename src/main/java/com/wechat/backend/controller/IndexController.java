package com.wechat.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String getHome(HttpServletRequest request) {
		return "index";
	}
}