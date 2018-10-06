package framework;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

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
 * Phoenix Test - Service superclass              
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
 */
public class Service
{
	public Config myConfig = null;
	public Database myDatabase = null;
	public Browser myBrowser = null;
	private WebDriver myWebDriver = null;
	
	public Service (Config sconfig, Database sdatabase, Browser sbrowser)
	{
		myConfig   = sconfig;
		myDatabase = sdatabase;
		myBrowser  = sbrowser;     // do we need to keep this if we store the webdriver?
		myWebDriver = myBrowser.getmyWebDriver ();
	}
	
	// ----- low-level services -----
	
	// Should these methods throw an exception if they fail? I think WebDriver will throw exceptions...
	
	/**
	 * Enter text that comes from the config file, such as the user name or password,
	 * into the field specified by the UI Map locator
	 * 
	 * @param mapLocator (bylocator | fieldlocator)
	 * @param configLookup - field to look up in Config file
	 */
	public void enterConfigText (String mapLocatorKey, String configLookup)
	{
		String myMapLocator = myDatabase.getStringUIMapData (mapLocatorKey);
		String texttoEnter = myConfig.getStringProperty (configLookup);
		String [] mylocator = myMapLocator.split ("\\|");  // split bylocator and fieldlocator
		
		//System.out.println ("enterText id: " + mylocator [0] + " locator: " + mylocator [1] + " text: " + texttoEnter);
		
		if (mylocator [0].equals ("id"))
		{
			myWebDriver.findElement (By.id (mylocator [1])).sendKeys (texttoEnter);
		}
		
		// TODO: add other "by" locators, such as "css"
	}
	
	/**
	 * Enter text passed in as a param to the field specified by the UIMap Locator
	 * 
	 * Note that the text to enter is hardcoded and not stored in UIMap...
	 * 
	 * @param mapLocator key (bylocator|fieldlocator)
	 * @param text to enter
	 */
	public void enterText (String mapLocatorKey, String enterMe)
	{
		String myMapLocator = myDatabase.getStringUIMapData (mapLocatorKey);
		String [] myLocator = myMapLocator.split ("\\|");  // split bylocator and fieldlocator
		
		//System.out.println ("enterText id: " + myLocator [0] + "| locator: " + myLocator [1] + "| text: " + enterMe);
		
		if (myLocator [0].equals ("id"))
		{
			//WebDriverWait wait = new WebDriverWait (myWebDriver, 15);
			//wait.until (ExpectedConditions.visibilityOfElementLocated (By.id (myLocator [1])));
			
			myWebDriver.findElement (By.id (myLocator [1])).sendKeys (enterMe);
		}
	}
	
	public void enterReturn (String mapLocatorKey)
	{
		String myMapLocator = myDatabase.getStringUIMapData (mapLocatorKey);
		String [] myLocator = myMapLocator.split ("\\|");  // split bylocator and fieldlocator
		
		if (myLocator [0].equals ("id"))
			myWebDriver.findElement (By.id (myLocator [1])).sendKeys (Keys.ENTER);
	}
	
	/**
	 * selectDropdownItem - select a drop-down list by entering the text
	 * 
	 * @param dropdown list locator key, drop down item
	 * 
	 */
	public void selectDropdownItem (String mapDropdownLocatorKey, String dropdownItem)
	{	
		String dropdownLocator = myDatabase.getStringUIMapData (mapDropdownLocatorKey);
		String [] dropdownLocatorSplit = dropdownLocator.split ("\\|");
	
		WebElement myDropdown = myWebDriver.findElement (By.id (dropdownLocatorSplit [1]));
		myDropdown.click ();
		myDropdown.sendKeys (dropdownItem);
		myDropdown.sendKeys (Keys.ENTER);
	}
	
	/**
	 * selectMenu
	 * 
	 * @param menu locator key
	 * 
	 */
	public void selectMenu (String mapMenuLocatorKey)
	{
		String menuLocator = myDatabase.getStringUIMapData (mapMenuLocatorKey);
		String [] menuLocatorSplit = menuLocator.split ("\\|");
		
		WebElement myMenu = null;
		
		if (menuLocatorSplit [0].equals ("id"))
			myMenu = myWebDriver.findElement (By.id (menuLocatorSplit [1]));
		else if (menuLocatorSplit [0].equals ("class"))
			myMenu = myWebDriver.findElement (By.className (menuLocatorSplit [1]));
		
		myMenu.click ();
	}
	
	/**
	 * selectMenuItem
	 * 
	 * @param menu item locator key
	 * 
	 */
	public void selectMenuItem (String mapMenuItemLocatorKey)
	{
		String menuItemLocator = myDatabase.getStringUIMapData (mapMenuItemLocatorKey);
		String [] menuItemLocatorSplit = menuItemLocator.split ("\\|");
		
		WebElement myMenuItem = myWebDriver.findElement (By.linkText (menuItemLocatorSplit [1]));
		myMenuItem.click ();
	}
	
	/**
	 * clickLink
	 * 
	 * @param UI Map link locator key
	 */
	public void clickLink (String mapLinkLocatorKey)
	{
		//System.out.println (mapLinkLocator);
		
		String myMapLocator = myDatabase.getStringUIMapData (mapLinkLocatorKey);
		String [] myLocator = myMapLocator.split("\\|"); // split bylocator and fieldlocator
		
		//System.out.println ("clickLink id: " + myLocator [0] + " locator: " + myLocator [1]);
		
		if (myLocator [0].equals ("name"))
		{
			myWebDriver.findElement (By.name (myLocator [1])).click ();
		}
		else if (myLocator [0].equals ("id"))
		{
			myWebDriver.findElement (By.id (myLocator [1])).click ();
		}
	}
	
	/**
	 * buttonExists
	 * 
	 * @param button locator key
	 */
	public boolean buttonExists (String buttonLocatorKey)
	{	
		String createButtonLocator = myDatabase.getStringUIMapData (buttonLocatorKey);
		String [] createButton = createButtonLocator.split("\\|");
				
		waitForElement (buttonLocatorKey);
		
		if (createButton [0].equals ("id"))
		{
			WebElement CreateButton = myWebDriver.findElement (By.id (createButton [1]));
			if (CreateButton.isDisplayed ())
			{
				//Utils.waitaFewSeconds (3);
				return (true);
			}
		}
		
		return (false);
	}
	
	/**
	 * verifyPageTitle
	 * 
	 * @param page title locator key
	 */
	public boolean verifyPageTitle (String pageTitleLocatorKey)
	{
		//System.out.println ("Verify page title " + pageTitleLocatorKey);
		
		String locator = myDatabase.getStringUIMapData (pageTitleLocatorKey);
		String [] myTitleLocator = locator.split ("\\|");  // split bylocator and fieldlocator
		
		if (myTitleLocator [0].equals ("title") && myWebDriver.getTitle ().equals (myTitleLocator [1]))
			return (true);
		else
			return (false);
	}
	
	/** 
	 * linkExists
	 * 
	 * @param link locator key
	 */
	public boolean linkExists (String locatorKey)
	{
		String link = myDatabase.getStringUIMapData (locatorKey);
		String [] myLinkLocator = link.split ("\\|");  // split bylocator and fieldlocator
		
		switch (myLinkLocator [0])
		{
			case "text":
			{
				WebElement target = myWebDriver.findElement (By.linkText (myLinkLocator [1]));
				return (target.isDisplayed ());
			}
			
			case "id":
			{
				WebElement target = myWebDriver.findElement (By.id (myLinkLocator [1]));
				return (target.isDisplayed ());
			}
			
			default:
			{
				// Throw an exception
				return (false);
			}
		}
	}
	
	public void selectRadioButton ()
	{
		System.out.println ("Select Radio button");
	}
	
	public void checkbox ()
	{
		System.out.println ("Check Box");
	}
	
	public void uncheckbox ()
	{
		System.out.println ("Uncheck Box");
	}
	
	/**
	 * waitForElement - theoretically this waits for a specific element to be
	 * visible on the sreen (but I'm not sure if it works...)
	 * 
	 * @param mapLocatorKey
	 */
	public void waitForElement (String mapLocatorKey)
	{	
		String myMapLocator = myDatabase.getStringUIMapData (mapLocatorKey);
		String [] myLocator = myMapLocator.split ("\\|");  // split bylocator and fieldlocator
		
		// make this more generic - check myLocator [0] and use different "By" locators 
		
		WebDriverWait wait = new WebDriverWait (myWebDriver, 15);
		if (myLocator [0].equals ("id"))
			wait.until (ExpectedConditions.visibilityOfElementLocated (By.id (myLocator [1]))); 
		else if (myLocator [0].equals ("class"))
			wait.until (ExpectedConditions.visibilityOfElementLocated (By.className (myLocator [1])));
	}
	
	// I sometimes see errors like this: "Element <element> is not clickable at point <x> because another
	// element <div class="aui-blanket" obscures it" when moving between actions (such as after adding a 
	// defect and trying to add another). Can we check to see if this "blanket" is visible and wait for it to go away?
	// Currently I don't use this but I'm leaving it here in case I want to try it again.
	public void waitForBlanket ()
	{	
		WebDriverWait wait = new WebDriverWait (myWebDriver, 15);
		wait.until (ExpectedConditions.invisibilityOfElementLocated (By.className ("aui-blanket"))); 
	}
}
