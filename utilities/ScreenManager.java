//
// Home Travel Booking System
// Written by Jeremy Mercer
//
// File: utilities/ScreenManager.java
// Description:
// 	This class, ScreenManager, is designed to be statically called to provision
// 	common screen formatting and output to facilitate the console menu interface.
//
package utilities;

import java.util.Scanner;

public class ScreenManager
{

	// Class wide static variables.
	private static final String PROGRAM_TITLE = "H O M E    T R A V E L    B O O K I N G    S Y S T E M";
	private static Scanner console = new Scanner(System.in);

	// Method to clear the console screen.
	public static void clear()
	{
		System.out.print("\u001b[2J");
		System.out.flush();
	}

	// Method to print the program title centred on screen.
	public static void printProgramTitle()
	{
		int titleLength = PROGRAM_TITLE.length() / 2;
		for(int i=0; i < (60 - titleLength); i++)
		{
			System.out.print(" ");
		}
		System.out.print("   ");
		System.out.printf("%S", PROGRAM_TITLE);
		System.out.print("   ");
		for(int i=0; i < (60 - titleLength); i++)
		{
			System.out.print(" ");
		}
		System.out.println();
		System.out.println();
	}

	// Method to print a title, centred on screen, wrapped with "#" to form
	// a horizontal divider.
	public static void printHeader(String title)
	{
		int titleLength = title.length() / 2;
		for(int i=0; i < (60 - titleLength); i++)
		{
			System.out.print("#");
		}
		System.out.print("   ");
		System.out.printf("%S", title);
		System.out.print("   ");
		for(int i=0; i < (60 - titleLength); i++)
		{
			System.out.print("#");
		}
		System.out.println();
		System.out.println();
	}

	// Method to print a horizontal divider.
	public static void printDivider(String message)
	{
		int messageLength = message.length() / 2;
		for(int i=0; i < (40 - messageLength); i++)
		{
			System.out.print("=");
		}
		System.out.print("   ");
		System.out.printf("%S", message);
		System.out.print("   ");
		for(int i=0; i < (40 - messageLength); i++)
		{
			System.out.print("=");
		}
		System.out.println();
	}

	// Method to print a menu item - using a menu option number and description.
	public static void printMenuItem(int option, String description)
	{
		System.out.printf("  %d.  %S\n\n", option, description);
	}

	// Method to print a prompt, with a associated prompt number.
	public static void printPrompt(int number, String prompt)
	{
		System.out.printf(" %d.  %S:  ", number, prompt);
	}

	// Method to print a warning on screen.
	public static void printWarning(String message)
	{
		System.out.println();
		System.out.println();
		System.out.print("*****");
		System.out.printf(" %S ", message);
		System.out.print("*****");
		System.out.println();
		System.out.println();
	}

	// Method to print a notice on screen.
	public static void printNotice(String message)
	{
		printWarning(message);
	}

	// Method to pause the program, until the user presses enter.
	public static void pause()
	{
		System.out.println();
		System.out.printf("  %S  ", "Press [enter] to continue");
		console.nextLine();
	}

	// Method to print a footer, with a horizontal divider.
	public static void printFooter()
	{
		for(int i=0; i < 126; i++)
		{
			System.out.print("#");
		}
		System.out.println();
		System.out.println();
	}
}
