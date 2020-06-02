package com.hl.rest.dao;

import com.hl.rest.vo.Member;

public interface IMemDao {
	/** member CRUD */
	public void registerMem(String email, String password, String username, String school, String isteacher, String grade, String classnum);
	public Member getMem(String email);
	public int getMemListSize();
}
