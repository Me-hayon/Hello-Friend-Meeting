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
import com.web.curation.model.entity.GroupApply;
import com.web.curation.model.entity.GroupInfo;
import com.web.curation.model.entity.GroupParticipant;
import com.web.curation.model.entity.UserInfo;
import com.web.curation.model.repository.AlarmRepository;
import com.web.curation.model.repository.FriendInfoRepository;
import com.web.curation.model.repository.GroupApplyRepository;
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

	@Autowired
	GroupApplyRepository groupApplyRepository;

	@PostMapping("/getUserListInGroup")
	public Object getUserListInGroup(@RequestParam int gno) {
		Map<String, Object> resultMap = new HashMap<>();

		List<GroupParticipant> list = groupParticipantRepository.findAllByGno(gno);
		List<Integer> unoList = new ArrayList<>();
		for (GroupParticipant gp : list)
			unoList.add(gp.getUno());

		List<UserInfo> userList = userInfoRepository.findAllByUnoIn(unoList);

		resultMap.put("userList", userList);

		return resultMap;
	}

	@PostMapping("/getGroupList")
	public Object getGroupList(@RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo userInfo = userInfoRepository.findByEmail(email);
		List<GroupParticipant> list = groupParticipantRepository.findAllByUno(userInfo.getUno());

		List<Integer> gnoList = new ArrayList<>();

		for (GroupParticipant gp : list)
			gnoList.add(gp.getGno());

		List<GroupInfo> groupList = groupInfoRepository.findAllByGnoIn(gnoList);
		resultMap.put("groupList", groupList);

		return resultMap;
	}

	@PostMapping("/makeGroup")
	public Object makeGroup(@RequestParam String email, @RequestParam String gname, @RequestParam int gcategory,
			@RequestParam int gboundary) {
		Map<String, Object> resultMap = new HashMap<>();
		UserInfo myInfo=userInfoRepository.findByEmail(email);
		int gmaster = myInfo.getUno();

		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setGboundary(gboundary);
		groupInfo.setGcategory(gcategory);
		groupInfo.setGmaster(gmaster);
		groupInfo.setGname(gname);
		groupInfo.setGuserList(Integer.toString(gmaster));

		groupInfoRepository.save(groupInfo);

		switch (gboundary) {// 0:비공개(초대만), 1:친구까지 공개, 2:친구의친구까지 공개
		case 1:
			List<FriendInfo> friendList=getFriendList(gmaster);
			StringBuilder sb=new StringBuilder();
			sb.append("회원님의 친구 ");
			sb.append(myInfo.getUname());
			sb.append("님이 ");
			sb.append(gname);
			sb.append("그룹을 만들었습니다.");
			for(FriendInfo fi:friendList) {
				Alarm alarm=new Alarm();
				alarm.setAtype(1);
				alarm.setAurl("#");
				alarm.setAuser(fi.getMyId());
				alarm.setCreateUser(gmaster);
				alarm.setAsummary(sb.toString());
				
				alarmRepository.save(alarm);
			}
			break;
		case 2:
			//////////////
			//////////////
			//////////////
			//////////////
			//////////////
			//////////////찬규님과 봉현님의 친구인 기호님이 만들었습니다.
			//////////////
			//////////////
			break;
		}

		resultMap.put("data", "그룹 생성에 성공했습니다.");
		return resultMap;
	}

	@PostMapping("/delGroup")
	public Object delGroup(@RequestParam int gno) {
		Map<String, Object> resultMap = new HashMap<>();

		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();
		groupInfoRepository.delete(groupInfo);

		resultMap.put("data", "그룹 삭제에 성공했습니다.");

		return resultMap;
	}

	@PostMapping("/changeGroupMaster")
	public Object changeGroupMaster(@RequestParam int gno, @RequestParam int nextMaster) {
		Map<String, Object> resultMap = new HashMap<>();

		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();
		groupInfo.setGmaster(nextMaster);
		groupInfoRepository.save(groupInfo);

		resultMap.put("data", "그룹장 변경에 성공했습니다.");
		return resultMap;
	}

	@PostMapping("/inviteGroup")
	public Object inviteGroup(@RequestParam String email, @RequestParam int friendId, @RequestParam int gno) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo myInfo = userInfoRepository.findByEmail(email);
		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();
		Alarm alarm = new Alarm();

		StringBuilder sb = new StringBuilder();
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

		GroupApply groupApply = new GroupApply();
		groupApply.setAisApply(false);
		groupApply.setGno(groupInfo.getGno());
		groupApply.setUno(friendId);

		groupApplyRepository.save(groupApply);

		return resultMap;
	}

	@PostMapping("/acceptInviteGroup")
	public Object acceptInviteGroup(@RequestParam String email, @RequestParam int gno) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo myInfo = userInfoRepository.findByEmail(email);

		GroupApply groupApply = groupApplyRepository.findByUnoAndGno(myInfo.getUno(), gno).get();
		groupApplyRepository.delete(groupApply);

		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();
		GroupParticipant groupParticipant = new GroupParticipant();
		groupParticipant.setGno(gno);
		groupParticipant.setUno(myInfo.getUno());
		groupParticipantRepository.save(groupParticipant);

		List<GroupParticipant> list = groupParticipantRepository.findAllByGno(gno);
		StringBuilder sb = new StringBuilder();
		for (GroupParticipant gp : list) {
			sb.append(gp.getUno());
			sb.append(" ");
		}
		
		List<FriendInfo> friendList=getFriendList(myInfo.getUno());
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append(myInfo.getUname());
		sb2.append("님이 ");
		sb2.append(groupInfo.getGname());
		sb2.append("그룹에 가입하셨습니다.");
		for(FriendInfo fi:friendList) {
			Alarm alarm = new Alarm();
			alarm.setAtype(1);
			alarm.setAurl("#");
			alarm.setAuser(fi.getMyId());
			alarm.setCreateUser(myInfo.getUno());

			alarm.setAsummary(sb2.toString());
			alarmRepository.save(alarm);
		}

		groupInfo.setGuserList(sb.toString());
		resultMap.put("data", "그룹에 가입했습니다!");

		return resultMap;
	}

	@PostMapping("/applyGroup")
	public Object joinGroup(@RequestParam String email, @RequestParam int gno) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo myInfo = userInfoRepository.findByEmail(email);

		GroupApply groupApply = new GroupApply();
		groupApply.setAisApply(true);
		groupApply.setGno(gno);
		groupApply.setUno(myInfo.getUno());

		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();

		Alarm alarm = new Alarm();
		alarm.setAtype(0);
		alarm.setAurl("#");
		alarm.setAuser(groupInfo.getGmaster());
		alarm.setCreateUser(myInfo.getUno());

		StringBuilder sb = new StringBuilder();
		sb.append(myInfo.getUname());
		sb.append("님이 ");
		sb.append(groupInfo.getGname());
		sb.append("그룹에 가입을 신청했습니다.");
		alarm.setAsummary(sb.toString());
		
		alarmRepository.save(alarm);

		resultMap.put("data", "그룹에 가입 신청을 보냈습니다.");

		return resultMap;
	}

	@PostMapping("/acceptApplyGroup")
	public Object acceptJoinoinGroup(@RequestParam int uno, @RequestParam int gno) {
		Map<String, Object> resultMap = new HashMap<>();

		GroupApply groupApply = groupApplyRepository.findByUnoAndGno(uno, gno).get();
		groupApplyRepository.delete(groupApply);

		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();

		GroupParticipant groupParticipant = new GroupParticipant();
		groupParticipant.setGno(gno);
		groupParticipant.setUno(uno);
		groupParticipantRepository.save(groupParticipant);

		List<GroupParticipant> gpList = groupParticipantRepository.findAllByGno(gno);

		StringBuilder sb = new StringBuilder();
		for (GroupParticipant gp : gpList) {
			sb.append(gp.getUno());
			sb.append(" ");
		}
		groupInfo.setGuserList(sb.toString());
		groupInfoRepository.save(groupInfo);

		resultMap.put("data", "가입 요청을 승인했습니다.");

		return resultMap;
	}

	@PostMapping("/getoutGroup")
	public Object getoutGroup(@RequestParam int gno, @RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo myInfo = userInfoRepository.findByEmail(email);
		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();

		GroupParticipant groupParticipant = groupParticipantRepository.findByUnoAndGno(myInfo.getUno(), gno);
		groupParticipantRepository.delete(groupParticipant);

		List<GroupParticipant> gpList = groupParticipantRepository.findAllByGno(gno);

		StringBuilder sb = new StringBuilder();
		for (GroupParticipant gp : gpList) {
			sb.append(gp.getUno());
			sb.append(" ");
		}
		groupInfo.setGuserList(sb.toString());
		groupInfoRepository.save(groupInfo);

		resultMap.put("data", "그룹에서 탈퇴했습니다.");

		return resultMap;
	}

	@PostMapping("/banishGroup")
	public Object banishGroup(@RequestParam int gno, @RequestParam int uno) {
		Map<String, Object> resultMap = new HashMap<>();

		GroupInfo groupInfo = groupInfoRepository.findById(gno).get();

		GroupParticipant groupParticipant = groupParticipantRepository.findByUnoAndGno(uno, gno);
		groupParticipantRepository.delete(groupParticipant);

		List<GroupParticipant> gpList = groupParticipantRepository.findAllByGno(gno);

		StringBuilder sb = new StringBuilder();
		for (GroupParticipant gp : gpList) {
			sb.append(gp.getUno());
			sb.append(" ");
		}
		groupInfo.setGuserList(sb.toString());
		groupInfoRepository.save(groupInfo);

		resultMap.put("data", "그룹에서 추방했습니다.");

		return resultMap;
	}

	public List<FriendInfo> getFriendList(int uno){
		Optional<List<FriendInfo>> friendList = friendInfoRepository.findAllByMyId(uno);

		List<FriendInfo> toReturnFriendList=new ArrayList<>();
		if (friendList.isPresent()) {
			for (FriendInfo friendInfo : friendList.get()) {
				Optional<FriendInfo> fi = friendInfoRepository.findByMyIdAndFriendId(friendInfo.getFriendId(),
						uno);
				if (fi.isPresent()) 
					toReturnFriendList.add(fi.get());
				
			}
		}
		
		return toReturnFriendList;
	}
	
}
