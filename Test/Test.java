//
// Home Travel Booking System
// Written by Jeremy Mercer
//
// File: Test/Test.java
// Description:
// 	This class, Test, is the driver class of the application - which would
// 	be replace if this code were ever used in a production environment.
//
package Test;

import Room.Room;
import Room.PremiumRoom;
import Menu.Menu;
import utilities.ScreenManager;
import java.io.*;

public class Test
{
	// Program starts here:
	public static void main(String[] args)
	{

		// Menu class can throw a number of exceptions, so they are being caught here.
		// See below for more information.
		try
		{

			// When calling the static method runMainMenu() in the Menu class, without
			// any parameters, all room and booking information is fetched from a file
			// called rooms.dat located within the project directory.
			// This method will then enter into the menu methods until a user completes
			// a function (i.e. books a room).
			Room rooms[] = Menu.runMainMenu();

			// This infinite loop ensures that after each completed function, the main
			// menu will be re-displayed after a user completes a function.
			// The overloaded runMainMenu(rooms) method will prevent room and booking
			// information being re-loaded from the rooms.dat file.
			while(1==1)
			{
				Menu.runMainMenu(rooms);
			}
		}
		// This exception is caught when the rooms.dat file does not exist, in this situation,
		// the software will assume that the program has never been run before, and will create
		// a set of default rooms and associated values.
		catch(IOException e)
		{
			ScreenManager.clear();
			ScreenManager.printProgramTitle();
			ScreenManager.printHeader("Program Setup");
			ScreenManager.printNotice("A saved rooms file has not been found, starting program with default values");
			ScreenManager.pause();
			Room newRooms[] = new Room[8];
			newRooms[0] = new Room("GARDEN0001", "North West Garden View", 45.00);
			newRooms[1] = new Room("GARDEN0002", "South East Garden View", 65.00);
			newRooms[2] = new Room("GARDEN0003", "North Garden View", 35.00);
			newRooms[3] = new Room("GARDEN0004", "South Garden View", 52.00);
			newRooms[4] = new Room("GARDEN0005", "West Garden View", 35.00);
			newRooms[5] = new Room("GARDEN0007", "East Garden View", 35.00);
			newRooms[6] = new PremiumRoom("POOL000001", "North Side Facing Pool", 90.00, 1, 150.00);
			newRooms[7] = new PremiumRoom("POOL000002", "South Side Facing Pool", 125.00, 2, 100.00);
			while(1 == 1)
			{
				newRooms = Menu.runMainMenu(newRooms);
			}

		}
		// This exception is caught when one of the class files is missing. This error is thrown
		// because the classes Room and PremiumRoom are stored as serialized objects in the
		// rooms.dat file, hence if they are missing, the program cannot load from file.
		// This is a critical error and the program terminates in a graceful manner.
		catch(ClassNotFoundException e)
		{
			ScreenManager.clear();
			ScreenManager.printProgramTitle();
			ScreenManager.printHeader("Error");
			ScreenManager.printWarning("A file is missing from your installation of Home Booking, please see below:");
			System.out.println(e);
			ScreenManager.pause();
		}
		
	}
}
