package com.ct.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.chainsaw.Main;

import com.ct.exception.UserException;
import com.ct.service.IService;
import com.ct.service.Service;
import com.ct.tv.TV;

public class UserInterface {
	//creating static reference of IService interface
	static IService userObj = null;
	UserInterface intObj = null;
	static Logger log = Logger.getLogger(UserInterface.class);
	
	
	public static void main(String[] args) {
		
		//Infinite while loop to continuously show home page

		
		while(true) {
		log.info("Getting user input, at home page");
		Scanner scn = new Scanner(System.in);
		System.out.println("***Welcome to ABC Television Shop***");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println("1. User Login");
		System.out.println("2. Admin Login");
		System.out.println("3. Exit");
		int choice = 0;
		try {
		choice = scn.nextInt();
		log.info("Got user choice" + choice);
		UserInterface uiObj = new UserInterface();
		
	  /*  Case 1 validates user credentials and returns ArrayList of TV from database and prints it to user
		  Case 2 validates admin credentials and shows additional options to admin
		  case 3 is to exit from application */
		
				switch (choice) {
				case 1:

					try {
						System.out.println("Enter username: ");
						String username = scn.next();
						log.info("Entered username of user: " + username);
						System.out.println("Enter password: ");
						String password = scn.next();
						log.info("Checking for valid username and password");
						boolean isValid = uiObj.validate(username, password, choice);
						if (isValid) {
							log.info("valid username");
							uiObj = new UserInterface();
							TV tvObj = new TV();
							log.info("creating new TV object");
							List<TV> allTvs = new ArrayList<TV>();
							int count = 0;
							log.info("Calling display all TVs method");
							allTvs = uiObj.displayAllTvs();
							Iterator iterator = allTvs.iterator();
							log.info("Printing all Tvs");
							while (iterator.hasNext()) {
								count++;
								tvObj = (TV) iterator.next();
								System.out.println(" " + count + " " + tvObj.getId() + " " + tvObj.getName() + " "
										+ tvObj.getBrand() + " " + tvObj.getPrice());
							}

							while (true) {
								try {
									log.info("Select TV to purchase");
									System.out.println("Select TV to purchase");
									log.info("Taking user choice");
									int tvNo = scn.nextInt();
									System.out.println(" " + allTvs.get(tvNo - 1) + " has been successfully purchased");
									break;
								} catch (IndexOutOfBoundsException e) {
									System.out.println("Please enter valid option");
									log.error("Index out of bound exception");

								} catch (InputMismatchException e) {
									log.error("Input mismatch while purchasing TV");
									throw new UserException("enter input in the specified format");

								}

							}

						} else {
							log.error("username or password typed incorrectly for user");
							throw new UserException("Either username or password is invalid");
						}

					} catch (UserException e) {
						log.error("user defined exception is thrown in user login");
						System.out.println(e.getMessage());
					} catch (InputMismatchException e) {
						log.error("Input Mismatch exception is thrown in user login");
						System.out.println("Please give input in the specified format");
					} catch (IndexOutOfBoundsException e) {
						log.error("Index Out Of Bound exception is thrown in user login");
						System.out.println("Please enter valid option");
					}

					break;

				case 2:
					try {

						System.out.println("Enter username: ");
						String username = scn.next();
						log.info("Entered username of user: " + username);

						System.out.println("Enter password: ");
						String password = scn.next();
						log.info("Checking for valid username and password");
						boolean isValid = uiObj.validate(username, password, choice);
						if (isValid) {
							log.info("Calling select option method for admin");
							UserInterface insideSwitch = new UserInterface();
							insideSwitch.selectOption();
						} else {
							log.error("username or password typed incorrectly for user");
							throw new UserException("Either username or password is invalid");
						}

					} catch (UserException e) {
						log.error("user defined exception is thrown in user login");
						System.out.println(e.getMessage());
					} catch (InputMismatchException e) {
						log.error("Input Mismatch exception is thrown in user login");
						System.out.println("Please give input in the specified format 1");
					}
					break;

				case 3:
					log.info("Exiting Home Screen");
					System.exit(0);

				default:
					log.info("Inside default of switch");
					System.out.println("Give valid input");
					break;
				}
			} catch (InputMismatchException e) {
				log.error("Input Mismatch exception is thrown in user login");
				System.out.println("Please give valid input");
			}

		}
	}
	
	
	/* selectOption method is called from option 2 of the switch case statement. selectOption is shown to the user only if 
	 * user is admin. validate function performs validation of admin. After successful validation, selectOption method is
	 * executed
	 * Case 1 inserts data input given by admin into database.
	 * Case 2 performs search operation for given TV ID and returns TV object.
	 * Case 3 displays all mobiles from database. Data from database is stored in ArrayList and then printed 
	 * 		   on terminal
	 * Case 4 is to exit from admin screen    */
	
	
	public void selectOption() throws UserException {
		
		while(true) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Welcome Admin!!");
		System.out.println("Please select from the below options: ");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println("1. Add New TV in database");
		System.out.println("2. Search TV by ID");
		System.out.println("3. Display all TVs");
		System.out.println("4. Exit");
		try {
		int userchoice = scn.nextInt();
		log.info("In admin login screen, displaying options to user");
		switch(userchoice) {
		case 1:
					log.info("Entering Add TV block");
					try {
						System.out.println("Enter Name: ");
						String name = scn.next();
						log.info("Entered name: " + name);
						System.out.println("Enter Brand: ");
						String brand = scn.next();
						log.info("Entered brand: " + brand);
						System.out.println("Enter Price:  ");
						int price = scn.nextInt();
						log.info("Entered price: " + price);
						TV tv = new TV(name, brand, price);
						log.info("created TV object: " + tv);
						intObj = new UserInterface();
						log.info("Calling addTV method to add TV: " + tv);
						boolean isAdded = intObj.addTV(tv);

						if (isAdded == false) {
							System.out.println("Data is successfully added into databse");
							log.info("TV added to database ");
						}
					} catch (UserException e) {
						System.out.println(e.getMessage());
						log.error("User Exception is thrown: " + e.getMessage());
					} catch (InputMismatchException e) {
						log.error("Input Mismatch Exception is thrown");
						System.out.println("Please enter input in the specified format");
					}

					break;
		case 2:
					log.info("Entering in search by ID block");
					try {
						System.out.println("Enter ID of the TV to be searched");
						long id = scn.nextLong();
						log.info("ID to be searched is: " + id);
						userObj = new Service();
						intObj = new UserInterface();
						log.info("Calling searchById method");
						TV tvObj = intObj.searchById(id);
						System.out.println(tvObj);
					} catch (UserException e) {
						log.error("User Exception is thrown: " + e.getMessage());
						System.out.println(e.getMessage());
					} catch (InputMismatchException e) {
						log.error("Input Mismatch Exception is thrown");
						System.out.println("Please give input in the specified format");
					}
					break;
		case 3:
					log.info("Entering in display all TVs block");
					intObj = new UserInterface();
					TV tvObj = new TV();
					log.info("Creating array List of TVs");
					List<TV> allTvs = new ArrayList<TV>();
					try {
						log.info("Calling displayAllTvs method");
						allTvs = intObj.displayAllTvs();
						if (allTvs == null) {
							System.out.println("There is no data to display. Please add data");
							log.warn("no data to display");
						} else {
							log.info("Displaying all TVs using iterator");
							Iterator iterator = allTvs.iterator();
							while (iterator.hasNext()) {
								tvObj = (TV) iterator.next();
								System.out.println(" " + tvObj.getId() + " " + tvObj.getName() + " " + tvObj.getBrand()
										+ " " + tvObj.getPrice());
							}
							log.info("Displayed All Tvs");
						}

					} catch (UserException e) {
						log.error("User Exception is thrown: " + e.getMessage());
						System.out.println(e.getMessage());

					}

					break;
		
		case 4:
			log.info("Exiting from admin page, chose option 4");
			break;		
		default:
			System.out.println("Please enter valid option");
			log.error("wrong option is chosen");
			break;
		}
		if(userchoice == 4) {
			log.error("Exiting from admin page, chose option 4");
			break;
		}
		
		
		}
		catch(InputMismatchException e) {
			log.error("Input Mismatch Exception is thrown");
			System.out.println("Please give valid input");
		}
		
	}
	}
	
	/* addTV method returns true if data is successfully added to database, 
	 * else returns false */

	private boolean addTV(TV tv) throws UserException {
		
		log.info("In AddTV method");
		
		boolean isAdded = false;
		userObj = new Service();
		isAdded = userObj.addTV(tv);
		return isAdded;
	}


	/* validate method takes user name, password and choice of the user as an arguments 
	 *  and performs validation checks */
	
	public boolean validate(String username, String password, int choice) throws UserException {
		
		log.info("In validate method of User Interface");
		boolean isValid = false;
		userObj = new Service();
		isValid = userObj.validate(username, password, choice);
		
		return isValid;
		
	}

	
	/* displayAllTvs() method stores data from database into ArrayList and prints it on terminal
	 * if data is not their, it'll return null ArrayList*/
	
	public List<TV> displayAllTvs() throws UserException {
		
		log.info("In displayAllTvs method");
		userObj = new Service();
		List<TV> allTvs = new ArrayList<TV>();
		allTvs = userObj.displayAllTvs();
		return allTvs;
	}

	/* SearchById method returns the entire TV object of the specified TV ID */
	
	public TV searchById(long id) throws UserException {
		// TODO Auto-generated method stub
		log.info("In searchById method");
		userObj = new Service();
		TV tvObj = userObj.searchById(id);
		return tvObj;
	}
	
}
