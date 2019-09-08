//
// Home Travel Booking System
// Written by Jeremy Mercer
//
// File: Test/Menu.java
// Description:
// 	This class, Menu, is designed to be statically called to provision the
// 	console based menu interface for the program. It relies heavily on
// 	another class called ScreenManager for common standard IO operations.
//
package Menu;

import java.util.Scanner;
import utilities.ScreenManager;
import Room.Room;
import Room.PremiumRoom;
import java.io.*;

public class Menu
{

	// Overloaded method used to first load room and booking information from file, then
	// parse an array of Rooms to the runMainMenu() method.
	public static Room[] runMainMenu() throws IOException, FileNotFoundException, ClassNotFoundException
	{
		Room[] rooms = loadRooms();
		rooms = runMainMenu(rooms);
		return rooms;
	}

	// This method is used to run the programs main menu.
	public static Room[] runMainMenu(Room[] rooms)
	{

		// These final static variables are used to store the minimum and maximum menu 
		// selection options.
		final int MIN_OPTION = 0;
		final int MAX_OPTION = 5; 

		int response = -1;
		boolean validResponse = false;

		// The following loop will iterate until a user enters a valid selection from
		// the displayed menu.
		do
		{

			// Print the main menu to screen.
			printMainMenu();
			System.out.print("Please make your selection: ");

			// Error handeling for invalid responses (non-integers) from users.
			try
			{

				// Read the menu selection from keyboard.
				Scanner console = new Scanner(System.in);
				response = console.nextInt();
				validResponse = true;

			}

			// Catch any invalid inputs from keyboard, forcing the loop to re-
			// iterate.
			catch(java.util.InputMismatchException e)
			{
				validResponse = false;
			}
		}
		while(response < MIN_OPTION || response > MAX_OPTION || !validResponse);

		// Determine the approprite method or action to take given a valid menu
		// option.
		switch(response)
		{
			// 1. Book Room
			case 1:
				rooms = bookRoom(rooms);
				break;

			// 2. Checkout of a Room
			case 2:
				rooms = checkoutRoom(rooms);
				break;

			// 3. Mark a Room as Clean
			case 3:
				rooms = cleanRoom(rooms);
				break;

			// 4. View the Status of All Rooms
			case 4:
				viewRooms(rooms);
				break;

			// 5. Search for Rooms by Price Range
			case 5:
				searchRoomsByPrice(rooms);
				break;

			// 0. Exit the System
			case 0:

				// On exit, print the status of all Room objects in the rooms array
				// using the toString() method.
				ScreenManager.printNotice("Printing status of all rooms");
				for(int i=0; i < rooms.length; i++)
				{
					System.out.println(rooms[i].toString());
				}

				// Save all Room objects in the rooms array to the rooms.dat file.
				ScreenManager.printNotice("Saving room information to file");
				saveRooms(rooms);
				ScreenManager.printNotice("Goodbye!");

				// Exit the program.
				System.exit(0);
				break;

		}

		// Return the rooms array back to the drive class.
		return rooms;
	}

	// This method prints the main menu to screen.
	public static void printMainMenu()
	{
		ScreenManager.clear();
		ScreenManager.printProgramTitle();
		ScreenManager.printHeader("Main Menu");
		ScreenManager.printMenuItem(1, "Book a room");
		ScreenManager.printMenuItem(2, "Checkout of a room");
		ScreenManager.printMenuItem(3, "Mark room as cleaned");
		ScreenManager.printMenuItem(4, "View all rooms");
		ScreenManager.printMenuItem(5, "Search for a room by price range");
		System.out.println();
		ScreenManager.printMenuItem(0, "Exit system");
		ScreenManager.printFooter();
	}

	// Method for handling the console interface when booking a room.
	public static Room[] bookRoom(Room[] rooms)
	{
		ScreenManager.clear();
		ScreenManager.printProgramTitle();
		ScreenManager.printHeader("Create room booking");

		// Call the roomSelection method, which will return the array index of a
		// room selected by the user.
		int roomArrayIndex = roomSelection(rooms, 1);

		// Determine if the room is currently available for booking.
		if(rooms[roomArrayIndex].roomAvailable())
		{

			boolean booked = false;

			// Loop while a booking has not been successful (initial case).
			while(!booked)
			{
				System.out.println();
				boolean validCustomer = false;
				String customerId = new String();

				// Loop while an invalid customer ID has been provided (initial case).
				while(!validCustomer)
				{
					
					// Read customer ID from keyboard.
					ScreenManager.printPrompt(2, "Enter customer ID");
					Scanner console = new Scanner(System.in);
					customerId = console.next();

					// Convert the inputted customer ID to lower case.
					customerId = customerId.toLowerCase();
					System.out.println();

					// Check the entered customer ID using the following regular expression.
					// This ensures it starts with a lower case 'c' followed by 7 digits.
					if(customerId.matches("c[0-9]{7}"))
					{

						// Exit the loop if the customer ID is valid.
						validCustomer = true;
					}
					else
					{
						ScreenManager.printWarning("That is not a valid customer ID, please try again");
					}
				}

				boolean validResponse = false;
				int numberOfNights = 0;

				// Do-while loop, until a valid number of nights has been provided.
				do
				{

					// Error handeling for invalid number of nights (non-integer values).
					try
					{

						// Read the number of nights from the keyboard.
						ScreenManager.printPrompt(3, "Number of nights required");
						Scanner console = new Scanner(System.in);
						numberOfNights = console.nextInt();
						validResponse = true;

						// The number of nights cannot be less than 1.
						if(numberOfNights < 1)
						{
							ScreenManager.printWarning("That is not a valid number of nights, please try again");
							validResponse = false;
						}
					}

					// Catch any invalid (non-integer) inputs from the keyboard to force the
					// loop to re-iterate.
					catch(java.util.InputMismatchException e)
					{
						ScreenManager.printWarning("That is not a valid number of nights, please try again");
					}
				}
				while(!validResponse);

				// Voucher and discount handeling for the PremiumRoom class.
				if(rooms[roomArrayIndex] instanceof PremiumRoom)
				{

					// Prompt the user for a valid voucher, loops until user enters 'y' or 'n' case
					// insensitive.
					String voucher;
					do
					{

						// Read response from keyboard.
						System.out.println();
						ScreenManager.printPrompt(4, "Does the customer have a valid voucher? [y/n]");
						Scanner console = new Scanner(System.in);
						voucher = console.next();
					}
					while(!voucher.toLowerCase().equals("y") && !voucher.toLowerCase().equals("n"));

					// If the customer has a valid voucher, read the amount.
					if(voucher.toLowerCase().equals("y"))
					{

						// Loop until the user enters a valid voucher amount (double).
						validResponse = false;
						double voucherAmount = 0.00;
						do
						{

							// Error handeling for invalid voucher amounts (non-double values).
							try
							{

								// Read voucher amount from keyboard.
								System.out.println();
								ScreenManager.printPrompt(5, "Enter voucher amount");
								Scanner console = new Scanner(System.in);
								voucherAmount = console.nextDouble();
								validResponse = true;
							}

							// Catch any non-valid voucher amounts (non-doubles) and force the loop
							// to re-iterate.
							catch(java.util.InputMismatchException e)
							{
								ScreenManager.printWarning("That is not a valid voucher amount, please try again");
							}
						}
						while(!validResponse);

						// Book the room - which has been casted to a PremiumRoom object to ensure the correct
						// bookRoom method is called.
						booked = ((PremiumRoom)rooms[roomArrayIndex]).bookRoom(customerId, numberOfNights, voucherAmount);
					}

					// The customer does not have a voucher.
					else
					{

						// Book the room - which has been casted to a PremiumRoom object to ensure the correct
						// bookRoom method is called.
						booked = ((PremiumRoom)rooms[roomArrayIndex]).bookRoom(customerId, numberOfNights);
					}
				}

				// Handle non-premium room bookings.
				else
				{
					booked = rooms[roomArrayIndex].bookRoom(customerId, numberOfNights);
				}

				// Print the room information if the room was successfully booked.
				if(booked)
				{
					rooms[roomArrayIndex].print();
					ScreenManager.printNotice("Room has been booked successfully");
					ScreenManager.printFooter();
					ScreenManager.pause();
				}

				// Warn the user than the room could not be booked, loop will re-iterate.
				else
				{
					ScreenManager.printWarning("Cannot book this room, please the details and try again");
					ScreenManager.printFooter();
					ScreenManager.pause();
				}
			}
		}

		// If the room is not available, tell the user and return to the main menu.
		else
		{
			ScreenManager.printWarning("Sorry, this room is currently not avaialble for booking");
			ScreenManager.pause();
		}

		// Return the array of rooms (modified) that was originally passed to this method.
		return rooms;
	}

	// Method for handling the console interface when checkout out of a room.
	public static Room[] checkoutRoom(Room[] rooms)
	{
		ScreenManager.clear();
		ScreenManager.printProgramTitle();
		ScreenManager.printHeader("Room checkout");

		// Select the array index of the room to be checked out.
		int roomArrayIndex = roomSelection(rooms, 1);

		// Attempt to check out the room, and prompt the user of the outcome.
		if(rooms[roomArrayIndex].checkout()){
			rooms[roomArrayIndex].print();
			ScreenManager.printNotice("Room has been checked out successfully");
			ScreenManager.printFooter();
			ScreenManager.pause();
		}
		else
		{
			ScreenManager.printWarning("This room is not currently booked");
			ScreenManager.printFooter();
			ScreenManager.pause();
		}

		// Return the array of rooms (modified) that was originally passed to this method.
		return rooms;
	}

	// Method for handling the console interface when marking a room as cleaned.
	public static Room[] cleanRoom(Room[] rooms)
	{
		ScreenManager.clear();
		ScreenManager.printProgramTitle();
		ScreenManager.printHeader("Mark Room as Cleaned");

		// Select the array index of the room to be marked as cleaned.
		int roomArrayIndex = roomSelection(rooms, 1);

		// Attempt to mark the room as cleaned, and prompt the user of the outcome.
		if(rooms[roomArrayIndex].cleanRoom())
		{
			rooms[roomArrayIndex].print();
			ScreenManager.printNotice("Room has been marked as clean");
			ScreenManager.printFooter();
			ScreenManager.pause();
		}
		else
		{
			ScreenManager.printWarning("This room is not currently marked as un-clean");
			ScreenManager.printFooter();
			ScreenManager.pause();
		}

		// Return the array of rooms (modified) that was originally passed to this method.
		return rooms;
	}

	// Method to handle the console interface to search for a room.
	public static void searchRoomsByPrice(Room[] rooms)
	{

		// Variable to determine the first result output, so headers can be printed.
		boolean first = true;

		boolean validResponse = false;

		// Variable for the number of results found.
		int results = 0;

		// Variables for the price bracket to search for.
		double minPrice = 0.00;
		double maxPrice = 0.00;

		ScreenManager.clear();
		ScreenManager.printProgramTitle();
		ScreenManager.printHeader("Search for room by price");

		// Loop until user enters a valid price bracket.
		do
		{

			// Error handling for invalid price bracket entries (non-doubles).
			try
			{

				// Prompt user for a price bracket and read from keyboard.
				ScreenManager.printPrompt(1, "Enter minimum price range");
				Scanner console = new Scanner(System.in);
				minPrice = console.nextDouble();
				System.out.println();
				ScreenManager.printPrompt(2, "Enter maximum price range");
				maxPrice = console.nextDouble();
				System.out.println();

				// Ensure the brackets are both above 0.
				if(minPrice > 0 && maxPrice > 0)
				{
					validResponse = true;
				}
				else
				{
					ScreenManager.printWarning("That is not a valid price range");
				}
			}

			// Catch any non valid (non-double) price brackets, and re-iterate the loop.
			catch(java.util.InputMismatchException e)
			{
				ScreenManager.printWarning("That is not a valid price range");
			}
		}
		while(!validResponse);

		// Loop through all rooms in the rooms array.
		for(int i=0; i < rooms.length; i++)
		{

			// If this room matchs the entered price bracket.
			if(rooms[i].getDailyRate() >= minPrice && rooms[i].getDailyRate() <= maxPrice)
			{
				results++;
				if(first)
				{

					// Print the room details as a row (including table headers).
					rooms[i].printRow(true);
					first = false;
				}
				else
				{

					// Print the room details as a row (without table headers).
					rooms[i].printRow();
				}
			}
		}

		// Print a message if there are no matching results.
		if(results == 0)
		{
			ScreenManager.printNotice("No rooms found in that price range");
		}

		// If there are results, tell the user how many where found.
		else
		{
			ScreenManager.printNotice("Total of " + results + " room(s) found within that price bracket");
		}
		ScreenManager.printFooter();
		ScreenManager.pause();

	}

	// Method to view all room informatuon in a table format.
	public static void viewRooms(Room[] rooms)
	{

		// Variable used to determine the first row of the table, so headers can be printed.
		boolean first = true;

		ScreenManager.clear();
		ScreenManager.printProgramTitle();
		ScreenManager.printHeader("Status of all rooms");

		// Loop through all rooms in the rooms array.
		for(int i=0; i < rooms.length; i++)
		{
			if(first)
			{

				// Print the first row, with table headings.
				rooms[i].printRow(true);
				first = false;
			}
			else
			{

				// Print a row, without table headings.
				rooms[i].printRow();
			}
		}
		System.out.println();
		ScreenManager.printFooter();
		ScreenManager.pause();
	}

	// Method called by other Menu class methods to allow a user to select a room.
	// Returns the array index of the matching room.
	public static int roomSelection(Room[] rooms, int promptNumber)
	{

		// Variable used to store the associated array index.
		int roomArrayIndex = -1;

		// Initally set the user response to "?".
		String roomId = "?";

		// Loop until the user response does not equal "?".
		while(roomId.equals("?"))
		{

			// Prompt for room ID, and read it from the keyboard.
			ScreenManager.printPrompt(promptNumber, "Enter room ID (or ? to show all rooms)");
			Scanner console = new Scanner(System.in);
			roomId = console.next();

			// Convert the input to upper case, so the input is not case sensitive.
			roomId = roomId.toUpperCase();

			// If the user has entered "?", print all rooms to screen.
			if(roomId.equals("?"))
			{
				for(int i=0; i < rooms.length; i++)
				{
					System.out.println();
					System.out.printf("  %S %30s %15s", rooms[i].getId(), rooms[i].getDescription(), rooms[i].getReadableStatus());
				}
				System.out.println();
				System.out.println();
			}

			// Now to check that the entered room actually exists.
			boolean found = false;
			for(int i=0; i < rooms.length; i++)
			{
				if(rooms[i].getId().equals(roomId))
				{

					// Set the array index to return to the method that called this.
					roomArrayIndex = i;
					found = true;
					break;
				}
			}

			// If the room ID does not exist, re-iterate the loop.
			if(!found)
			{

				// Don't warn the user if they entered "?".
				if(!roomId.equals("?"))
				{

					// Output no rooms where round, and set room ID to "?" so the
					// loop will re-iterate.
					ScreenManager.printWarning("Room not found, type ? to see all rooms");
					roomId = "?";
				}
			}
		}
		return roomArrayIndex;

	}

	// Method to room and booking information to file.
	public static void saveRooms(Room[] rooms)
	{

		// Error handeling for file operation.
		try
		{

			// Output serialised objects to file, and close the file.
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rooms.dat"));
			out.writeObject(rooms);
			out.close();
		}

		// Catch for file not found errors.
		catch(FileNotFoundException e)
		{
			ScreenManager.printWarning("Could not save data - file not found");
		}

		// Catch for IO errors.
		catch(IOException e)
		{
			ScreenManager.printWarning("Could not save data - IO problem");
		}

	}


	// Method to load room and booking information from file
	public static Room[] loadRooms() throws FileNotFoundException, IOException, ClassNotFoundException
	{

		// Loan the rooms array from a serialised file.
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("rooms.dat"));
		Room[] rooms = (Room[]) in.readObject();
		in.close();

		// Loop through each room, and correct the booking start and end times.
		// See the Room source file for more information.
		for(int i=0; i < rooms.length; i++)
		{
			if(!rooms[i].roomAvailable())
			{
				rooms[i].setCorrectDates();
			}
		}
		return rooms;

	}
}
