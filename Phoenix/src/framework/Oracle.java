package framework;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.codec.binary.Base64;
import java.util.regex.*;  
//import org.json.JSONObject;
//import org.json.JSONArray;

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
* Phoenix Test - Oracle class
* 
* Use REST API to query Jira server. 
*  
* @author	 	Bob Brander
* @version	 	1.0
* @date		9/22/2018
* 
*/
public class Oracle
{
	private Config myConfig = null;
	String JiraServerURL = null;
	String JiraUser = null;
	String JiraPassword = null;
	
	/**
	 * Oracle class constructor
	 * 
	 * @param config file
	 */
	public Oracle (Config config)
	{
		myConfig = config;
		
		JiraServerURL = myConfig.getStringProperty ("JiraServerURL");
		JiraUser      = myConfig.getStringProperty ("JiraUser");
		JiraPassword  = myConfig.getStringProperty ("JiraPassword");
	}
	
	/**
	 * main provided for testing only!
	 * 
	 * @param args - path to config file
	 */
	public static void main (String [] args)
	{
		Config testConfig = new Config (args [0]);
		Oracle testOracle = new Oracle (testConfig);
		System.out.println ("Attempting REST call to Jira server");
		if (testOracle.verifyDefectInsert ("This is my first bug!"))
			System.out.println ("Summary Verified!");
		else
			System.out.println ("Summary NOT Verified!");
	}
	
	/**
	 * verifyDefectInsert - very simplistic verification of defect insert.
	 * Extract the summary of the latest defect added to the database and compare it 
	 * to the summary passed in as a parameter. Note that we are using basic 
	 * authentication for the REST API call.
	 * 
	 * @param who
	 * @param pwd
	 * @return
	 */
	public boolean verifyDefectInsert (String mySummary)
	{
		// This is based on https://developer.atlassian.com/server/jira/platform/jira-rest-api-examples/
		
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		String retVal = null;
		String jSummary = null;
		
		try 
		{
			Base64 b64 = new Base64 ();
            String encodedCreds = b64.encodeAsString (new String (JiraUser + ":" + JiraPassword).getBytes ());
            
			URL resetEndpoint = new URL (JiraServerURL + "/rest/api/2/search?jql=assignee=" + JiraUser + "+order+by+created&fields=summary&maxResults=1");
			
			connection = (HttpURLConnection) resetEndpoint.openConnection ();
			connection.setRequestMethod ("GET");
			connection.setRequestProperty  ("Authorization", "Basic " + encodedCreds);
 
			// Read the response
			reader = new BufferedReader (new InputStreamReader (connection.getInputStream ()));
			StringBuilder jsonSb = new StringBuilder ();
			String line = null;
			while ((line = reader.readLine ()) != null) 
			{
				jsonSb.append (line);
			}
			retVal = jsonSb.toString ();
 
			// parse out the summary using regex			
			Pattern pattern = Pattern.compile ("\"summary\"[:]\"(.+)\"");  // "summary":"This is my first bug!"  
		    Matcher matcher = pattern.matcher (retVal);
		    if (matcher.find ())
		    	jSummary = matcher.group (1);
		} 
		catch (Exception e) 	
		{
			e.printStackTrace ();
		} 
		finally 
		{
			// Clean up
			if (reader != null) 
			{
				try 	
				{
					reader.close ();
				} 
				catch (IOException e) 
				{
					e.printStackTrace ();
				}
			}
			if (connection != null) 
			{
				connection.disconnect ();
			}
		}
		
	return (mySummary.equals (jSummary));
	}
}
