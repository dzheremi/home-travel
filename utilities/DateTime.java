package utilities;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateTime
{
	private long advance;
	private long time;
	
	public DateTime()
	{
		time = System.currentTimeMillis() + advance;
	}
	
	public DateTime(int setClockForwardInDays)
	{
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = System.currentTimeMillis() + advance;
	}
	
	public long getTime()
	{
		return time;
	}
	
	public String toString()
	{
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		return gct.toString();
	}
	
	public static String getCurrentTime()
	{
		Date date = new Date(System.currentTimeMillis());  // returns current Date/Time
		return date.toString();
	}
	
	public String getFormattedDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		
		return sdf.format(gct);
	}
	
	// returns difference in days
	public static int diffDays(DateTime endDate, DateTime startDate)
	{
		final long HOURS_IN_DAY = 24L;
		final int MINUTES_IN_HOUR = 60;
		final int SECONDS_IN_MINUTES = 60;
		final int MILLISECONDS_IN_SECOND = 1000;
		long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
		long hirePeriod = endDate.getTime() - startDate.getTime();
		return (int) (1 + (hirePeriod) / (convertToDays)) -1;
	}
}
