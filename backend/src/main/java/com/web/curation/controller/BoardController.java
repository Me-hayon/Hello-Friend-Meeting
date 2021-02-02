package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.entity.Board;
import com.web.curation.model.repository.BoardRepository;
import com.web.curation.model.repository.UserInfoRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class BoardController {

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	UserInfoRepository userInfoRepository;

	@PostMapping("/boardtest")
	public Object getBoardList(@RequestParam int bgno) {
		Map<String, Object> resultMap = new HashMap<>();

		List<Board> notice = boardRepository.findAllByBgnoAndBisNotice(bgno, true, Sort.by("bno").descending());
		List<Board> notNotice = boardRepository.findAllByBgnoAndBisNotice(bgno, false, Sort.by("bno").descending());
		resultMap.put("notice", notice);
		resultMap.put("notNotice", notNotice);

		return resultMap;
	}

	@PostMapping("/boardDetail")
	public Object boardDetail(@RequestParam int bno) {
		Map<String,Object> resultMap=new HashMap<>();
		Board board = boardRepository.findById(bno).get();
		resultMap.put("curBoard",board);
		
		return resultMap;
	}
	
	@PostMapping("/writeBoard")
	public Object writeBoard(@RequestParam String email, @RequestParam int bgno, @RequestParam String title,
			@RequestParam String content, @RequestParam boolean bisNotice) {
		Map<String, Object> resultMap = new HashMap<>();

		Board board = new Board();
		board.setBwriter(userInfoRepository.findByEmail(email).getUno());
		board.setBgno(bgno);
		board.setBtitle(title);
		board.setBcontent(content);
		board.setBisNotice(bisNotice);

		boardRepository.save(board);

		resultMap.put("data", "글 작성에 성공했습니다.");
		return resultMap;
	}

	@PostMapping("/modifyBoard")
	public Object modifyBoard(@RequestParam int bno, @RequestParam String title, @RequestParam String content) {
		Map<String, Object> resultMap = new HashMap<>();

		Board board = boardRepository.findById(bno).get();
		board.setBtitle(title);
		board.setBcontent(content);

		boardRepository.save(board);

		resultMap.put("data", "글 수정에 성공했습니다.");
		return resultMap;
	}
	
	@PostMapping("/delBoard")
	public Object delBoard(@RequestParam int bno) {
		Map<String,Object> resultMap=new HashMap<>();
		Board board = boardRepository.findById(bno).get();
		boardRepository.delete(board);
		resultMap.put("data","글을 삭제했습니다.");
		
		return resultMap;
	}

}
