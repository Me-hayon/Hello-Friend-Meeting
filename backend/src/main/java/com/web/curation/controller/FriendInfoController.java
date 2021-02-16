package com.web.curation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.entity.Alarm;
import com.web.curation.model.entity.FriendInfo;
import com.web.curation.model.entity.Timeline;
import com.web.curation.model.entity.UserInfo;
import com.web.curation.model.repository.AlarmRepository;
import com.web.curation.model.repository.FriendInfoRepository;
import com.web.curation.model.repository.TimelineRepository;
import com.web.curation.model.repository.UserInfoRepository;

@RestController
@CrossOrigin("*")
public class FriendInfoController {

	@Autowired
	FriendInfoRepository friendInfoRepository;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	AlarmRepository alarmRepository;

	@Autowired
	TimelineRepository timelineRepository;
	
	@PostMapping("/isFriend")
	public int isFriend(@RequestBody Map<String, String> map) {
		int myId = Integer.parseInt(map.get("myUno"));
		int friendId = Integer.parseInt(map.get("friendUno"));
		
		Optional<FriendInfo> friendInfoOpt1 = friendInfoRepository.findFriendInfoByMyIdAndFriendId(myId, friendId);
		Optional<FriendInfo> friendInfoOpt2 = friendInfoRepository.findFriendInfoByMyIdAndFriendId(friendId, myId);

		int friendStatus = 0;
		
		if (friendInfoOpt1.isPresent())
			friendStatus++;
		
		if (friendInfoOpt2.isPresent())
			friendStatus += 2;

		// 0 : 친구가 아닌경우
		// 1 : 친구요청 보냈지만 아직 수락 못받은 경우
		// 2 : 친구요청을 받았지만 아직 수락하지 않은 경우
		// 3 : 친구인 경우
		
		return friendStatus;
	}

	@PostMapping("/addFriendByTel")
	public Object addFriend(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		int myId = Integer.parseInt(map.get("myUno"));
		int friendId = Integer.parseInt(map.get("friendUno"));
		
		Optional<FriendInfo> optFriendInfoMe = friendInfoRepository.findFriendInfoByMyIdAndFriendId(myId, friendId);
		Optional<FriendInfo> optFriendInfoFriend = friendInfoRepository.findFriendInfoByMyIdAndFriendId(friendId, myId);

		if (optFriendInfoMe.isPresent() && optFriendInfoFriend.isPresent()) {
			resultMap.put("is-success", 3);
		} 
		else if(optFriendInfoMe.isPresent()) {
			resultMap.put("is-success", 1);
		}
		else if(optFriendInfoFriend.isPresent()) {
			resultMap.put("is-success", 2);
		}
		else {
			Optional<UserInfo> optMyInfo = userInfoRepository.findById(myId);
			
			if(optMyInfo.isPresent()) {
				UserInfo myInfo = optMyInfo.get();
				FriendInfo friendInfo = new FriendInfo();
				friendInfo.setMyId(myId);
				friendInfo.setFriendId(friendId);
				
				friendInfoRepository.save(friendInfo);
				
				StringBuilder sb = new StringBuilder();
				sb.append(myInfo.getUname());
				sb.append("님이 친구 요청을 보냈습니다.");
				
				Alarm alarm = new Alarm();
				alarm.setAsummary(sb.toString());
				alarm.setAtype(0);
				alarm.setAurl("FriendInfo");
				alarm.setAuser(friendId);
				alarm.setCreateUser(myInfo.getUno());
				alarm.setAurlNo(myInfo.getUno());
				
				alarmRepository.save(alarm);
				resultMap.put("is-success", 0);
			}
		}
		
		return resultMap;
	}

	@PostMapping("/acceptFriend")
	public Object acceptFriend(@RequestParam(required = true) final String myEmail,
			@RequestParam(required = true) final String friendEmail) {
		
		UserInfo myInfo=userInfoRepository.findByEmail(myEmail);
		UserInfo friendInfo=userInfoRepository.findByEmail(friendEmail);
		int myId = myInfo.getUno();
		int friendId=friendInfo.getUno();
		final BasicResponse result = new BasicResponse();
		result.status = true;

		FriendInfo fi = new FriendInfo();
		fi.setMyId(myId);
		fi.setFriendId(friendId);
		friendInfoRepository.save(fi);
		result.data = "친구 요청을 수락했습니다.";
		
		
		Timeline timeline=new Timeline();
		timeline.setTcontent("친구");
		timeline.setTcontentSecond(friendInfo.getUname());
		timeline.setUno(myInfo.getUno());
		timelineRepository.save(timeline);
		
		
		timeline=new Timeline();
		timeline.setTcontent("친구");
		timeline.setTcontentSecond(myInfo.getUname());		
		timeline.setUno(friendInfo.getUno());
		timelineRepository.save(timeline);

		ResponseEntity response = null;
		response = new ResponseEntity<>(result, HttpStatus.OK);

		return response;
	}

	@PostMapping("/denyFriend")
	public Object denyFriend(@RequestParam(required = true) final String myEmail,
			@RequestParam(required = true) final String friendEmail) {
		int myId = userInfoRepository.findByEmail(myEmail).getUno();
		int friendId=userInfoRepository.findByEmail(friendEmail).getUno();
		final BasicResponse result = new BasicResponse();
		result.status = true;

		FriendInfo fi = friendInfoRepository.findByMyIdAndFriendId(friendId, myId).get();
		friendInfoRepository.delete(fi);
		result.data = "친구 요청을 거절했습니다.";

		ResponseEntity response = null;
		response = new ResponseEntity<>(result, HttpStatus.OK);

		return response;
	}

	@PostMapping("/deleteFriend")
	public Object deleteFriend(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		int myId = Integer.parseInt(map.get("myUno"));
		int friendId = Integer.parseInt(map.get("friendUno"));
		
		Optional<FriendInfo> optFriendInfo = friendInfoRepository.findByMyIdAndFriendId(myId, friendId);
		
		if(optFriendInfo.isPresent()) {
			friendInfoRepository.delete(optFriendInfo.get());
			optFriendInfo = friendInfoRepository.findByMyIdAndFriendId(friendId, myId);
			
			if(optFriendInfo.isPresent()) {
				friendInfoRepository.delete(optFriendInfo.get());
				resultMap.put("is-success", true);
			}
			else resultMap.put("is-success", false);
		}
		else resultMap.put("is-success", false);
		
		return resultMap;
	}
	
	@PostMapping("/cancelRequest")
	public Object cancelRequest(@RequestParam(required = true) final String myEmail,
			@RequestParam(required = true) final String friendEmail) {
		int myId=userInfoRepository.findByEmail(myEmail).getUno();
		int friendId=userInfoRepository.findByEmail(friendEmail).getUno();
		
		Optional<FriendInfo> fi=friendInfoRepository.findByMyIdAndFriendId(myId, friendId);
		System.out.println(fi.get());
		friendInfoRepository.delete(fi.get());
		
		final BasicResponse result=new BasicResponse();
		result.status=true;
		result.data="친구 요청을 삭제했습니다.";
		
		return result;
	}
	
	@PostMapping("/getFriendList")
	public Object findFriendList(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		
		int myId = userInfoRepository.findByEmail(map.get("email")).getUno();
		Optional<List<FriendInfo>> optFriendList = friendInfoRepository.findAllByMyId(myId);
	
		if(optFriendList.isPresent()) {	// 친구 기록이 있을 경우
			List<FriendInfo> friendListTmp = optFriendList.get();
			List<UserInfo> friendList = new ArrayList<>();
			List<UserInfo> favoriteFriendList = new ArrayList<>();
			
			for(FriendInfo friend: friendListTmp) {
				Optional<FriendInfo> optFriendInfo = friendInfoRepository.findByMyIdAndFriendId(friend.getFriendId(),myId);
				
				if(optFriendInfo.isPresent()) {	// 해당 친구가 나를 친구 수락한 경우
					UserInfo friendInfo = userInfoRepository.findById(friend.getFriendId()).get();
					friendList.add(friendInfo);
					if(friend.isFavorite()) favoriteFriendList.add(friendInfo);
				}
			}
			
			if(friendList.size()!=0) {	// 친구가 한명이라도 존재할 경우
				resultMap.put("friendList", friendList);
				resultMap.put("favoriteFriendList", favoriteFriendList);
				resultMap.put("is-success", true);
			}
			else {	// 친구가 존재하지 않을 경우
				resultMap.put("is-success", false);
			}
		}
		else {	// 친구 기록이 없을 경우
			resultMap.put("is-success", false);
		}
		
		return resultMap;
	}
	
	@PutMapping("/favoriteChange")
	public Object favoriteChange(@RequestBody Map<String, String> map) {
		Map<String, Object> resultMap = new HashMap<>();
		UserInfo user = userInfoRepository.findByEmail(map.get("email"));
		
		if(user!=null) {
			Optional<FriendInfo> optFriendInfo = friendInfoRepository.findByMyIdAndFriendId(user.getUno(), Integer.parseInt(map.get("friendUno")));
			
			if(optFriendInfo.isPresent()) {
				FriendInfo friendInfo = optFriendInfo.get();
				friendInfo.setFavorite(Boolean.parseBoolean(map.get("isFavorite")));
				friendInfoRepository.save(friendInfo);
				resultMap.put("is-success", true);
			}
			else resultMap.put("is-success", false);
		}
		else resultMap.put("is-success", false);
		
		return resultMap;
	}
}
