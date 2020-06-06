package com.hl.rest.service;

import java.util.List;

import com.hl.rest.vo.Homework_old;
import com.hl.rest.vo.Notice;

public interface IBoardService {
	public void insertHomework(Homework_old homework);
	public int getHomeListSize(String grade, String classnum);
	public int getHomeListSize(String memberIdx);
	public List<Homework_old> getHomeworkList(int startlist, int listsize, String grade, String classnum);
	public List<Homework_old> getHomeworkList(int startlist, int listsize, String memberIdx);
	
	public Notice getNotice(String noticeIdx);
	public void createNotice(Notice notice);
	public List<Notice> getNoticeList();
}
