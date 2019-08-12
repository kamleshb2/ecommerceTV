package com.ct.dao;

interface IQueryMapper{
	public static final String insertQuery = "INSERT INTO TV(TV_Name, Brand_Name, Price) VALUES(?,?,?)";
	public static final String searchQuery = "select TV_id, TV_Name, Brand_Name, Price from TV where TV_id=?";
	public static final String displayQuery = "select TV_id, TV_Name, Brand_Name, Price from TV";
	
	
}