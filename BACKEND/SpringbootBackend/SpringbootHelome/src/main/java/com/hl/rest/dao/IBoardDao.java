package com.hl.rest.dao;

import java.util.List;

import com.hl.rest.vo.Homework_old;
import com.hl.rest.vo.Member;
import com.hl.rest.vo.Notice;

public interface IBoardDao {
	public Notice getNotice(String noticeIdx);
	public void createNotice(Notice notice);
	public List<Notice> getNoticeList();
	public int getNoticeListSize();
}
