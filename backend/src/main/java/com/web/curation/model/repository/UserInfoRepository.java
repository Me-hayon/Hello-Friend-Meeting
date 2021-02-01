package com.web.curation.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	public UserInfo findByEmail(String email);
	public UserInfo findByTel(String tel);
	public UserInfo findByEmailAndPassword(String email, String password);
	public void deleteByEmail(String email);
}
