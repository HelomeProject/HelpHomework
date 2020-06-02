package com.hl.rest.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hl.rest.vo.Homework;

@Repository
public class BoardDaoImpl implements IBoardDao {
	@Autowired
	SqlSession session;

	@Override
	public void insertHomework(Homework homework) {
		session.insert("board.insertHomework", homework);
	}
	
	
}
