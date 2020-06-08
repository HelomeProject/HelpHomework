package com.hl.rest.service;

import java.util.List;

import com.hl.rest.vo.Member;

public interface IMemService {
	/** member CRUD */
	public void registerMem(Member mem);
	public Member getMem(String email);
	public Member getMem(int memberIdx);
	public int getMemListSize();
	public List<Member> getMemStudentList(int startlist, int listsize, String grade, String classnum);
	public void updateMember(Member member);
}
