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
