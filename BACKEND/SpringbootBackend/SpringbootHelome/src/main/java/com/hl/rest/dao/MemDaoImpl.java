package com.hl.rest.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemDaoImpl implements IMemDao {
	
	@Autowired
	SqlSession session;

	@Override
	public void registerMem(String username, String school, String email, String password, String grade) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("school", school);
		map.put("email", email);
		map.put("password", password);
		map.put("grade", Integer.parseInt(grade));
		session.insert("member.insertMember", map);
	}
	
}