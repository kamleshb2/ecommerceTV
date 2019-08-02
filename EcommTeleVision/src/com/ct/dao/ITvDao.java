package com.ct.dao;

import com.ct.exception.UserException;
import com.ct.tv.TV;

public interface ITvDao {
	
	public boolean validate(String username, String password) throws UserException;

	public boolean adminValidate(String username, String password) throws UserException;

	public boolean addTV(TV tv);
	

}
