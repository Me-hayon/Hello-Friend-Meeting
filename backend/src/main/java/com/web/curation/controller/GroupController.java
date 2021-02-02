package com.web.curation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.Alarm;
import com.web.curation.model.entity.FriendInfo;
import com.web.curation.model.entity.GroupInfo;
import com.web.curation.model.entity.GroupParticipant;
import com.web.curation.model.entity.UserInfo;
import com.web.curation.model.repository.AlarmRepository;
import com.web.curation.model.repository.FriendInfoRepository;
import com.web.curation.model.repository.GroupInfoRepository;
import com.web.curation.model.repository.GroupParticipantRepository;
import com.web.curation.model.repository.UserInfoRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class GroupController {
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	GroupInfoRepository groupInfoRepository;
	
	@Autowired
	GroupParticipantRepository groupParticipantRepository;
	
	@Autowired
	AlarmRepository alarmRepository;
	
	@Autowired
	FriendInfoRepository friendInfoRepository;
	
	/*
	@PostMapping("/url")
	public Object writename(@RequestParam String param) {
		Map<String,Object> resultMap=new HashMap<>();
		
		return resultMap;
	}
	*/
	
	@PostMapping("/getGroupList")
	public Object getGroupList(@RequestParam String email) {
		Map<String,Object> resultMap=new HashMap<>();
		
		UserInfo userInfo=userInfoRepository.findByEmail(email);
		List<GroupParticipant> list=groupParticipantRepository.findAllByUno(userInfo.getUno());
		//in절 써라
		List<Integer> gnoList=new ArrayList<>();
		
		for(GroupParticipant gp:list) 
			gnoList.add(gp.getGno());
		
		List<GroupInfo> groupList=groupInfoRepository.findAllByGnoIn(gnoList);
		resultMap.put("groupList",groupList);
		
		return resultMap;
	}
	
	@PostMapping("/makeGroup")
	public Object makeGroup(@RequestParam String email,@RequestParam String gname,@RequestParam int gcategory,
			@RequestParam int gboundary) {
		Map<String,Object> resultMap=new HashMap<>();
		int gmaster=userInfoRepository.findByEmail(email).getUno();
		
		GroupInfo groupInfo=new GroupInfo();
		groupInfo.setGboundary(gboundary);
		groupInfo.setGcategory(gcategory);
		groupInfo.setGmaster(gmaster);
		groupInfo.setGname(gname);
		groupInfo.setGuserList(Integer.toString(gmaster));
		
		groupInfoRepository.save(groupInfo);
		
		resultMap.put("data","그룹 생성에 성공했습니다.");
		return resultMap;
	}
	
	@PostMapping("/delGroup")
	public Object writename(@RequestParam int gno) {
		Map<String,Object> resultMap=new HashMap<>();
		
		GroupInfo groupInfo=groupInfoRepository.findById(gno).get();
		groupInfoRepository.delete(groupInfo);
		
		resultMap.put("data","그룹 삭제에 성공했습니다.");
		
		
		return resultMap;
	}
	
	@PostMapping("/changeGroupMaster")
	public Object changeGroupMaster(@RequestParam int gno, @RequestParam int nextMaster) {
		Map<String,Object> resultMap=new HashMap<>();
		
		GroupInfo groupInfo=groupInfoRepository.findById(gno).get();
		groupInfo.setGmaster(nextMaster);
		groupInfoRepository.save(groupInfo);
		
		resultMap.put("data","그룹장 변경에 성공했습니다.");
		return resultMap;
	}
	
	@PostMapping("/inviteGroup")
	public Object inviteGroup(@RequestParam String email, @RequestParam int friendId, @RequestParam int gno) {
		Map<String,Object> resultMap=new HashMap<>();
		
		UserInfo myInfo=userInfoRepository.findByEmail(email);
		GroupInfo groupInfo=groupInfoRepository.findById(gno).get();
		Alarm alarm=new Alarm();
		
		StringBuilder sb=new StringBuilder();
		sb.append(myInfo.getUname());
		sb.append("님이 ");
		sb.append(groupInfo.getGname());
		sb.append("그룹으로 초대하셨습니다!");
		alarm.setAsummary(sb.toString());
		alarm.setAuser(friendId);
		alarm.setCreateUser(myInfo.getUno());
		alarm.setAtype(0);
		alarm.setAurl("#");
		alarmRepository.save(alarm);
		
		return resultMap;
	}
	
	@PostMapping("/acceptInviteGroup")
	public Object acceptInviteGroup(@RequestParam String email, @RequestParam int gno) {
		Map<String,Object> resultMap=new HashMap<>();
		
		UserInfo myInfo=userInfoRepository.findByEmail(email);
		
		GroupInfo groupInfo=groupInfoRepository.findById(gno).get();
		GroupParticipant groupParticipant=new GroupParticipant();
		groupParticipant.setGno(gno);
		groupParticipant.setUno(myInfo.getUno());
		groupParticipantRepository.save(groupParticipant); 
		
		List<GroupParticipant> list=groupParticipantRepository.findAllByGno(gno);
		StringBuilder sb=new StringBuilder();
		for(GroupParticipant gp:list) {
			sb.append(gp.getUno());
			sb.append(" ");
		}
		
		Optional<List<FriendInfo>> friendList=friendInfoRepository.findAllByMyId(myInfo.getUno());
		
		if(friendList.isPresent()) {
			StringBuilder sb2=new StringBuilder();
			sb2.append(myInfo.getUname());
			sb2.append("님이 ");
			sb2.append(groupInfo.getGname());
			sb2.append("그룹에 가입하셨습니다.");
			for(FriendInfo friendInfo:friendList.get()) {
				Optional<FriendInfo> fi=friendInfoRepository.findByMyIdAndFriendId(friendInfo.getFriendId(),myInfo.getUno());
				if(fi.isPresent()) {
					Alarm alarm=new Alarm();
					alarm.setAtype(1);
					alarm.setAurl("#");
					alarm.setAuser(fi.get().getMyId());
					alarm.setCreateUser(myInfo.getUno());
					
					alarm.setAsummary(sb2.toString());
					alarmRepository.save(alarm);
				}
			}
		}
		
		groupInfo.setGuserList(sb.toString());
		resultMap.put("data","그룹에 가입했습니다!");
		
		return resultMap;
	}
	
	@PostMapping("/joinGroup")
	public Object joinGroup(@RequestParam String email, @RequestParam int gno) {
		Map<String,Object> resultMap=new HashMap<>();
		
		//그룹 가입에대한 아이디어 필요. 초대,신청받았을때 알림페이지에서 처리할지
		
		resultMap.put("data","그룹에 가입 신청을 보냈습니다.");
		
		return resultMap;
	}
	
	@PostMapping("/acceptJoinGroup")
	public Object acceptJoinoinGroup(@RequestParam int uno, @RequestParam int gno) {
		Map<String,Object> resultMap=new HashMap<>();
		
		return resultMap;
	}
	
//	@PostMapping("/getoutGroup")
//	public Object getoutGroup(@RequestParam int gno, @RequestParam String email) {
//		Map<String,Object> resultMap=new HashMap<>();
//		
//		GroupInfo groupInfo=groupInfoRepository.findById(gno).get();
//		group
//		
//		return resultMap;
//	}
	
	
	
}
