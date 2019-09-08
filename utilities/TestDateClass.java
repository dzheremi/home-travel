package utilities;

public class TestDateClass
{

	public static void main(String[] args)
	{
		DateTime startDate = new DateTime();
		DateTime endDate = new DateTime(30);
		
		System.out.println("The start date is: " + startDate.getFormattedDate());
		System.out.println("The end date is: " + endDate.getFormattedDate());
		
		System.out.println("The booking period is: " + DateTime.diffDays(endDate, startDate));
		
		

	}

}
