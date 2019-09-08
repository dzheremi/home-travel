//
// Home Travel Booking System
// Written by Jeremy Mercer
//
// File: utilities/DateTimeExtended.java
// Description:
// 	Very simple class that extends the DateTime class, but allows it to
// 	implement Serializable so that it may be saved to a serialised file.
//
package utilities;

import java.io.*;

public class DateTimeExtended extends DateTime implements Serializable
{

	public DateTimeExtended()
	{
		super();
	}

	public DateTimeExtended(int setClockForwardInDays)
	{
		super(setClockForwardInDays);
	}

}
