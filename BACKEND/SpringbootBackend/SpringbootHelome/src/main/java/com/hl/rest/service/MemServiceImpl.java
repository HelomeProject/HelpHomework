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
		repo.registerMem(mem.getUsername(), mem.getSchool(), mem.getEmail(), mem.getPassword(), mem.getGrade());
	}
	
}
