package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.GroupChatting;
import com.web.curation.model.repository.GroupChattingRepository;
import com.web.curation.model.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
	
	@Autowired
	private UserInfoRepository userRepo;
	
	@Autowired
	private final GroupChattingRepository chatRepo;
	
	private final SimpMessagingTemplate template;
	
	@MessageMapping("/chat")
	public void sendMessage(@Payload GroupChatting chat) {
		log.info("전달 메세지 : " + chat);
		
		chatRepo.save(chat);
		template.convertAndSend("/sub/" + chat.getGcgno(), chat);
	}
	
	@GetMapping("/getChat/{gcgno}")
	public ResponseEntity<List<GroupChatting>> getChat(@PathVariable(name="gcgno") int gcgno) {
		List<GroupChatting> list = chatRepo.findAllByGcgno(gcgno);
		System.out.println(list);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
