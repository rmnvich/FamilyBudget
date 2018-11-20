package rmnvich.apps.familybudget.domain.helper;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper {

    private static long getCurrentTimeInMills() {
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
}
