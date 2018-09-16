package framework;

import org.openqa.selenium.WebDriver;

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
* Phoenix Test - Login subclass (of Service class)              
*  
*  This class provides login and logout services
*  
* @author		Bob Brander
* @version	 	1.0
* @date	 	6/23/2018
* 
*/
public class Login extends Service
{
	WebDriver wdd = null;
	
	/**
	 * Login clases constructor
	 * 
	 * @param config object
	 * @param database object
	 * @param browser object
	 * 
	 */
	public Login (Config config, Database database, Browser browser)
	{
		super (config, database, browser);
	}
	
	// ----- high-level services -----
	
	/**
	 * login
	 * 
	 * login to Jira based on info from config file
	 * 
	 */
	public void login ()
	{
		System.out.println ("Logging in to Jira");
		
		wdd = myBrowser.getmyWebDriver ();
		wdd.get (myConfig.getStringProperty ("JiraServerURL"));
		Utils.waitaFewSeconds (3);
		
		try
		{
			verifyPageTitle ("DashboardDashPageTitle");
		
			enterText ("LoginLoginUserName", myConfig.getStringProperty ("JiraUser"));
			enterText ("LoginLoginPassword", myConfig.getStringProperty ("JiraPassword"));
			clickLink ("LoginLoginButton");
		
			// verify login worked - see if Create button is visible
			if (linkExists ("NavBarCreateIssueButton"))
				System.out.println ("Login Verified");
			else 
			{
				// throw Exception
				System.out.println ("Login not verified - test terminating");
				System.exit (1);
			}
		}
		catch (Exception e)
		{
			System.out.println("Login failed for user " + myConfig.getStringProperty ("JiraUser") + "! Test terminating!");
			e.printStackTrace();
			System.exit (-41);
		}
	}
	
	/**
	 * logout
	 * 
	 * Logout from Jira
	 * 
	 */
	public void logout ()
	{
		if (wdd != null)
		{	 
			waitForElement ("NavBarUserOptionsMenu");
			selectMenu ("NavBarUserOptionsMenu"); 
			selectMenuItem ("LogoutLogoutButtonText");  
	        
			if (linkExists ("LoginLoginButton"))
				System.out.println ("Logged out from Jira. Bye!");
			else
				System.out.println ("Warning: Not logged out from Jira!");
				
			wdd.quit (); 
		}
	}	
}
