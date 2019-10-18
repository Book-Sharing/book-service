package com.zuovx.book.utils;

import com.zuovx.book.config.AuthJwt;
import com.zuovx.book.model.UserInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static com.zuovx.book.utils.Constants.TOKEN;

/**
 * @author zuovx
 * @date 2019-10-18 10:55
 */
@Component
public class ApplicationUtils implements ApplicationContextAware {

	private final AuthJwt authJwt;

	private static ApplicationContext applicationContext = null;

	private static String jwt = "";

	@Autowired
	public ApplicationUtils(AuthJwt authJwt) {
		this.authJwt = authJwt;
	}


	public static ApplicationContext getApplicationContext(){
		return ApplicationUtils.applicationContext;
	}
	/**
	 * 根据类型获取Bean
	 *
	 * @param cls Bean类
	 * @param <T> Bean类型
	 * @return Bean对象
	 */
	public static <T> T getBean(Class<T> cls) {
		return applicationContext == null ? null : applicationContext.getBean(cls);
	}

	/**
	 * 根据名称获取Bean
	 *
	 * @param name Bean名称
	 * @return Bean对象
	 */
	public static Object getBean(String name) {
		return applicationContext == null ? null : applicationContext.getBean(name);
	}

	/**
	 * 根据Bean名称和类获取Bean对象
	 *
	 * @param name Bean名称
	 * @param cls Bean类
	 * @param <T> Bean类型
	 * @return Bean对象
	 */
	public static <T> T getBean(String name, Class<T> cls) {
		return applicationContext == null ? null : applicationContext.getBean(name, cls);
	}

	/**
	 * 只能用在request 上下文
	 * @return
	 */
	public static UserInfo getUserInfo() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token = request.getParameter(TOKEN);
		if (token == null || token.isEmpty()){
			for (Cookie cookie : request.getCookies()){
				if (TOKEN.equals(cookie.getName())){
					token = cookie.getValue();
				}
			}
		}
		Claims claims = TokenUtils.parseJwt(token,jwt);
		if (claims == null){
			return new UserInfo("tourists",0)	;
		}
		String userId = claims.get("userId").toString();
		String account = claims.get("account").toString();
		if (userId == null || account == null || userId.isEmpty() || account.isEmpty()){
			return new UserInfo("tourists",0);
		}
		return new UserInfo(account,Integer.valueOf(userId));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationUtils.applicationContext = applicationContext;
		ApplicationUtils.jwt = authJwt.getBase64Secret();
	}
}
