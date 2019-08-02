package com.ct.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.ct.exception.UserException;
import com.ct.tv.TV;

public class ITvDaoImpl implements ITvDao {
	
	
	public boolean validate(String username, String password) throws UserException {
		boolean daores = false;
		try {
			FileInputStream fis;
			fis = new FileInputStream("C://Users//kamleshb2//Documents//Eclipse//EcommTeleVision//src/userlogin.properties");
			Properties prop = new Properties();
			prop.load(fis);

			if (prop.containsKey(username) && password.equals(prop.get(username))) {
				daores = true;

			}
			else {
				throw new UserException("Either username or password is incorrect");
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("IOException");
		}
	   

		
		
		return daores;
	}

	@Override
	public boolean adminValidate(String username, String password) throws UserException {
		// TODO Auto-generated method stub
		boolean daores = false;
		try {
			FileInputStream fis;
			fis = new FileInputStream("C://Users//kamleshb2//Documents//Eclipse//EcommTeleVision//src/adminlogin.properties");
			Properties prop = new Properties();
			prop.load(fis);

			if (prop.containsKey(username) && password.equals(prop.get(username))) {
				daores = true;

			} else {
				throw new UserException("Either username or password is incorrect");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");

		} catch (IOException e) {
			System.out.println("IOException");
		}

		return daores;
		
	}

	@Override
	public boolean addTV(TV tv) {
		// TODO Auto-generated method stub
		return false;
	}

}
