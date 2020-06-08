package com.hl.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hl.rest.vo.Homework;
import com.hl.rest.vo.HomeworkNotice;

@Repository
public class HomeworkDaoImpl implements IHomeworkDao{
	@Autowired
	SqlSession session;

	@Override
	public void CreateHomeworkNotice(HomeworkNotice homeworknotice) {
		session.insert("homework.CreateHomeworkNotice", homeworknotice);
	}

	@Override
	public void CreateHomework(Homework homework) {
		session.insert("homework.CreateHomework", homework);
	}

	@Override
	public List<HomeworkNotice> getHomeworkNoticeList() {
		return session.selectList("homework.getHomeworkNoticeList");
	}

	@Override
	public List<Homework> getHomeworkList_teacher(String memberIdx) {
		return session.selectList("homework.getHomeworkList_teacher", memberIdx);
	}

	@Override
	public List<Homework> getHomeworkList_student(String memberIdx) {
		return session.selectList("homework.getHomeworkList_student", memberIdx);
	}

	@Override
	public List<Homework> getHomeworkList_byIdx(String homeworkNoticeIdx) {
		return session.selectList("homework.getHomeworkList_byIdx", homeworkNoticeIdx);
	}

	@Override
	public int getWhoseHomeworkNotice(String homeworkNoticeIdx) {
		return session.selectOne("homework.getWhoseHomeworkNotice", homeworkNoticeIdx);
	}

	@Override
	public List<Homework> getHomeworkList_byIdx(String homeworkNoticeIdx, String memberIdx) {
		Map<String, String> map = new HashMap<>();
		map.put("homeworkNoticeIdx", homeworkNoticeIdx);
		map.put("memberIdx", memberIdx);
		return session.selectList("homework.getHomeworkList_byIdx_memberIdx", map);
	}

	@Override
	public void updateHomeworkNotice(HomeworkNotice homeworknotice) {
		session.update("homework.updateHomeworkNotice", homeworknotice);
	}
	
}
