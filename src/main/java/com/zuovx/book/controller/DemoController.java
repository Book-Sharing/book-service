package com.zuovx.book.controller;

import com.zuovx.book.annotation.LoginRequired;
import com.zuovx.book.annotation.OperatorLogController;
import com.zuovx.book.model.User;
import com.zuovx.book.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zuovx
 * @date 2019-10-10 15:57
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping("/getAllUser")
	@LoginRequired
	@OperatorLogController(describe = "获取所有用户")
	public List<User> getAllUser(){
		return demoService.getAllUser();
	}

	@OperatorLogController(describe = "通过名字获取用户")
	@RequestMapping(value = "/getUserByName",method = RequestMethod.GET)
	public List<User> getUserByName(@RequestParam(value = "name") String name){
		return demoService.getUserByName(name);
	}

	/**
	 * 测试service异常记录
	 * @return r
	 */
	@RequestMapping(value = "/test")
	public String testException(){
		try {
			return demoService.testException();
		}catch (Exception e){
			return "eeeee";
		}
	}
}
