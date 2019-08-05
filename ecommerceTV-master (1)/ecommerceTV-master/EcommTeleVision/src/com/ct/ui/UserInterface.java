package com.ct.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.ct.exception.UserException;
import com.ct.service.IService;
import com.ct.service.Service;
import com.ct.tv.TV;

public class UserInterface {
	//creating static reference of IService interface
	static IService userObj = null;
	UserInterface intObj = null;
	
	
	public static void main(String[] args) {
		
		//Infinite while loop to continuously show home page
		
		while(true) {										
		Scanner scn = new Scanner(System.in);
		System.out.println("***Welcome to ABC Television Shop***");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		
		System.out.println("1. User Login");
		System.out.println("2. Admin Login");
		System.out.println("3. Exit");
		
		int choice = scn.nextInt();
		UserInterface uiObj = new UserInterface();
		
	  /*  Case 1 validates user credentials and returns ArrayList of TV from database and prints it to user
		  Case 2 validates admin credentials and shows additional options to admin
		  case 3 is to exit from application */
		
			switch (choice) {
			case 1:

				try {
					System.out.println("Enter username: ");
					String username = scn.next();
					System.out.println("Enter password: ");
					String password = scn.next();
					boolean isValid = uiObj.validate(username, password, choice);
					if (isValid) {
						uiObj = new UserInterface();
						TV tvObj = new TV();
						List<TV> allTvs = new ArrayList<TV>(); 
						try {
							allTvs = uiObj.displayAllTvs(); 
							Iterator iterator = allTvs.iterator();
							while (iterator.hasNext()) {
								tvObj = (TV) iterator.next();
								System.out.println(" " + tvObj.getId() + " " + tvObj.getName() + " " + tvObj.getBrand()
										+ " " + tvObj.getPrice());
							}
						} catch (UserException e) {
							System.out.println(e.getMessage());
						}
					} else {
						throw new UserException("Either username or password is invalid");
					}

				} catch (UserException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Please give input in the specified format");
				}
				break;

			case 2:
				System.out.println("Enter username: ");
				String username = scn.next();
				System.out.println("Enter password: ");
				String password = scn.next();
				try {
					boolean isValid = uiObj.validate(username, password, choice);
					if (isValid) {
						UserInterface insideSwitch = new UserInterface();
						insideSwitch.selectOption();
					} else {
						throw new UserException("Either username or password is invalid");
					}

				} catch (UserException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Please give input in the specified format");
				}
				break;

			case 3:
				System.exit(0);

			default:
				System.out.println("Give valid input");
				break;
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
	
	
	public void selectOption() {
		
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
		int userchoice = scn.nextInt();
		switch(userchoice) {
		case 1:
				System.out.println("Enter Name, Brand and price of the TV ");
				String name = scn.next();
				String brand = scn.next();
				int price = scn.nextInt();
				TV tv = new TV(name, brand, price);
				userObj = new Service();
				try {
					boolean isAdded = userObj.addTV(tv);
					if (isAdded == false) {
						System.out.println("Data is successfully added into databse");
					}
				} catch (UserException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Please give input in the specified format");
				}

				break;
		case 2:
				System.out.println("Enter ID of the TV to be searched");
				long id = scn.nextLong();
				userObj = new Service();
				intObj = new UserInterface();
				try {
					TV tvObj = intObj.searchById(id);
				} catch (UserException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Please give input in the specified format");
				}
				break;
		case 3:
				intObj = new UserInterface();
				TV tvObj = new TV();
				List<TV> allTvs = new ArrayList<TV>();
				try {
					allTvs = intObj.displayAllTvs();
					if (allTvs == null) {
						System.out.println("There is no data to display. Please add data");
					} else {
						Iterator iterator = allTvs.iterator();
						while (iterator.hasNext()) {
							tvObj = (TV) iterator.next();
							System.out.println(" " + tvObj.getId() + " " + tvObj.getName() + " " + tvObj.getBrand()
									+ " " + tvObj.getPrice());
						}
					}
				} catch (UserException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Please give input in the specified format");
				}

				break;
		
		case 4:
			break;		
		default:
			System.out.println("Please enter valid option");
			break;
		}
		if(userchoice == 4) {
			break;
		}
		
		}
	}
	
	

	/* validate funtion takes username, password and choice of the user as an arguments 
	 *  and performs validation checks */
	
	public boolean validate(String username, String password, int choice) throws UserException {
		
		boolean isValid = false;
		userObj = new Service();
		isValid = userObj.validate(username, password, choice);
		
		return isValid;
		
	}

	
	/* displayAllTvs() method stores data from database into ArrayList and prints it on terminal
	 * if data is not their, it'll return null ArrayList*/
	
	public List<TV> displayAllTvs() throws UserException {
		
		userObj = new Service();
		List<TV> allTvs = new ArrayList<TV>();
		allTvs = userObj.displayAllTvs();
		return allTvs;
	}

	/* SearchById method returns the entire TV object of the specified ID */
	
	public TV searchById(long id) throws UserException {
		// TODO Auto-generated method stub
		userObj = new Service();
		TV tvObj = userObj.searchById(id);
		return tvObj;
	}
	
}
