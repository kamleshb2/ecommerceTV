package com.ct.service;

import java.util.ArrayList;
import java.util.List;

import com.ct.dao.ITvDao;
import com.ct.dao.ITvDaoImpl;
import com.ct.exception.UserException;
import com.ct.tv.TV;

public class Service implements IService {
	
	static ITvDao daoObj = null;
	
	/* validate() method returns boolean and 
	 * calls validate method from Dao */

	public boolean validate(String username, String password, int choice) throws UserException {
		
		daoObj = new ITvDaoImpl();
		boolean isValid = false;
		isValid = daoObj.validate(username, password, choice);
		
		return isValid;
	}

	/* addTV() takes TV object as an argument and returns boolean of whether object
	 * is inserted or not. It first performs validation of user input
	 * checks whether user has entered correct brand name or not and then calls addTV method of
	 * Dao layer. */

	@Override
	public boolean addTV(TV tv) throws UserException {
		// TODO Auto-generated method stub
		boolean isAdded=false;
		ArrayList<String> brandNames = new ArrayList();
		brandNames.add("LG");
		brandNames.add("Samsung");
		brandNames.add("Sony");
		brandNames.add("Panasonic");
		brandNames.add("Huawei");
		brandNames.add("Onida");
		brandNames.add("BPL");
		brandNames.add("Mi");
		brandNames.add("Videocon");
		String bname = tv.getBrand();
		if(brandNames.contains(bname)) {
			daoObj = new ITvDaoImpl();
			isAdded = daoObj.addTV(tv);
		}
		else {
			throw new UserException("Please enter valid brand name");
		}
		return isAdded;
	}


	/* searchById() method returns TV object of given TV Id */
	@Override
	public TV searchById(long id) throws UserException {
		daoObj = new ITvDaoImpl();
		TV tvObj = daoObj.searchById(id);

		return tvObj;
	}

	/* displayAllTvs() method returns ArrayList of all TVs in database*/

	@Override
	public List<TV> displayAllTvs() throws UserException {
		
		List<TV> allTvs = new ArrayList<TV>();
		allTvs = daoObj.displayAllTvs();
		return allTvs;
	}
}
