package com.hl.rest.dao;

public interface IMemDao {
	/** member CRUD */
	public void registerMem(String username, String school, String eamil, String password, String grade);
}
