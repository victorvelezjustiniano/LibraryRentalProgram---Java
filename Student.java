import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static java.lang.Math.toIntExact;
import java.text.DateFormat;

public class Student {

	public static final String daysLeft = null;



	public static void info() {
		int rentDays = 7;
		double lateFee = 2.00;
	}

	public static int numDays() {
		Scanner date = new Scanner(System.in);
		System.out.println("Please enter the rental date as follows (yyyy mm dd) to get your rental fee: ");
		int year = date.nextInt();
		int month = date.nextInt() - 1;
		int day = date.nextInt();
		Date d1 = new GregorianCalendar(year, month, day).getTime();

		Date today = new Date();
		long diff = today.getTime() - d1.getTime();
		long diffDays = diff / (1000 * 60 * 60 * 24);
		int diffrence = toIntExact(diffDays);
		System.out.println(diffrence);
		return diffrence;
	}

	

	public static int daysLeft() {
		int rentDays = 7;
		int days = Student.numDays();
		if(days <= 7){
			int remainDays = 7 - days;
			System.out.println("You have " + remainDays + " days left.");
			return remainDays;
		}else{
			int days2 = days - 7;
			return days2;
		}
	}
}
