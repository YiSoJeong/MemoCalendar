package GUI;

import java.util.Calendar;
import java.util.GregorianCalendar;

/** 
 * Class for finding the value of a calendar in 6*7 array
 */
public class DateManager{ 
	final static int COLUMN = 7;
	final static int ROW = 6;
	int calDates[][] = new int[ROW][COLUMN];
	static int calYear;
	static int calMonth;
	static int calDayOfMon;
	final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int calLastDate;
	final static String MONTH_NAME[]= {"January","February","March", "April","June", "July","August", "September", "October","November","December"};
	
	Calendar today = Calendar.getInstance();
	Calendar cal;
	
	public DateManager(){ 
		setToday();
	}
	
	public void setToday(){
		calYear = today.get(Calendar.YEAR); 
		calMonth = today.get(Calendar.MONTH);
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
		makeCalData(today);
	}
	
	/**
	 * Find the location of the first date and calculate the last date. 
	 */
	private void makeCalData(Calendar cal){
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
		if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + isLeap(calYear);
		else calLastDate = calLastDateOfMonth[calMonth];
		// Initializing calendar array
		for(int i = 0 ; i<ROW ; i++){
			for(int j = 0 ; j<COLUMN ; j++){
				calDates[i][j] = 0;
			}
		}
		// Insert values in calendar array
		for(int i = 0, num = 1, k = 0 ; i<ROW ; i++){
			if(i == 0) k = calStartingPos;
			else k = 0;
			for(int j = k ; j<COLUMN ; j++){
				if(num <= calLastDate) calDates[i][j]=num++;
			}
		}
	}
	
	/**
	 * create a new calendar array from the current calendar
	 * (1 year: +12, -12)
	 * @param mon month to move
	 */
	public void moveMonth(int mon){ 
		calMonth += mon;
		if(calMonth>11) while(calMonth>11){
			calYear++;
			calMonth -= 12;
		} else if (calMonth<0) while(calMonth<0){
			calYear--;
			calMonth += 12;
		}
		cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
		makeCalData(cal);
	}
	
	/**
	 *check for leap year
	 *@return Return 1 for leap year
	 */
	private int isLeap(int year){ 
		if((year % 400 == 0)||(year % 4 == 0 && year % 100 != 0 )) 
			return 1;
		else return 0;
	}
	

}