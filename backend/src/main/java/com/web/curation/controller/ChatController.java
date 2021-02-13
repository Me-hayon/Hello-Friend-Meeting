package com.web.curation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.GroupChatting;
import com.web.curation.model.repository.UserInfoRepository;

@RestController
public class ChatController {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@MessageMapping("/receive")
	@SendTo("/send")
	public GroupChatting SocketHandler(GroupChatting chatting) {
		// String userName = userInfoRepository.findByUno(chatting.getGcuno()).getUname();
		int gcno = chatting.getGcno();
		int gcgno = chatting.getGcgno();
		int gcuno = chatting.getGcuno();
		String gccontent = chatting.getGccontent();
		String gcdate = chatting.getGcdate();
		
		GroupChatting result = new GroupChatting(gcno, gcgno, gcuno, gccontent, gcdate);
		
		
		return result;
	}
	
}
