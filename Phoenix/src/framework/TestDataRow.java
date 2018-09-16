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
 * Phoenix Test - TestDataRow class              
 *  
 * This is class is a simple class to allow passing test data read
 * from the database to any consumers of this data. The fields 
 * are a composite of all the possible fields in Jira for issues,
 * defects, and Epics.
 *  
 * @author		Bob Brander
 * @version	 	1.0
 * @date	 	6/23/2018
 * 
 */
public class TestDataRow
{
	boolean Status = false;
	int	   TestRecordID = 0;
	String ActionType = null;
	String Project = null;
	String IssueType = null;
	String EpicName = null;
	String Summary = null;
	String Description = null;
	String Priority = null;
	String Labels = null;
	String Environment = null;
	String Attachment = null;
	String LinkedIssued = null;
	String Issue = null;
	String Assignee = null;
	String EpicLink = null;
	String Sprint = null;
	String TestResult = null;
	
	/**
	 * displayTestDataRow
	 * 
	 * debugging method - display a few elements of the test data row on the console
	 * 
	 */
	public void displayTestDataRow ()
	{
		System.out.println ("Test data row: " + Project + "|" + IssueType + "|" + Summary + "|" + Description);
	}
	
	/** weHaveData
	 * 
	 * return true if we have selected another row from the test data table
	 * 
	 * @return
	 */
	public boolean weHaveData ()
	{
		return (Status);
	}
}
