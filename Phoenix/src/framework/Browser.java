package framework;

import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.firefox.FirefoxDriver;
import framework.Utils;

/* 
    Copyright (C) 2018  Bob Brander
 
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
 * Phoenix Test - Webdriver/Browser class 
 *   
 * There are a number of improvements needed here:
 * 
 *  - don't use Utils.waitafewseconds
 *  - don't use explicit waits (use fluent waits?)
 *  - add support for different browsers and browser capabilities
 *  
 * @author	 	Bob Brander
 * @version	 	1.0
 * @date		6/23/2018
 * 
 */
public class Browser
{
	private WebDriver mywebdriver = null;

	/**
	 * Browser class constructor
	 * 
	 * @param myConfig object
	 */
	public Browser (Config myConfig)
	{	
		String browserType = myConfig.getStringProperty ("browser");
		
		if (browserType.equals ("Firefox"))
		{
			System.setProperty ("webdriver.gecko.driver", myConfig.getStringProperty ("BrowserDriver"));
			mywebdriver = new FirefoxDriver ();
			if (mywebdriver == null)
			{
				System.out.println ("Webdriver Firefox driver failed to load. Test terminating");
				System.exit (-20);
			}
		}
		else
		{
			System.out.println("Unkown or unsupported browser type: " + browserType + ". Test terminating");
			System.exit (-21);
		}
		
		mywebdriver.manage().timeouts().implicitlyWait (10, TimeUnit.SECONDS);
	}
	
	/**
	 * getmyWebDriver
	 * 
	 * @return web driver
	 * 
	 */
	public WebDriver getmyWebDriver ()
	{
		return (mywebdriver);
	}
	
	/**
	 * closeWebdriver
	 * 
	 */
	public void closeWebdriver ()
	{
		if (mywebdriver != null)
			mywebdriver.quit ();
	}
}
