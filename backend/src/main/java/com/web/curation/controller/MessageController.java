package com.web.curation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.Message;
import com.web.curation.model.repository.MessageRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class MessageController {
	@Autowired
	MessageRepository messageRepository;
	
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody Message message) {		
		messageRepository.save(message);
	}
	
	@PostMapping("/delMessage")
	public Object delMessage(@RequestParam int mno) {
		Map<String, Object> resultMap = new HashMap<>();
		
		messageRepository.deleteById(mno);
		resultMap.put("data", true);
		return resultMap;
	}
	
}
