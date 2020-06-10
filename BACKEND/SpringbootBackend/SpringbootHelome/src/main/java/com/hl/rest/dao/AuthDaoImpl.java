package com.hl.rest.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDaoImpl implements IAuthDao{
	@Autowired
	SqlSession session;

	@Override
	public String getPassword(String email) {
		return session.selectOne("member.getPassword", email);
	}
}
