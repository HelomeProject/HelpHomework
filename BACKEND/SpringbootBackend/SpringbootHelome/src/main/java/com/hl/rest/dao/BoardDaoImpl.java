package com.hl.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hl.rest.vo.Homework_old;
import com.hl.rest.vo.Notice;

@Repository
public class BoardDaoImpl implements IBoardDao {
	@Autowired
	SqlSession session;

	@Override
	public void insertHomework(Homework_old homework) {
		session.insert("board.insertHomework", homework);
	}

	@Override
	public int getHomeListSize(String grade, String classnum) {
		Map<String, Object> map = new HashMap<>();
		map.put("grade", Integer.parseInt(grade));
		map.put("classnum", Integer.parseInt(classnum));
		return session.selectOne("board.getHomeworkListSize", map);
	}

	@Override
	public List<Homework_old> getHomeworkList(int startlist, int listsize, String grade, String classnum) {
		Map<String, Object> map = new HashMap<>();
		map.put("startlist", startlist);
		map.put("listsize", listsize);
		map.put("grade", Integer.parseInt(grade));
		map.put("classnum", Integer.parseInt(classnum));
		return session.selectList("board.getHomeworkList", map);
	}

	@Override
	public int getHomeListSize(String memberIdx) {
		return session.selectOne("board.getHomeworkSize", Integer.parseInt(memberIdx));
	}

	@Override
	public List<Homework_old> getHomeworkList(int startlist, int listsize, String memberIdx) {
		Map<String, Object> map = new HashMap<>();
		map.put("startlist", startlist);
		map.put("listsize", listsize);
		map.put("memberIdx", Integer.parseInt(memberIdx));
		return session.selectList("board.getMyHomeworkList", map);
	}

	@Override
	public Notice getNotice(String noticeIdx) {
		return session.selectOne("board.getNotice", Integer.parseInt(noticeIdx));
	}

	@Override
	public void createNotice(Notice notice) {
		session.insert("board.createNotice", notice);
	}

	@Override
	public List<Notice> getNoticeList() {
		return session.selectList("board.getNoticeList");
	}

	@Override
	public int getNoticeListSize() {
		return session.selectOne("board.getNoticeListSize");
	}
	
	
}
