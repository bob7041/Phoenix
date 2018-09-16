package framework;

import java.sql.*;
import java.util.Properties;

import framework.Utils;

/* Copyright (C) 2018  Bob Brander

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation version 3. The full license is 
   available here: https://opensource.org/licenses/GPL-3.0 

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
   GNU General Public License for more details.
*/

/** 
 * Phoenix Test - Database class
 * 
 * Open a connection to the database specified in the 
 * config file, cache the UIMap table, and
 * provide database services to the other Phoenix modules
 *  
 * @author	 	Bob Brander
 * @version	 	1.0
 * @date		6/23/2018
 * 
 */
public class Database
{
	private Config myConfig = null;
	private Connection dbcon = null;
	private Properties mapprops = null;  
	private ResultSet nextrecord = null;
	private boolean firsttime = true;
	
	/* 
	 * main is only for testing!
	 * 
	 */
	public static void main (String [] args)
	{
		// Note: if database is offline this test should fail and terminate the program
		Config myconfig = new Config (args [0]);
		Database mydb = new Database (myconfig);
		System.out.println ("Database open!");
		
		TestDataRow myTDR = new TestDataRow ();
		mydb.getNextTestData (myTDR, myconfig.getStringProperty ("testset"));
		
		mydb.insertTestResults ("Tester1", "testset1000", 1, "FJira", "PASS");
		System.out.println ("Test Result inserted!");
	}
	
	/**
	 * Database class constructor
	 * 
	 * @param config file
	 */
	public Database (Config config)
	{
		myConfig = config;
		
		// open database connection and cache UI Map
		String dbURL   = myConfig.getStringProperty ("dbServerURL");
		String dbuname = myConfig.getStringProperty ("dbUsername");
		String dbpwd   = myConfig.getStringProperty ("dbPassword");
		
		openDatabaseConnection (dbURL, dbuname, dbpwd);
		cacheUIMap ();
	}
	
	/**
	 * getNextTestData
	 * 
	 * Get 1 row at a time from testData table using a global result set. 
	 * Filter by testset name. Note that we are passing the testset as a param
	 * for performance reasons instead of looking it up in the config property 
	 * list every time this method gets called. This method will populate
	 * the testDataRow object with the data read from the testData table.
	 * 
	 * @param testDataRow object, testsetName
	 * 
	 * NOTE: status of the next record is set in the boolean "Status" field 
	 * of the TestDataRow object (false = next record was not read successfully from database)
	 * 
	 */
	public void getNextTestData (TestDataRow nextRow, String testsetName)
	{	
		int testdata_id = 0;
		
		nextRow.Status = false;
		nextRow.TestRecordID = 0;
		nextRow.ActionType = null;
		nextRow.Project = null;
		nextRow.IssueType = null;
		nextRow.EpicName = null;
		nextRow.Summary = null;
		nextRow.Description = null;
		nextRow.Priority = null;
		nextRow.Labels = null;
		nextRow.Environment = null;
		nextRow.Attachment = null;
		nextRow.LinkedIssued = null;
		nextRow.Issue = null;
		nextRow.Assignee = null;
		nextRow.EpicLink = null;
		nextRow.Sprint = null;  
			
		if (firsttime)
		{
			//System.out.println("getNextTestData: " + testsetName);
		
			try
			{
				String queryString =  "SELECT * FROM TestData WHERE testset = ?";			
				PreparedStatement preparedQueryStmt = dbcon.prepareStatement (queryString);
				preparedQueryStmt.setString (1, testsetName);
				nextrecord = preparedQueryStmt.executeQuery ();
					
				firsttime = false;
			}
			catch (Exception e)
			{
				System.out.println ("Failed to retrieve test data from database");
				System.out.println (e);
				return;
			}
		}
		
		try
		{
		    if (nextrecord.next ())
			{
		    	testdata_id = nextrecord.getInt (1);
		    	
		    	// NEED TO DOUBLE-CHECK the index on these fields!
		    	
		    	nextRow.TestRecordID = nextrecord.getInt     (1);
		    	nextRow.ActionType	 = nextrecord.getString  (4);
		    	nextRow.Project      = nextrecord.getString  (5);
				nextRow.IssueType    = nextrecord.getString  (6);
				nextRow.EpicName     = nextrecord.getString  (7);
				nextRow.Summary      = nextrecord.getString  (8);
				nextRow.Description  = nextrecord.getString  (9);
				nextRow.Priority     = nextrecord.getString  (10);
				nextRow.Labels       = nextrecord.getString  (11);
				nextRow.Environment  = nextrecord.getString  (12);
				nextRow.LinkedIssued = nextrecord.getString  (13);
				nextRow.Issue        = nextrecord.getString  (14);
				nextRow.Assignee     = nextrecord.getString  (15);
				nextRow.EpicLink     = nextrecord.getString  (16);
				nextRow.Sprint       = nextrecord.getString  (17);
				nextRow.TestResult   = "Pass";   // Need Oracle to determine Pass/Fail
				
				nextRow.Status       = true;
			
				//System.out.println("Next Record: " + testdata_id + "|" + nextRow.Project + "|" + nextRow.IssueType + "|" + nextRow.ActionType + "|" + nextRow.Summary + "|" + nextRow.Priority + "|" + nextRow.Labels);
			}
		}
		catch (Exception e)
		{
			System.out.println ("Failed to retrieve test data from database! Test terminating!");
			System.out.println (e);
			System.exit (-10);;
		}
	}
	
	/*
	 * insertTestResults
	 * 
	 * insert results of a single test run into TestResults 
	 * table in database.
	 * 
	 * @Pparam TBD
	 */
	public void insertTestResults (String uname, String tset, int td_id, String tsserver, String tresults)
	{
		//System.out.println ("insert test result: " + tid + "|" + results);
			
		String insertString =  "INSERT INTO TestResults (username, testset, trdatetime, testdata_id, tserver, results) VALUES (?, ?, ?, ?, ?, ?)";			
		try
		{
			PreparedStatement preparedInsertStmt = dbcon.prepareStatement (insertString);
			preparedInsertStmt.setString	(1, uname);
			preparedInsertStmt.setString	(2, tset);
			preparedInsertStmt.setTimestamp (3, Utils.getDateTimeStamp ());
			preparedInsertStmt.setInt       (4, td_id);
			preparedInsertStmt.setString    (5, tsserver);
			preparedInsertStmt.setString    (6, tresults); 
				
			preparedInsertStmt.execute();
						
			//System.out.println ("Prepared insert statement for test results: " + preparedInsertStmt);
			Utils.waitaFewSeconds (2);
		}
		catch (Exception e)
		{
			System.out.println ("Test Status insert failed! Test terminating!");
			System.out.println ("Data: " + uname + "|" + td_id + "|" + tsserver + "|" + tresults);
			e.printStackTrace ();
			System.exit (-11);
		}
	}
	
	/** getStringUIMap
	 * 
	 * return the UIMap value for the key
	 * 
	 * @param mapKey
	 * @return UI Map value
	 * 
	 */
	public String getStringUIMapData (String mapKey)
	{
		return (mapprops.getProperty (mapKey));
	}
		
	/**
	 * closeDatabaseConnection
	 * 
	 */
	public void closeDatabaseConnection ()
	{
		if (dbcon == null)
			System.out.println ("Database not open!");
		else
		{
			try
			{
				dbcon.close ();  	
				System.out.println ("Database closed");
			}
			catch (Exception e) 
			{ 
				System.out.println ("Warning! Database close failed!");
				e.printStackTrace ();
			}
		}
	}
	
	// ---------- private methods ----------
	
	/*
	 * Open the database connection. Terminate if database open fails.
	 * 
	 * @params database URL, database username and password
	 * 
	 * TO DO: modify to try 3 times to open the database
	 */
	private void openDatabaseConnection (String dbURL, String dbuname, String dbpwd)
	{
		try
		{
			// note that this is hardcoded for mysql
			Class.forName ("com.mysql.jdbc.Driver");  
			dbcon = DriverManager.getConnection (dbURL, dbuname, dbpwd);  
		}
		catch (Exception e) 
		{ 
			System.out.println ("Connection to database failed");
			e.printStackTrace ();
			System.exit (-12);
		}
	}
	
	/* 
	 * cache the UIMap table into a property list for better performance. Terminate the 
	 * program if caching fails.
	 * 
	 * Note that we concatenate pagename+fieldname and bylocator|fieldlocator so we can 
	 * use a properties list
	 * 
	 */
	private void cacheUIMap ()
	{
		String pagename, fieldname, bylocator, fieldlocator;
		mapprops = new Properties();
		
		try
		{
			Statement stmt = dbcon.createStatement ();
			ResultSet rs1 = stmt.executeQuery ("select * from UIMap");
		
			while (rs1.next ())
			{
				pagename     = rs1.getString (2);
				fieldname    = rs1.getString (3);
				bylocator    = rs1.getString (4);
				fieldlocator = rs1.getString (5);
			
				// concatenate the pagename and fieldname for the key and bylocator|fieldlocator for the value
				mapprops.put (pagename + fieldname, bylocator + "|" + fieldlocator);
			
				System.out.println ("UIMap: " + pagename + fieldname + "  " + bylocator + "|" + fieldlocator);
			}
		}
		catch (Exception e)
		{
			System.out.println ("Failed to retrieve UI Map data from database - aborting");
			e.printStackTrace ();;
			System.exit (-13);
		}
	}
}

