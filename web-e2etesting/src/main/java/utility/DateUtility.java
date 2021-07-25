package utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	/**
	 * @return
	 */
	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		System.out.println(strDate);
		return strDate;
	}

	/**
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static String getCurrentTimes() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

	public static long getCurrentTimeInMillis() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		long startTime = cal.getTimeInMillis();
		return startTime;
	}

}
