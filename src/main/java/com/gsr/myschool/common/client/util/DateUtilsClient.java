package com.gsr.myschool.common.client.util;

import java.util.Date;

public class DateUtilsClient {
    private static final long MILLISECONDS_IN_SECOND = 1000l;
    private static final long SECONDS_IN_MINUTE = 60l;
    private static final long MINUTES_IN_HOUR = 60l;
    private static final long MILLISECONDS_IN_HOUR = MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR;

    public static Date correctDate(Date date) {
        return new Date(date.getTime() + 12 * MILLISECONDS_IN_HOUR);
    }
}
