package com.ct.dao;

import java.util.List;

import com.ct.exception.UserException;
import com.ct.tv.TV;

public interface ITvDao {
	
	public boolean validate(String username, String password, int choice) throws UserException;

	public boolean addTV(TV tv) throws UserException;

	public TV searchById(long id) throws UserException;

	public List<TV> displayAllTvs() throws UserException;
	

}
