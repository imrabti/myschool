package com.gsr.myschool.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static Integer currentYear() {
        Date now = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(now);

        return calendar.get(Calendar.YEAR);
    }

    public static Integer getYear(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }
}
