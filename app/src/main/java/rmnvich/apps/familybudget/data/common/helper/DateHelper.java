package rmnvich.apps.familybudget.data.common.helper;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper {

    public static long getCurrentTimeInMills() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeInString() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return dateFormat.format(getCurrentTimeInMills());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFullTimeInStringFromLong(long time) {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm");
        return dateFormat.format(time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getShortTimeInStringFromLong(long time) {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        return dateFormat.format(time);
    }

    public static long getTimeFromDatePicker(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, date);

        return calendar.getTimeInMillis();
    }

    public static boolean isDateMoreThatToday(long date) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);

        return date > today.getTimeInMillis();
    }
}
