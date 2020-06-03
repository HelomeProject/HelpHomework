package com.hl.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hl.rest.dao.IBoardDao;
import com.hl.rest.vo.Homework;
import com.hl.rest.vo.Notice;


@Service
public class BoardServiceImpl implements IBoardService {
	
	@Autowired
	IBoardDao repo;

	@Override
	public void insertHomework(Homework homework) {
		repo.insertHomework(homework);
	}

	@Override
	public int getHomeListSize(String grade, String classnum) {
		return repo.getHomeListSize(grade, classnum);
	}

	@Override
	public List<Homework> getHomeworkList(int startlist, int listsize, String grade, String classnum) {
		return repo.getHomeworkList(startlist, listsize, grade, classnum);
	}

	@Override
	public int getHomeListSize(String memberIdx) {
		return repo.getHomeListSize(memberIdx);
	}

	@Override
	public List<Homework> getHomeworkList(int startlist, int listsize, String memberIdx) {
		return repo.getHomeworkList(startlist, listsize, memberIdx);
	}

	@Override
	public Notice getNotice(String noticeIdx) {
		return repo.getNotice(noticeIdx);
	}

	@Override
	public void createNotice(Notice notice) {
		repo.createNotice(notice);
	}

}
