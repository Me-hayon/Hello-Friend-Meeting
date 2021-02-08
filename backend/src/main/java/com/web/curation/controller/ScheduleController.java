package com.web.curation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.repository.ScheduleParticipantRepository;
import com.web.curation.model.repository.ScheduleRepository;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class ScheduleController {

	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	ScheduleParticipantRepository scheduleParticipantRepository;
	
	
	
	
}
