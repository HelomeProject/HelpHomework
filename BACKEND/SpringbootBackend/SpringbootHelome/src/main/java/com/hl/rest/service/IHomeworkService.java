package com.hl.rest.service;

import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;

public interface IHomeworkService {
	public void CreateHomeworkNotice(HomeworkNotice homeworknotice);
	public void CreateHomework(Homework homework);
}
