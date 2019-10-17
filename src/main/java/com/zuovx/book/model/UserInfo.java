package com.zuovx.book.model;

import lombok.Data;

/**
 * @author zuoweixing
 * @date 2019-10-17 15:57
 */
@Data

public class UserInfo {
	public UserInfo(String account,int id){
		this.account = account;
		this.id = id;
	}
	private int id;
	private String account;
}
