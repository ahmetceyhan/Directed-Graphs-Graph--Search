import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class Tools/* implements Comparator */{

	private static final String FORMAT = "%02d:%02d";

	/*convert String time to long minute*/
	public long durationToMinute(String duration) {
		long minute;
		String[] time = duration.split(":");
		minute = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
		return minute;
	}

	/*convert long minute to String time*/
	public String minuteToDuration(long time) {
		return String.format(FORMAT, TimeUnit.MINUTES.toHours(time),
				TimeUnit.MINUTES.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(time)));
	}

	/*convert millisecond time to String time*/
	public String msToDuration(long milliseconds) {
		return String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(milliseconds),
				TimeUnit.MILLISECONDS.toMinutes(milliseconds)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)));
	}

	/*convert millisecond time to Date time*/
	public Date msToDate(long milliseconds) {
		return new Date(milliseconds);
	}

	/*'->' splitter*/
	public String[] arrowSplitter(String dept_arr) {
		return dept_arr.split("->");
	}

	/*tab splitter*/
	public String[] tabSplitter(String line) {
		return line.split("\t");
	}

	/*convert String("dd/MM/yyyy HH:mm E") time to Date time*/
	public Date deperatureStringToDate(String date_s) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm E", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(date_s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/*convert String("dd/MM/yyyy") time to Date time*/
	public Date startStringToDate(String date_s) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(date_s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
