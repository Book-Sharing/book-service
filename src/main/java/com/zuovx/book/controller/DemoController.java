package com.zuovx.book.controller;

import com.zuovx.book.model.User;
import com.zuovx.book.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zuoweixing@guazi.com
 * @date 2019-10-10 15:57
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping("/getAllUser")
	public List<User> getAllUser(){
		return demoService.getAllUser();
	}
}
