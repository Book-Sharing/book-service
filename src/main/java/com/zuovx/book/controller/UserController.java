package com.zuovx.book.controller;

import com.alibaba.fastjson.JSON;
import com.zuovx.book.annotation.LoginRequired;
import com.zuovx.book.config.AuthJwt;
import com.zuovx.book.model.User;
import com.zuovx.book.service.bus.DealResult;
import com.zuovx.book.service.bus.JsonResult;
import com.zuovx.book.service.user.UserService;
import com.zuovx.book.utils.Constants;
import com.zuovx.book.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author zuoweixing@guazi.com
 * @date 2019-10-11 14:27
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthJwt authJwt;

	/**
	 * 注册，成功后生成token返回到cookie中
	 * @return
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public JsonResult register(HttpServletResponse response, @RequestBody String requestBody){
		JsonResult jsonResult = new JsonResult();
		try {
			User user = JSON.parseObject(requestBody,User.class);
			DealResult dealResult = userService.register(user);
			if (dealResult.isSucceed()){
				jsonResult.setStatus(200);
				Cookie cookie = new Cookie(Constants.TOKEN,URLEncoder.encode(dealResult.getMsg(),"utf-8"));
				cookie.setPath("/");
				response.addCookie(cookie);
				jsonResult.setMsg("注册成功");
			}else {
				jsonResult.setMsg(dealResult.getMsg());
				jsonResult.setStatus(400);
			}
		}catch (Exception e){
			jsonResult.setMsg(e.getMessage());
			jsonResult.setStatus(400);
			log.info(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public JsonResult login(HttpServletResponse response, @RequestBody String requestBody){
		JsonResult jsonResult = new JsonResult();
		try {
			User user = JSON.parseObject(requestBody,User.class);
			DealResult dealResult = userService.login(user);
			if (dealResult.isSucceed()){
				jsonResult.setStatus(200);
				log.info(dealResult.getMsg());
				log.info(URLEncoder.encode(dealResult.getMsg(),"utf-8")+"======");
				Cookie cookie = new Cookie(Constants.TOKEN, URLEncoder.encode(dealResult.getMsg(),"utf-8"));
				cookie.setPath("/");
				response.addCookie(cookie);
				jsonResult.setMsg("登陆成功");
			}else {
				jsonResult.setMsg(dealResult.getMsg());
				jsonResult.setStatus(400);
			}
		}catch (Exception e){
			jsonResult.setMsg(e.getMessage());
			jsonResult.setStatus(400);
			log.info(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 登出
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	@LoginRequired
	public JsonResult logout(HttpServletResponse response,HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(200);
		Cookie cookie = CookieUtils.get(request,Constants.TOKEN);
		if (cookie != null){
			CookieUtils.setResponse(response,cookie.getName(),null);
		}
		return jsonResult;
	}
}
