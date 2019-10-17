package com.zuovx.book.service.message;

import com.zuovx.book.dao.FriendGroupMapper;
import com.zuovx.book.dao.FriendMapper;
import com.zuovx.book.dao.UserMapper;
import com.zuovx.book.model.Friend;
import com.zuovx.book.model.UserInfo;
import com.zuovx.book.service.bus.DealResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zuoweixing
 * @date 2019-10-17 15:54
 */
@Service
@Slf4j
public class FriendService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FriendMapper friendMapper;

	@Autowired
	private FriendGroupMapper friendGroupMapper;


	/**
	 * 添加好友
	 * @param userInfo 用户信息
	 * @param id 好友id
	 * @param groupId 好友分组id
	 * @return 添加结果
	 */
	public DealResult addFriendById(UserInfo userInfo,int id,int groupId,String remarks){

		DealResult dealResult = new DealResult();
		dealResult.setSucceed(false);
		if (!userMapper.checkUserIdExists(id)){
			dealResult.setMsg("用户不存在！");
			return dealResult;
		}
		if (friendMapper.checkFriendExists(userInfo.getId(),id)){
			dealResult.setMsg("对方已经是你等好友！");
			return dealResult;
		}
		if (!friendGroupMapper.checkGroupExists(userInfo.getId(),groupId)){
			dealResult.setMsg("分组不存在！");
			return dealResult;
		}
		Friend friend = new Friend();
		friend.setCreatedAt(new Date());
		friend.setFriendUserId(id);
		friend.setUserId(userInfo.getId());
		friend.setUpdateAt(new Date());
		friend.setGroupId(groupId);
		friend.setRemarks(remarks);
		if (1 == friendMapper.insertSelective(friend)){
			dealResult.setSucceed(true);
		}
		dealResult.setMsg("数据库操作失败！");
		return dealResult;
	}

	public DealResult deleteFriend(){
		return null;
	}
}
