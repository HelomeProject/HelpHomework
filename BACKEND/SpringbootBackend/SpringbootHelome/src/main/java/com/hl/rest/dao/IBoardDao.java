package com.hl.rest.dao;

import java.util.List;

import com.hl.rest.vo.Homework;
import com.hl.rest.vo.Member;

public interface IBoardDao {
	public void insertHomework(Homework homework);
	public int getHomeListSize(String grade, String classnum);
	public int getHomeListSize(String memberIdx);
	public List<Homework> getHomeworkList(int startlist, int listsize, String grade, String classnum);
	public List<Homework> getHomeworkList(int startlist, int listsize, String memberIdx);
}
