package com.hl.rest.service;

import java.util.List;

import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;

public interface IHomeworkService {
	public void CreateHomeworkNotice(HomeworkNotice homeworknotice);
	public void CreateHomework(Homework homework);
	public List<HomeworkNotice> getHomeworkNoticeList();
	public List<Homework> getHomeworkList_teacher(String memberIdx);
	public List<Homework> getHomeworkList_student(String memberIdx);
}
