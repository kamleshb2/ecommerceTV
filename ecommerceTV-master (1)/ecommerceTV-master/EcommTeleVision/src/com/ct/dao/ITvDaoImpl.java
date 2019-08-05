package com.ct.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ct.exception.UserException;
import com.ct.tv.TV;

public class ITvDaoImpl implements ITvDao {
	
	/*validate() method loads different properties files for user and admin. 
	 * seperately validates user and admin, returns boolean whether validation 
	 * is successful or not*/
	
	public boolean validate(String username, String password, int choice) throws UserException {
		boolean isValid = false;
		try {
			FileInputStream fis;
			if (choice == 1) {
				fis = new FileInputStream(
						"C://Users//Trainingvdi//Downloads//ecommerceTV-master//ecommerceTV-master//EcommTeleVision//src//userlogin.properties");
			} else {
				fis = new FileInputStream(
						"C://Users//Trainingvdi//Downloads//ecommerceTV-master//ecommerceTV-master//EcommTeleVision//src//adminlogin.properties");
			}
			Properties prop = new Properties();
			prop.load(fis);

			if (prop.containsKey(username) && password.equals(prop.get(username))) {
				isValid = true;

			} else {
				throw new UserException("Either username or password is incorrect");
			}
		} catch (FileNotFoundException e) {
			throw new UserException("There is problem in Login. Please try again after some time");

		} catch (IOException e) {
			throw new UserException(
					"Problem is incurred during login. Please try again after sometime, if problem persists, contact admin");
		}

		return isValid;
	}



	/* addTV() method adds user values into database. TV object is passed 
	 * into the method.  */
	@Override
	public boolean addTV(TV tv) throws UserException {
		// TODO Auto-generated method stub
		boolean isAdded = false;
		Connection conn = DbConnection.getConnection();
		try {
			PreparedStatement insertData = conn
					.prepareStatement(IQueryMapper.insertQuery);
			insertData.setString(1, tv.getName());
			insertData.setString(2, tv.getBrand());
			insertData.setInt(3, tv.getPrice());
			isAdded = insertData.execute();

		} catch (SQLException e) {
			throw new UserException("Server Internal Error: Try after some time, if issue persists, contact admin");
		}

		return isAdded;
	}


	/* searchById takes TV Id as an argument and returns corresponding 
	 * TV object, if Id is not present, throws exception */
	@Override
	public TV searchById(long id) throws UserException {
		Connection connect = DbConnection.getConnection();
		TV newTV = null;

		try {
			PreparedStatement selectTV = connect
					.prepareStatement(IQueryMapper.searchQuery);
			selectTV.setLong(1, id);
			ResultSet resultQuery = selectTV.executeQuery();

			if (resultQuery.next()) {
				TV tvObj = new TV();
				tvObj.setId(resultQuery.getLong(1));
				tvObj.setName(resultQuery.getString(2));
				tvObj.setBrand(resultQuery.getString(3));
				tvObj.setPrice(resultQuery.getInt(4));
				newTV = tvObj;
				System.out.println(newTV);
			} else {
				throw new UserException("Please Enter Valid ID");
			}

		} catch (SQLException e) {
			throw new UserException("Server is unresponsive. Please try again after some time");
		}

		return newTV;
	}


	/*displayAllTvs() method returns ArrayList of all data present in the database in the 
	 * form of TV objects. */

	@Override
	public List<TV> displayAllTvs() throws UserException {
		Connection conn = DbConnection.getConnection();
		List<TV> tvList = new ArrayList<TV>();
		try {
			PreparedStatement displayTv = conn.prepareStatement(IQueryMapper.displayQuery);
			ResultSet resultQuery = displayTv.executeQuery();
			while (resultQuery.next()) {
				TV tvObj = null;
				tvObj = new TV();
				tvObj.setId(resultQuery.getLong(1));
				tvObj.setName(resultQuery.getString(2));
				tvObj.setBrand(resultQuery.getString(3));
				tvObj.setPrice(resultQuery.getInt(4));
				tvList.add(tvObj);
			}

		} catch (SQLException e) {
			throw new UserException("Server is unresponsive. Please try again after some time");
		}

		return tvList;
	}

}
