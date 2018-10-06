package framework;

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
 * Phoenix Test - User class              
 * 
 * This class is the main class that runs the actual tests
 * 
 * @author		Bob Brander
 * @version	 	1.0
 * @date	 	6/23/2018
 * 
 * @param command-line arg: config file path/location
 * 
 */
public class User
{
	private Config   myConfig = null;
	private Database myDatabase = null;
	private Browser  myBrowser = null;
	private Oracle   myOracle = null;
	private Login    myLogin = null;
	private Defects  myDefects = null;
	public  TestDataRow myNextRow = null;
	
	/**
	 * main
	 *  
	 * Run our tests - login, run test, logout, clean up
	 * 
	 */
	public static void main (String [] args)
	{
		if (args.length != 1)
		{
			System.out.println ("Usage: User <config file>");
			System.exit (1);
		}
		
		User user1 = new User (args [0]);
		
		user1.login ();
		user1.runTest ();
		user1.logout ();
		user1.cleanup ();
	}
	
	/**
	 * User constructor
	 * 
	 * @param ConfigFile
	 */
	public User (String ConfigFile)
	{
		myConfig = new Config (ConfigFile);
		myConfig.printProperties ();
		
		myDatabase = new Database (myConfig);
		myBrowser  = new Browser (myConfig);
		myOracle   = new Oracle (myConfig);
		
		// Create services
		myLogin = new Login (myConfig, myDatabase, myBrowser);
		
		myDefects = new Defects (myConfig, myDatabase, myBrowser, myOracle);
		myNextRow = new TestDataRow ();
	}
		
	// ----- private methods -----
	
	/**
	 * Log in to Jira using the login service
	 */
	private void login ()
	{
		myLogin.login ();
	}
	
	/** 
	 * Run the test by reading test data from a table in the database
	 * and acting on each row.
	 */
	public void runTest ()
	{
		System.out.println ("Running Test");
		//myDefects.navigateToSearchPage ();
		
		
		// read in test data from TestData table and perform the appropriate
		// action (add, update, or delete a defect). Note that we are passing
		// each test data row in the TestDataRow object. 
		
		// cache the testset name so we don't have to look it up each time through the loop
		String testsetName = myConfig.getStringProperty ("testset");
		myDatabase.getNextTestData (myNextRow, testsetName);
		while (myNextRow.weHaveData ())
		{
			switch (myNextRow.IssueType)
			{
				case "defect":
				{
					myDefects.processDefect (myNextRow);
					break;
				}
			
				default:
				{
					System.out.println("Unknown issue type " + myNextRow.IssueType);
				}
			}
			
			myDatabase.getNextTestData (myNextRow, testsetName);
		}
	}
	
	/**
	 * logout from Jira using the Login service
	 */
	private void logout ()
	{
		// add a check to verify we are still logged in...
		
		myLogin.logout ();
	}
	
	/**
	 * Close webdriver, database
	 */
	private void cleanup ()
	{
		myBrowser.closeWebdriver ();
		myDatabase.closeDatabaseConnection ();
	}
}
