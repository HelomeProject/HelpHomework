package com.hl.rest.dao;

import java.util.HashMap;
import java.util.List;
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

	@Override
	public int getMemListSize() {
		return session.selectOne("member.getMemberListSize");
	}

	@Override
	public List<Member> getMemStudentList(int startlist, int listsize, String grade, String classnum) {
		Map<String, Integer> map = new HashMap<>();
		map.put("startlist", startlist);
		map.put("listisze", listsize);
		map.put("grade", Integer.parseInt(grade));
		map.put("classnum", Integer.parseInt(classnum));
		return session.selectList("member.getMemStudentList", map);
	}

	@Override
	public Member getMem(int memberIdx) {
		return session.selectOne("member.getMemberIdx", memberIdx);
	}

	@Override
	public void updateMember(Member member) {
		session.update("member.updateMember", member);
	}

}
