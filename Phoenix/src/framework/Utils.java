package framework;

import java.util.Calendar;

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
* Phoenix Test - Utils class              
*  
*  This is a collection of misc utility classes used by other classes
*  
* @author		Bob Brander
* @version	 	1.0
* @date	 	6/23/2018
* 
*/
public class Utils
{
	/**
	 * Good old-fashioned sleep for a few seconds method
	 * 
	 * @param secs
	 * @return true unless an exception occurred
	 * 
	 */
	public static boolean waitaFewSeconds (int secs)    // should this be static?
	{
		if (secs < 1) secs = 1;
		else if (secs > 60) secs = 60;
		
		try
		{
			Thread.sleep (secs * 1000);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	} 
	
	/**
	 * Get the current date/timestamp
	 * 
	 * @return Timestamp
	 * 
	 */
	public static java.sql.Timestamp getDateTimeStamp ()
	{
	    Calendar calendar = Calendar.getInstance ();
	    return (new java.sql.Timestamp (calendar.getTime().getTime()));
	}
}
