//
// Home Travel Booking System
// Written by Jeremy Mercer
//
// File: Room/PremiumRoom.java
// Description:
// 	This class, PremiumRoom, represents a premium hotel room, extending the
// 	Room class by providing a facillity for discounts and booking vouchers.
//
package Room;

import utilities.DateTime;
import utilities.ScreenManager;
import java.io.*;


// Class extends the Room class, and implements Serializable so that it may be
// written to file and saved.
public class PremiumRoom extends Room implements Serializable
{

	// This is the % discount given to a customer, based on the charge of
	// their current booking, to be used on their next booking.
	private static final double DISCOUNT_PCENT = 0.25;

	// Class wide variables.
	private int freeNights;
	private double discountRate;
	private double nextBookingDiscountVoucher;
	private String discountVoucher;
	private double voucherAmount;
	private double discountAmount;

	// Constructor method.
	public PremiumRoom(String roomId, String description, double dailyRate, int freeNights, double discountRate)
	{
		super(roomId, description, dailyRate);
		this.freeNights = freeNights;
		this.discountRate = discountRate;
	}

	// Overridden method to book rooms, allowing for vouchers to be stored when booking.
	public boolean bookRoom(String customerId, int nightsRequired, double voucherAmount)
	{
		if(super.bookRoom(customerId, nightsRequired))
		{
			this.voucherAmount = voucherAmount;
			return true;
		}
		else
		{
			return false;
		}

	}

	// Overridden method to book rooms, to be used when a voucher is not povided.
	public boolean bookRoom(String customerId, int nightsRequired)
	{
		if(bookRoom(customerId, nightsRequired, 0.00))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	// Overridden method to checkout of a room, allowing for discounts and vouchers.
	public boolean checkout()
	{
		if(super.checkout())
		{

			// Process any discounts on the current booking.
			discountAmount = processDiscounts();

			// Set the new booking charge.
			setCharge(getCharge() - discountAmount);

			// Set the discount amount for the next booking.
			nextBookingDiscountVoucher = getCharge() * DISCOUNT_PCENT;
			return true;
		}
		else
		{
			return false;
		}

	}

	// Overridden method to print room and booking information, along with any
	// discount / voucher information.
	public void print()
	{
		super.print();

		// If the room is set to booked, print a table summary of the convoluted discount system,
		// in an attempt to make it readable to the user.
		if(getStatus() == 'B')
		{
			System.out.println();
			ScreenManager.printHeader("Premium room discount details");
			ScreenManager.printNotice("Customer is entitled to the greater of the following promotional discounts");
			System.out.println();
			System.out.printf("%S %1s $ %(,7.2f %26s\n", "Discount Rate: ", "", discountRate, "One-off Discount");
			System.out.println();
			ScreenManager.printDivider("or");
			System.out.println();
			System.out.printf("%S %11d %25s\n", "Free-Night(s): ", freeNights, "Per Reservation");
			System.out.println();
			ScreenManager.printDivider("or");
			System.out.println();
			System.out.printf("%S %7s $ %(,7.2f %30s\n", "Voucher: ", "", voucherAmount, "Provided by Customer");
			System.out.println();
		}

		// If the room is now set to un-clean (i.e. it was just checked out), print a promotional
		// off to be redeemed on next booking.
		else if(getStatus() == 'U')
		{
			System.out.printf("%S %4s $ %(,7.2f (%s)", "Discount: ", "", discountAmount, "Applied to Charge Above");
			if(nextBookingDiscountVoucher > 0)
			{
				System.out.println();
				System.out.println();
				ScreenManager.printDivider("Promotional offer");
				System.out.println();
				System.out.printf("  Receive a $ %(,7.2f reduction on your next booking.\n", nextBookingDiscountVoucher);
				System.out.println("  Simply present this receipt to claim your discount.");
			}
		}

	}

	// Convert the room to a ":" delimitered string.
	public String toString()
	{
		String stringVersion = super.toString();
		stringVersion += ":" + freeNights + ":" + discountRate + ":" + nextBookingDiscountVoucher;
		return stringVersion;
	}

	// Method to process any discounts for this object.
	// Works by selecting the discount that provides the best value to the customer, not exactely
	// business logic, but why not.
	public double processDiscounts()
	{
		double discountTotal = 0;
		int numberOfDays = getNumberOfDays();
		if(numberOfDays >= freeNights)
		{
			discountTotal = getDailyRate() * freeNights;
		}
		if(getCharge() >= discountRate)
		{
			if(discountTotal < (getCharge() - discountRate))
			{
				discountTotal = getCharge() - discountRate;
			}
		}
		if(voucherAmount >= discountTotal)
		{
			discountTotal = voucherAmount;
		}

		// Don't let the discount exceed the total charge.
		if(discountTotal > getCharge())
		{
			discountTotal = getCharge();
		}

		return discountTotal;

	}
}
