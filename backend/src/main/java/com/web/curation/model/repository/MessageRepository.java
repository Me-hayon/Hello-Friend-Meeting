package com.web.curation.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message,Integer> {

}
