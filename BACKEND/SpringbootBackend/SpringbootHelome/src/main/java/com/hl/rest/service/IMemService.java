package com.hl.rest.service;

import com.hl.rest.vo.Member;

public interface IMemService {
	/** member CRUD */
	public void registerMem(Member mem);
	public Member getMem(String email);
	public int getMemListSize();
}
