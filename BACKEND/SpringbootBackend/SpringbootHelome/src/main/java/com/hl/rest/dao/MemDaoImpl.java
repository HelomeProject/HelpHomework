package com.hl.rest.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hl.rest.vo.Member;

@Repository
public class MemDaoImpl implements IMemDao {
	
	@Autowired
	SqlSession session;

	@Override
	public void registerMem(String email, String password, String username, String school, String isteacher, String grade, String classnum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", password);
		map.put("username", username);
		map.put("school", school);
		map.put("isteacher", Integer.parseInt(isteacher));
		map.put("grade", Integer.parseInt(grade));
		map.put("classnum", Integer.parseInt(classnum));
		session.insert("member.insertMember", map);
	}

	@Override
	public Member getMem(String email) {
		return session.selectOne("member.getMember", email);
	}
	
}
