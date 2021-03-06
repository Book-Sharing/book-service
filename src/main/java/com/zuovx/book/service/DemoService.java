package com.zuovx.book.service;

import com.zuovx.book.annotation.ServiceExceptionLog;
import com.zuovx.book.dao.UserMapper;
import com.zuovx.book.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zuovx
 * @date 2019-10-10 15:55
 */
@Service
public class DemoService {

	@Autowired
	private UserMapper userMapper;

	public List<User> getAllUser(){
		return userMapper.selectByExample(null);
	}

	public List<User> getUserByName(String name){
		return userMapper.selectByName(name);
	}

	@ServiceExceptionLog(describe = "testttttt")
	public String testException() throws Exception{

		throw new Exception("heihie");

//		return "test";
	}
}
