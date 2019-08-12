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

import org.apache.log4j.Logger;

import com.ct.exception.UserException;
import com.ct.tv.TV;
import com.ct.ui.UserInterface;

public class ITvDaoImpl implements ITvDao {
	
	static Logger log = Logger.getLogger(ITvDaoImpl.class);
	
	/*validate() method loads different properties files for user and admin. 
	 * seperately validates user and admin, returns boolean whether validation 
	 * is successful or not*/
	
	
	public boolean validate(String username, String password, int choice) throws UserException {
		log.info("In validate method of DAO");
		boolean isValid = false;
		try {
			log.info("Creating File Input Stream Object");
			FileInputStream fis;
			if (choice == 1) {
				log.info("Accessing property file of user");
				fis = new FileInputStream(
						"C://Users//Trainingvdi//Documents//EclipseProj//ecommerceTV-1.2//ecommerceTV-master (1)//ecommerceTV-master//EcommTeleVision//src//userlogin.properties");
			} else {
				log.info("Accessing property file of admin");
				fis = new FileInputStream(
						"C://Users//Trainingvdi//Documents//EclipseProj//ecommerceTV-1.2//ecommerceTV-master (1)//ecommerceTV-master//EcommTeleVision//src//adminlogin.properties");
			}
			Properties prop = new Properties();
			log.info("Loading properties file");
			prop.load(fis);
			log.info("validating username and password");
			if (prop.containsKey(username) && password.equals(prop.get(username))) {
				isValid = true;

			} else {
				log.error("username or password incorrect: from DAO");
				throw new UserException("Either username or password is incorrect");
			}
		} catch (FileNotFoundException e) {
			log.error("File not found: from DAO");
			throw new UserException("There is problem in Login. Please try again after some time");

		} catch (IOException e) {
			log.error("IOException: from DAO");
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
		log.info("In addTv method of DAO");
		boolean isAdded = false;
		log.info("Calling DbConnection and creating database object");
		Connection conn = DbConnection.getConnection();
		try {
			log.info("Executing query");
			PreparedStatement insertData = conn
					.prepareStatement(IQueryMapper.insertQuery);
			insertData.setString(1, tv.getName());
			insertData.setString(2, tv.getBrand());
			insertData.setInt(3, tv.getPrice());
			isAdded = insertData.execute();

		} catch (SQLException e) {
			log.error("SQLException: from DAO");
			throw new UserException("Server Internal Error: Try after some time, if issue persists, contact admin");
		}

		return isAdded;
	}


	/* searchById takes TV Id as an argument and returns corresponding 
	 * TV object, if Id is not present, throws exception */
	@Override
	public TV searchById(long id) throws UserException {
		log.info("In searchById method of DAO");
		log.info("Calling DbConnection and creating database object");
		Connection connect = DbConnection.getConnection();
		TV newTV = null;

		try {
			log.info("Executing query");
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
				log.info("TV found: " + newTV);
			} else {
				log.error("Entered Invalid ID: from DAO");
				throw new UserException("Please Enter Valid ID");
			}

		} catch (SQLException e) {
			log.error("SQLException: from DAO");
			throw new UserException("Server is unresponsive. Please try again after some time");
		}

		return newTV;
	}


	/*displayAllTvs() method returns ArrayList of all data present in the database in the 
	 * form of TV objects. */

	@Override
	public List<TV> displayAllTvs() throws UserException {
		log.info("In displayAllTvs method of DAO");
		log.info("Calling DbConnection and creating database object");
		Connection conn = DbConnection.getConnection();
		log.info("creating ArrayList");
		List<TV> tvList = new ArrayList<TV>();
		try {
			log.info("Executing query");
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
			log.info("Execution done");

		} catch (SQLException e) {
			log.error("SQLException: from DAO");
			throw new UserException("Server is unresponsive. Please try again after some time");
		}

		return tvList;
	}

}
