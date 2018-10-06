package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
 * Phoenix Test - Defects subclass (of Service class)              
 *  
 *  This is an experimental use of inheritance. This superclass provides 
 *  common low-level methods (such as click a link or enter text),
 *  while the subclasses will provide higher-level services 
 *  (such as create a new issue in Jira) built form the superclass
 *  low-level services. 
 *  
 *  Possible modification to superclass - change to abstract class?
 *  
 * @author		Bob Brander
 * @version	 	1.0
 * @date	 	6/23/2018
 * 
 * 
 */
public class Defects extends Service
{
	Oracle myOracle = null;
	WebDriver wdd = null;
	
	/**
	 * Defects constructor
	 * 
	 * @param config file
	 * @param database object
	 * @param browser object
	 * 
	 */
	public Defects (Config config, Database database, Browser browser, Oracle oracle)
	{
		super (config, database, browser);
		wdd = browser.getmyWebDriver ();
		myOracle = oracle;
	}
	
	// ----- high-level services -----
	
	/**
	 * processDefect
	 * 
	 * Process a defect (insert it into Jira)
	 * 
	 * @param myDefect
	 */
	public void processDefect (TestDataRow myDefect)
	{
		if (myDefect.ActionType.equals ("insert"))
			addDefect (myDefect);
		else if (myDefect.ActionType.equals ("update"))
			updateDefect (myDefect);
		else if (myDefect.ActionType.equals ("delete"))
			deleteDefect (myDefect);   
		else
		{
			System.out.println ("Unknown action type " + myDefect.ActionType);
			myDefect.displayTestDataRow ();
		}
	}
	/** 
	 * addDefect
	 * 
	 * insert a defect 
	 * 
	 * @param test data to insert
	 */
	public void addDefect (TestDataRow next)
	{
		System.out.println ("insert defect " + next.Summary);
		
		waitForElement ("NavBarCreateIssueButton");
		clickLink ("NavBarCreateIssueButton");
		
		// verify we are on create issue dialog page - see if "Configure Fields" button exists
		if (!linkExists ("CreateDialogConfigureFields"))
		{
			System.out.println("Not on create issue dialog page - test terminating!");
			System.exit (1);
		}
		
		// enter fields for a new Bug
		
		// set issue type to "Bug" (we can assume that if we got to this point we are inserting a defect)
		selectDropdownItem ("CreateDialogIssueType", "Bug");
		Utils.waitaFewSeconds (3);
		
		waitForElement ("CreateDialogSummary");
		enterText ("CreateDialogSummary", next.Summary); 
		
		// need this because we have to wait for frame to become visible (and waitForElement won't do that
		// because we are waiting on something in a frame
		Utils.waitaFewSeconds (3);
		
		wdd.switchTo ().frame (1);                  // hardcoded frame # - need to fix this somehow
		enterText ("CreateDialogDescription", next.Description);
		wdd.switchTo ().defaultContent ();
		
		selectDropdownItem ("CreateDialogPriority", next.Priority);
		
		wdd.switchTo ().frame (2);
		enterText ("CreateDialogEnvironment", next.Environment);
		wdd.switchTo ().defaultContent ();
		
		selectDropdownItem ("CreateDialogAssignee", next.Assignee);
		
		// click "Save" (or submit or whatever)
		waitForElement ("NavBarCreateIssueButton");
		clickLink ("CreateDialogSubmitButton");
		
		// insert test results
		String status = null;
		if (myOracle.verifyDefectInsert (next.Summary))
			status = "Pass";
		else
			status = "Fail";
			
		myDatabase.insertTestResults (myConfig.getStringProperty ("JiraUser"), myConfig.getStringProperty ("testset"), next.TestRecordID, myConfig.getStringProperty ("JiraServerName"), status);
	}
	
	public void updateDefect (TestDataRow next)
	{
		System.out.println ("update defect " + next.Summary);
	}
	
	public void deleteDefect (TestDataRow next)
	{
		System.out.println ("delete defect " + next.Summary);
	}
	
	public void navigateToSearchPage ()
	{
		// go to Issues -> Current Search
		selectMenu ("HomepageIssuesMenu");
		selectMenuItem ("SearchCurrentsearch");
		waitForElement ("SearchOrderby");
		
		// Order by "Created"
		selectMenu ("SearchOrderby");
		Utils.waitaFewSeconds(5);
		enterText ("SearchCreator", "Created");
		Utils.waitaFewSeconds(1);
		enterReturn ("SearchCreator");
		Utils.waitaFewSeconds(5);
		clickLink ("SearchChangeOrderby");
		Utils.waitaFewSeconds(5);
	}
	
	public void refreshSearchPage ()
	{
		
	}
	
	public String getLatestDefectKey ()
	{
		return ("Dummy");
	}
}
