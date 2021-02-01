package com.web.curation.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alarm {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ano;
	
	private String aurl;
	private String asummary;
	private int atype;//0:알림, 1:피드
	private int auser;
	@Column(insertable=false,updatable=false)
	private String adate;
	@Column(insertable=false)
	private boolean aisRead;
	private int createUser;
	//피드 dto 하나  만들어서 피드 페이지로 리턴해주기

}
