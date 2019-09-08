//
// Home Travel Booking System
// Written by Jeremy Mercer
//
// File: Room/Room.java
// Description:
// 	This class, Room, represents hotel rooms, with methods such as making
// 	a room booking, checking out, and manageing a room's status.
//
package Room;

import utilities.DateTimeExtended;
import utilities.ScreenManager;
import java.io.*;

// Room class must implement Serializable so that it may be outputted to a
// serialised file.
public class Room implements Serializable
{

	// Class wide variables
	private String roomId;
	private String description;
	private char status;
	private double dailyRate;
	private double charge;
	private DateTimeExtended bookingStartDate;
	private long bookingStartTimestamp;
	private DateTimeExtended bookingEndDate;
	private long bookingEndTimestamp;
	private String customerId;


	// Consructor, sets room to available by default.
	public Room(String roomId, String description, double dailyRate)
	{
		this.roomId = roomId;
		this.description = description;
		this.dailyRate = dailyRate;
		status = 'A';
	}

	// Method to handle booking a room.
	public boolean bookRoom(String customerId, int nightsRequired)
	{

		// Ensure the room is available for booking, that the customer ID provided
		// is valid, and that the room is being booked for at least one day.
		if(roomAvailable() && customerId.matches("c[0-9]{7}") && nightsRequired > 0)
		{

			// Use the DateTimeExtended class to store the booking start and end
			// date. The DateTimeExtend class extends the DateTime class so that
			// it may be used as a serialisable object and written to file.
			// This work around will cause issues when saving the booking start and
			// end dates, so this class stores the timestamps as well as the DateTimeExtended
			// objects, so the DateTimeExtended objects can be re-constructed when
			// the program loads information from a saved file.
			bookingStartDate = new DateTimeExtended();
			bookingStartTimestamp = bookingStartDate.getTime();
			bookingEndDate = new DateTimeExtended(nightsRequired);
			bookingEndTimestamp = bookingEndDate.getTime();
			this.customerId = customerId;

			// Calculate the tentative charge for the booking.
			charge = nightsRequired * dailyRate;

			// Mark the room as booked.
			status = 'B';

			return true;
		}
		else
		{
			return false;
		}
	
	}

	// Method to handle checking out of a room.
	public boolean checkout()
	{

		// Don't proceed if the room is available for booking, or marked as unclean.
		if(roomAvailable() || status == 'U')
		{
			return false;
		}
		else
		{

			// Calculate the number of nights the room was booked for.
			int numberOfDays = getNumberOfDays();

			// Minimum charge is for one night.
			if(numberOfDays <= 1)
			{
				charge = dailyRate;
			}

			// Calculate the room charge.
			else
			{
				charge = dailyRate * numberOfDays;
			}

			// Mark the room as un-clean.
			status = 'U';

			return true;
		}

	}

	// Method to handle rooms being marked as clean.
	public boolean cleanRoom()
	{

		// Only proceed if the room is marked as un-clean.
		if(status == 'U')
		{

			// Mark the room as clean.
			status = 'A';

			return true;
		}
		else
		{
			return false;
		}

	}

	// Print the room details to screen.
	public void print()
	{
		System.out.println();
		ScreenManager.printHeader("Room summary");
		System.out.println();
		System.out.printf("%S %21s\n", "ID: ", roomId);
		System.out.printf("%S %25s\n", "Description:", description);
		System.out.printf("%S %9s (%s)\n", "Status:", status, getReadableStatus());
		System.out.println();
		
		// If the room is booked, also print booking information.
		if(!roomAvailable())
		{
			System.out.printf("%S %10s\n", "Customer ID: ", customerId);
			System.out.printf("%S %12s\n", "Booked From: ", bookingStartDate.getFormattedDate());
			System.out.printf("%S %14s\n", "Booked To: ", bookingEndDate.getFormattedDate());
			System.out.printf("%S %6s $ %(,7.2f", "Charge: ", "", charge);
			System.out.println();
		}
	}

	// Convert the room to a ":" delimitered string.
	public String toString()
	{
		String stringVersion = new String();
		stringVersion = roomId + ":" + description + ":" + status + ":" + dailyRate;

		// Don't include booking information if the room is available.
		if(roomAvailable())
		{
			stringVersion += ":null:null";
		}

		// Include booking informaton if the room is booked.
		else
		{	
			stringVersion += ":" + bookingStartDate.toString() + ":" + bookingEndDate.toString();
		}
		return stringVersion;

	}

	// Method to convert room status' to a human readable format.
	public String getReadableStatus()
	{
		String statusString = new String();
		switch(status)
		{
			case 'A':
				statusString = "Available";
				break;
			case 'B':
				statusString = "Booked";
				break;
			case 'U':
				statusString = "Un-cleaned";
				break;
		}
		return statusString;

	}

	// Method to determine if a room is available for booking.
	public boolean roomAvailable()
	{
		if(status == 'A')
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	// Method to print the room as a row in a table using the printf method.
	public void printRow(boolean header)
	{

		// Print the table header, if the instructed to do so.
		if(header)
		{
			System.out.printf("  %S %33S     %9S  %13S  %15S  %10S\n", "Room ID", "Description", "Rate", "Status", "Booked Until", "Customer");
		}

		// Print room and booking information if the room is booked.
		if(status == 'B')
		{
			System.out.printf("  %S %30s     $ %(,7.2f  %13s  %15s  %10s\n", roomId, description, dailyRate, getReadableStatus(), bookingEndDate.getFormattedDate(), customerId);
		}

		// Print room information.
		else
		{
			System.out.printf("  %S %30s     $ %(,7.2f  %13s  %15s  %10s\n", roomId, description, dailyRate, getReadableStatus(), " - ", " - ");
		}

	}

	// Overridden printRow method to be called without parameters.
	public void printRow()
	{
		printRow(false);
	}

	// Accessor method for ID.
	public String getId()
	{
		return roomId;
	}

	// Accessor method for customer ID.
	public String getCustomerId()
	{
		return customerId;
	}

	// Accessor method for description.
	public String getDescription()
	{
		return description;
	}

	// Accessor method for daily rate.
	public double getDailyRate()
	{
		return dailyRate;
	}

	// Accessor method for status.
	public char getStatus()
	{
		return status;
	}

	// Accessor method for charge.
	public double getCharge()
	{
		return charge;
	}

	// Mutator method for charge.
	public void setCharge(double charge)
	{
		this.charge = charge;
	}

	// Method to reconstruct the bookingStartDate and bookingEndDate objects, when the
	// program loads information from a serialised file - this is a work around.
	public void setCorrectDates()
	{

		// One day in milliseconds.
		final long HOURS_IN_DAY = 24L;
		final int MINUTES_IN_HOUR = 60;
		final int SECONDS_IN_MINUTES = 60;
		final int MILLISECONDS_IN_SECOND = 1000;
		long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;

		// Calculate the difference between the end booking date and now.
		long diff = bookingEndTimestamp - System.currentTimeMillis();
		int numberOfDays = (int) (diff/convertToDays) + 1;

		// Create a new DateTimeExtended object for the booking end time.
		bookingEndDate = new DateTimeExtended(numberOfDays);

		// Calculate the difference between the start booking date and now.
		diff = bookingStartTimestamp - System.currentTimeMillis();
		numberOfDays = (int) (diff/convertToDays);

		// Create a new DateTimeExtended object for the booking start time.
		bookingStartDate = new DateTimeExtended(numberOfDays);

	}

	// Method to calculate the number of days a room is booked for.
	public int getNumberOfDays()
	{

		// Only allow this to happen if the room is not available.
		if(!roomAvailable())
		{

			// Create a new DateTimeExtended object (for now).
			DateTimeExtended now = new DateTimeExtended();

			// Use the diffDays method to calculate the number of days.
			int numberOfDays = DateTimeExtended.diffDays(now, bookingStartDate);
			return numberOfDays;
		}
		else
		{
			return 0;
		}

	}
}
