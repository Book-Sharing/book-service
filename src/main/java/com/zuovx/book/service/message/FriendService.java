package com.zuovx.book.service.message;

import com.zuovx.book.dao.FriendGroupMapper;
import com.zuovx.book.dao.FriendMapper;
import com.zuovx.book.dao.UserMapper;
import com.zuovx.book.model.Friend;
import com.zuovx.book.model.FriendExample;
import com.zuovx.book.model.UserInfo;
import com.zuovx.book.service.bus.DealResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zuovx
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

	/**
	 * 删除好友 逻辑删除
	 * @param userInfo 用户信息
	 * @param id 好友id
	 * @return 处理结果
	 */
	public DealResult deleteFriend(UserInfo userInfo,int id){
		DealResult dealResult = new DealResult();
		dealResult.setSucceed(false);
		FriendExample friendExample = new FriendExample();
		friendExample.createCriteria().andFriendUserIdEqualTo(id)
				.andUserIdEqualTo(userInfo.getId()).andIsDeletedEqualTo((byte) 0);
		List<Friend> friends = friendMapper.selectByExample(friendExample);
		if (friends == null || friends.size() < 1){
			dealResult.setMsg("好友不存在！");
			return dealResult;
		}
		Friend friend = friends.get(0);
		friend.setIsDeleted((byte) 1);
		if (1 == friendMapper.updateByPrimaryKeySelective(friend)){
			dealResult.setSucceed(true);
			return dealResult;
		}
		dealResult.setMsg("数据库操作失败！");
		return dealResult;
	}
}
