package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.Message;
import com.web.curation.model.repository.MessageRepository;
import com.web.curation.model.repository.UserInfoRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class MessageController {
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestParam String email,@RequestParam String friendEmail,@RequestParam String mtitle,@RequestParam String mcontent) {		
		Message message=new Message();
		message.setMsender(userInfoRepository.findByEmail(email).getUno());
		message.setMreceiver(userInfoRepository.findByEmail(friendEmail).getUno());
		message.setMtitle(mtitle);
		message.setMcontent(mcontent);
		
		messageRepository.save(message);
	}
	
	@PostMapping("/delMessage")
	public Object delMessage(@RequestParam int mno) {
		Map<String, Object> resultMap = new HashMap<>();
		
		messageRepository.deleteById(mno);
		resultMap.put("data", true);
		return resultMap;
	}
	
	@PostMapping("/getMessages")
	public List<Message> getMessage(@RequestParam String email){
		int uno=userInfoRepository.findByEmail(email).getUno();
		List<Message> list=messageRepository.findAllByMreceiver(uno,Sort.by("mno").descending());
		return list;
	}
	
}
