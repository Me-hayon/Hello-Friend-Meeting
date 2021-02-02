package com.web.curation.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.entity.GroupParticipant;

public interface GroupParticipantRepository extends JpaRepository<GroupParticipant,Integer> {
	public List<GroupParticipant> findAllByGno(int gno);
	public List<GroupParticipant> findAllByUno(int uno);
}
