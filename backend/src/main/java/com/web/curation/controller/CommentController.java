package com.web.curation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.Comment;
import com.web.curation.model.repository.CommentRepository;
import com.web.curation.model.repository.UserInfoRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class CommentController {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@PostMapping("/getCommentList")
	public Object getCommentList(@RequestParam int bno) {
		Map<String, Object> resultMap=new HashMap<>();
		List<Comment> list=commentRepository.findAllByBno(bno);
		
		List<String> writerList=new ArrayList<>();
		for(Comment comment:list) 
			writerList.add(userInfoRepository.findById(comment.getCwriter()).get().getUname());
		
		resultMap.put("comments",list);
		resultMap.put("writerList", writerList);
		return resultMap;
	}
	
	@PostMapping("/writeComment")
	public Object writeComment(@RequestParam String email, @RequestParam String ccontent, @RequestParam int bno) {
		Map<String, Object> resultMap=new HashMap<>();
		
		Comment comment=new Comment();
		comment.setBno(bno);
		comment.setCcontent(ccontent);
		comment.setCwriter(userInfoRepository.findByEmail(email).getUno());
		commentRepository.save(comment);
		
		resultMap.put("data","댓글 작성에 성공했습니다.");
		
		return resultMap;
	}

	@PostMapping("/modifyComment")
	public Object modifyComment(@RequestParam int cno, @RequestParam String ccontent) {
		Map<String, Object> resultMap=new HashMap<>();
		Comment comment=commentRepository.findById(cno).get();
		comment.setCcontent(ccontent);
		
		commentRepository.save(comment);
		resultMap.put("data","댓글 수정에 성공했습니다.");
		
		return resultMap;
	}
	
	@PostMapping("/delComment")
	public Object delComment(@RequestParam int cno) {
		Map<String, Object> resultMap=new HashMap<>();
		
		Comment comment=commentRepository.findById(cno).get();
		commentRepository.delete(comment);
		resultMap.put("data","댓글 삭제에 성공했습니다.");
		
		return resultMap;
	}
}
