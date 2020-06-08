package com.hl.rest.service;

import java.util.List;

import com.hl.rest.vo.Homework_old;
import com.hl.rest.vo.Notice;

public interface IBoardService {
	public Notice getNotice(String noticeIdx);
	public void createNotice(Notice notice);
	public List<Notice> getNoticeList();
	public int getNoticeListSize();
	public void deleteNotice(int noticeIdx);
}
