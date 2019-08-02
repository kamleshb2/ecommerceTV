package com.ct.service;

import java.util.ArrayList;

import com.ct.dao.ITvDao;
import com.ct.dao.ITvDaoImpl;
import com.ct.exception.UserException;
import com.ct.tv.TV;

public class Service implements IService {
	
	ITvDao daoObj = null;

	public boolean validate(String username, String password) throws UserException {
		
		daoObj = new ITvDaoImpl();
		boolean res = false;
		res = daoObj.validate(username, password);
		
		return res;
	}

	@Override
	public boolean adminValidate(String username, String password) throws UserException {
		// TODO Auto-generated method stub
		boolean res = false;
		daoObj = new ITvDaoImpl();
		res = daoObj.adminValidate(username, password);
		return res;
	}

	@Override
	public boolean addTV(TV tv) throws UserException {
		// TODO Auto-generated method stub
		boolean res=false;
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
			res = daoObj.addTV(tv);
		}
		else {
			throw new UserException("Brand Name is invalid!");
		}
		return res;
	}
}
