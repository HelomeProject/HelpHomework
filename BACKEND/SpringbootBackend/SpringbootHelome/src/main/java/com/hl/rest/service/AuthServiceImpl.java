package com.hl.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hl.rest.dao.IAuthDao;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	IAuthDao repo;
	
	@Override
	public String getPassword(String email) {
		return repo.getPassword(email);
	}
	
}
