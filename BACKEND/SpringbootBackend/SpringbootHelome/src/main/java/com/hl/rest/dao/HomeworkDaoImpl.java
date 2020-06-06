package com.hl.rest.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hl.rest.vo.HomeworkNotice;

@Repository
public class HomeworkDaoImpl implements IHomeworkDao{
	@Autowired
	SqlSession session;

	@Override
	public void CreateHomeworkNotice(HomeworkNotice homeworknotice) {
		session.insert("homework.CreateHomeworkNotice", homeworknotice);
	}
	
}
