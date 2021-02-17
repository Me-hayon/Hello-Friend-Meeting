package com.web.curation.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.model.entity.Filetest;
import com.web.curation.model.repository.FiletestRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class FiletestController {

	@Autowired
	FiletestRepository filetestRepository;
	
	@PostMapping("/fileUpload")
	public Object fileUpload(@RequestParam("file") MultipartFile file) {
		Map<String,Object> resultMap=new HashMap<>();
		
		String fileName=file.getOriginalFilename();
		
		byte[] bytes;
		
		Filetest filetest=new Filetest();
		resultMap.put("result",false);
		try {
			bytes=file.getBytes();
			
			try {
				Blob blob=new javax.sql.rowset.serial.SerialBlob(bytes);
				
				filetest.setFname(fileName);
				filetest.setFblob(blob);
				filetestRepository.save(filetest);
				resultMap.clear();
				resultMap.put("result",true);
			}catch(SerialException e1) {
				e1.printStackTrace();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		
		return resultMap;
	}
	
	@GetMapping("/getFile")
	public Object getFile(@RequestParam int fno) {
		Map<String,Object> resultMap=new HashMap<>();
		
		Filetest file=filetestRepository.findById(fno).get();
		
		
		try {
			byte[] encoded= file.getFblob().getBytes(1l, (int)file.getFblob().length());
			String fileString=new String(encoded);
			
			resultMap.put("file",encoded);
			resultMap.put("fileString",fileString);
		}catch(Exception e){}
		
		return resultMap;
	}
}
