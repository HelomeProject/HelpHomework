package com.hl.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hl.rest.dao.IHomeworkDao;
import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;

@Service
public class HomeworkServiceImpl implements IHomeworkService{
	
	@Autowired
	IHomeworkDao repo;

	@Override
	public void CreateHomeworkNotice(HomeworkNotice homeworknotice) {
		repo.CreateHomeworkNotice(homeworknotice);
	}

	@Override
	public void CreateHomework(Homework homework) {
		repo.CreateHomework(homework);
	}

	@Override
	public List<HomeworkNotice> getHomeworkNoticeList() {
		return repo.getHomeworkNoticeList();
	}
	
}
