package cn.fortune.apps.sleepseconds.helper;

import java.util.Calendar;

/**
 * Created by fortune on 13-10-1.
 */
public class TimeToMillSeconds {
    public static long addTimeToNow(int minutes) {
        return nowOfMillSecond() + minutes * 60 * 1000;
    }

    public static long nowOfMillSecond() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

}
