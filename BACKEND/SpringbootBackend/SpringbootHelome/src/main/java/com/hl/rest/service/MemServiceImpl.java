package com.hl.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hl.rest.dao.IMemDao;
import com.hl.rest.vo.Member;

@Service
public class MemServiceImpl implements IMemService {
	
	@Autowired
	IMemDao repo;
	
	@Override
	public void registerMem(Member mem) {
		repo.registerMem(mem.getEmail(), mem.getPassword(), mem.getUsername(), mem.getSchool(), mem.getIsteacher(), mem.getGrade(), mem.getClassnum());
	}

	@Override
	public Member getMem(String email) {
		return repo.getMem(email);
	}
	
}
