package com.web.curation.model.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm,Integer>{

	public List<Alarm> findByAuserAndAtype(int auser,int atype,Sort sort);
	public Alarm findByAno(int ano);
}
