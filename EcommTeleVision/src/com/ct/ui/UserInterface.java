package com.ct.ui;

import java.util.Scanner;

import com.ct.exception.UserException;
import com.ct.service.IService;
import com.ct.service.Service;
import com.ct.tv.TV;

public class UserInterface {
	IService userObj = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(System.in);
		System.out.println("***Welcome to ABC Television Shop***");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		
		System.out.println("1. User Login");
		System.out.println("2. Admin Login");
		
		int choice = scn.nextInt();
		UserInterface uiobj = new UserInterface();
		
		
		switch(choice) {
			case 1:
				
				try {
				System.out.println("Enter username: ");
				String username = scn.next();
				System.out.println("Enter password: ");
				String password = scn.next();
				boolean res = uiobj.validate(username, password);
				if(res) {
					System.out.println("Valid username");
				}
				
				}catch(UserException e){
					System.out.println(e.getMessage());
				}
				break;
				

			case 2:
				System.out.println("Enter username: ");
				String username = scn.next();
				System.out.println("Enter password: ");
				String password = scn.next();
				try {
				
				boolean res = uiobj.adminValidate(username, password);
				if(res) {
					UserInterface insideSwitch = new UserInterface();
					insideSwitch.selectOption();
				}
				
				}catch(UserException e){
					System.out.println(e.getMessage());
				}
				break;
			
		}
				
		
	}
	
	public boolean validate(String username, String password) throws UserException {
		
		boolean uiresult = false;
		userObj = new Service();
		uiresult = userObj.validate(username, password);
		
		return uiresult;
		
	}
	
	public boolean adminValidate(String username, String password) throws UserException {
		
		boolean uiresult = false;
		userObj = new Service();
		uiresult = userObj.adminValidate(username, password);
		
		return uiresult;
		
	}
	
	public void selectOption() {
		
		Scanner scn = new Scanner(System.in);
		System.out.println("Welcome Admin!!");
		System.out.println("Please select from the below options: ");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println("1. Add New TV in database");
		System.out.println("2. Search TV by ID");
		System.out.println("3. Display all TVs");
		int userchoice = scn.nextInt();
		switch(userchoice) {
		case 1:
			System.out.println("Enter Name, Brand and price of the TV ");
			String name = scn.next();
			String brand = scn.next();
			int price = scn.nextInt();
			TV tv = new TV(name, brand, price);
			try {
				boolean res = userObj.addTV(tv);
			} catch (UserException e) {
				System.out.println(e.getMessage());
			}
			
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}
	
}
