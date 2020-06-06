package com.hl.rest.dao;

import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;

public interface IHomeworkDao {
	public void CreateHomeworkNotice(HomeworkNotice homeworknotice);
	public void CreateHomework(Homework homework);
}
