package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
 * Phoenix Test - Config class              
 * 
 * Read the config file. The config file contains key:value pairs. The 
 * first parameter in the config file must be the number of properties in the config file.
 * The default constructor takes no parameters and will try to find a default config file in the
 * current directory. The second constructor takes a path/filename to a config file.
 * 
 * This class will terminate the program if the config file can't be read.
 * 
 * @author: Bob Brander
 * @version: 1.0
 * @date: 6/23/2018
 * 
 */
public class Config
{
	private Properties props;
	private String pConfigFile;

	/*
	 *  main is for testing only - note that this is not very comprehensive testing,
	 *  as invalid config files will cause the program to terminate. Comment out 2 of the
	 *  invalid format tests for any given test run.
	 */
	public static void main (String [] args)
	{
		System.out.println ("Test 1: valid config file");
		Config validConfig = new Config (args [0]);
		validConfig.printProperties ();
		
		//System.out.println ("Test 2: invalid config file path");
		//Config invalidConfig = new Config ("C:\\invalid\\path\\config.properties");
		//invalidConfig.printProperties ();
		
		//System.out.println ("Test 3: invalid config file format");
		//Config invalidConfig = new Config ("C:\\Bob\\Workspace\\Java\\Selenium\\Phoenix\\Config\\InvalidFormatConfig.properties");
		//invalidConfig.printProperties ();
		
		System.out.println ("Test 4: incorrect arg count");
		Config invalidConfig = new Config ("C:\\Bob\\Workspace\\Java\\Selenium\\Phoenix\\Config\\InvalidNumPropertiesConfig.properties");
		invalidConfig.printProperties ();
	}
	
	/*
	 * Default constructor - try to find a default properties file in current directory
	 */
	public Config ()
	{
		pConfigFile = "Config.properties";
		verifyConfigFile ();
		readConfigFile ();
	}
	
	/*
	 *  main constructor with user-specified properties file
	 *  
	 *  @params String myConfigFile - <path>/<config file name> 
	 */
	public Config (String myConfigFile)
	{
		pConfigFile = myConfigFile;
		verifyConfigFile ();
		readConfigFile ();
	}
	
	/*
	 *  provide a key, get a property
	 *  
	 *  @param String containing key to look up
	 *  @return String containing value for the supplied key (or null if none)
	 */
	public String getStringProperty (String thekey)
	{
		return (props.getProperty (thekey));
	}
	
	/*
	 * convenience method for debugging - print out all properties in the list
	 */
	public void printProperties ()
	{
		props.list (System.out);
	}
	
	// ----- private helper methods -----
	
	/* 
	 * verify the config file is a valid file and we have read permission
	 */
	private void verifyConfigFile ()
	{
		try
		{
			File fConfigFile = new File (pConfigFile);
			fConfigFile.exists ();
			fConfigFile.isFile ();
			fConfigFile.canRead ();
			System.out.println ("Found config file " + pConfigFile);	
		}
		catch (Exception e)
		{
			System.out.println ("Error verifying config file " + pConfigFile + ". Test aborting");
			e.printStackTrace ();
			System.exit (-1);
		}
	}
		
	/* 
	 * Read the config file and parse the contents into a property list
	 * 
	 * Throw an exception if config file can't be read
	 */
	private void readConfigFile ()
	{
		props = new Properties ();
		InputStream input = null;

		try 
		{
			input = new FileInputStream (pConfigFile);
			props.load (input);
		} 
		catch (IOException ex) 
		{
			System.out.println ("Error reading config file " + pConfigFile + ". Test aborting");
			ex.printStackTrace ();
			System.exit (-1);
		} 
		finally 
		{
			if (input != null) 
			{
				try 
				{
					input.close();
				} 
				catch (IOException e) 
				{
					System.out.println ("Warning: config file " + pConfigFile + " not closed!");
					e.printStackTrace ();
				}
			}
		}
			
		try 
		{
			if (props.size () != Integer.parseInt (props.getProperty ("numProperties")))
				throw new IllegalArgumentException ();    // what should we throw here?
		}
		catch (Exception ee)
		{
			System.out.println ("Error reading config file " + pConfigFile + ". Test aborting");
			ee.printStackTrace ();
			System.exit (-2);
		}
	}
}
