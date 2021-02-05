package com.web.curation.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.config.JwtService;
import com.web.curation.config.SensService;
import com.web.curation.model.entity.UserInfo;
import com.web.curation.model.repository.UserInfoRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class UserInfoController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private SensService sensService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	public static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	/////////////////////////////////// 회원가입

	// 이메일 중복 확인 체크 버튼을 눌렀을 경우
	@PostMapping("/dckEmail")
	public boolean dckEmail(@RequestParam("email") String email) {

		boolean result = true;
		UserInfo userInfo = userInfoRepository.findByEmail(email);

		if (userInfo == null) {
			System.out.println();
			result = false; // 중복이 아님
		} else {
			result = true; // 중복임
		}

		return result;
	}

	// 나중에 SENS API 사용한 인증으로 바꿔야 함
	// 전화 중복 확인 체크 버튼을 눌렀을 경우
	@PostMapping("/authTel")
	public Object authTel(@RequestParam("phone") String tel, HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<>();
		UserInfo userInfo = null;
		if(userInfoRepository.findByTel(tel).isPresent())
			userInfo=userInfoRepository.findByTel(tel).get();

		if (userInfo == null) { // 중복이 아님
			sensService.makeBody(tel);
			System.out.println(tel + "에 문자 전송");
			resultMap.put("is-success", true);
			return resultMap;
		} else { // 중복임
			System.out.println("ㅡㅡ짱나");
			resultMap.put("is-success", false);
			return resultMap;
		}
	}

	@PostMapping("/join")
	public UserInfo join(@RequestBody UserInfo userInfo) {

		userInfoRepository.save(userInfo);
		return userInfo;
	}

	/////////////////////////////////// 로그인

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password,
			HttpServletResponse response, HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserInfo loginUser = userInfoRepository.findByEmailAndPassword(email, password);

			if (loginUser != null) { // 로그인 성공 시 토큰 생성
				String token = jwtService.create(loginUser);
				logger.trace("로그인 토큰정보 : {}", token);

				resultMap.put("auth-token", token);
				resultMap.put("user-email", loginUser.getEmail());
				resultMap.put("user-name", loginUser.getUname());
				resultMap.put("is-success", true);
				resultMap.put("status", true);
				resultMap.put("data", loginUser);
				status = HttpStatus.ACCEPTED;
				System.out.println(jwtService.get(token));
			} else { // 로그인 실패
				resultMap.put("message", "로그인 실패");
				resultMap.put("is-success", false);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(email + " / " + password);

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/////////////////////////////////// 프로필

	@PostMapping("/profile")
	public Object getUser(@RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo user = userInfoRepository.findByEmail(email);
		if (user != null) {
			resultMap.put("user-tel", user.getTel());
			resultMap.put("user-name", user.getUname());
			resultMap.put("user-email", user.getEmail());
			resultMap.put("profile-img", user.getUprofileImg());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} else {
			resultMap.put("user-tel", "앗! 전화번호를 불러올 수 없어요.");
			resultMap.put("user-name", "앗! 이름을 불러올 수 없어요.");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
	}
	
	@PostMapping("/profileByTel")
	public Object getUserByTel(@RequestParam String tel) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo user = null;
		if(userInfoRepository.findByTel(tel).isPresent()) {
			user=userInfoRepository.findByTel(tel).get();
			resultMap.put("isPresent",true);
		}
		else {
			resultMap.put("data","해당하는 사용자가 없습니다.");
			resultMap.put("isPresent",false);
			return resultMap;
		}
		resultMap.put("data",user);
		return resultMap;
		
	}
	
	@PostMapping("/findEmailByUno")
	public Object findEmailByUno(@RequestParam int uno) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo user = null;
		if(userInfoRepository.findById(uno).isPresent()) {
			user=userInfoRepository.findById(uno).get();
			resultMap.put("isPresent",true);
		}
		else {
			resultMap.put("data","해당하는 사용자가 없습니다.");
			resultMap.put("isPresent",false);
			return resultMap;
		}
		resultMap.put("data",user.getEmail());
		return resultMap;
		
	}

	@PostMapping("/findUnameByUno")
	public Object findUnameByUno(@RequestParam int uno) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo user = null;
		if(userInfoRepository.findById(uno).isPresent()) {
			user=userInfoRepository.findById(uno).get();
			resultMap.put("isPresent",true);
		}
		else {
			resultMap.put("data","해당하는 사용자가 없습니다.");
			resultMap.put("isPresent",false);
			return resultMap;
		}
		resultMap.put("data",user.getUname());
		return resultMap;
		
	}
	
	/////////////////////////////////// 비밀번호 변경

	@PostMapping("/modify")
	public Object modifyPassword(@RequestParam String email, HttpServletResponse response,
			@RequestParam String oldPassword, @RequestParam String newPassword) {
		Map<String, Object> resultMap = new HashMap<>();
		System.out.println("oldPassword: " + oldPassword);
		System.out.println("newPassword: " + newPassword);

		UserInfo user = userInfoRepository.findByEmail(email);
		if (user.getPassword().equals(oldPassword)) { // 비밀번호 일치
			System.out.println("getPassword(): " + user.getPassword());
			user.setPassword(newPassword);
			userInfoRepository.save(user);

			String token = jwtService.create(user);
			resultMap.put("auth-token", token);
			resultMap.put("is-success", true);

			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

		} else { // 불일치
			resultMap.put("is-success", false);

			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
	}

	/////////////////////////////////// 회원 탈퇴

	@Transactional
	@PostMapping("/delete")
	public Object deleteUser(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<>();

		UserInfo user = userInfoRepository.findByEmailAndPassword(email, password);
		System.out.println(user);
		if (user != null) { // 비밀번호 일치
			userInfoRepository.deleteByEmail(email);
			resultMap.put("is-success", true);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} else { // 불일치
			resultMap.put("is-success", false);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
	}
	
	@PutMapping("/changeAvatar")
	public Object changeAvatar(@RequestParam String email, @RequestParam String profileImg) {
		Map<String, Object> resultMap = new HashMap<>();
		UserInfo user = userInfoRepository.findByEmail(email);
		
		System.out.println(email);
		System.out.println(profileImg);
		
		if (user != null) {
			user.setUprofileImg(profileImg);
			userInfoRepository.save(user);
			resultMap.put("is-success", true);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		
		resultMap.put("is-success", false);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
}
