package com.hl.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hl.rest.dao.IBoardDao;
import com.hl.rest.vo.Homework;


@Service
public class BoardServiceImpl implements IBoardService {
	
	@Autowired
	IBoardDao repo;

	@Override
	public void insertHomework(Homework homework) {
		repo.insertHomework(homework);
	}

}
