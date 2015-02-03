package com.rajohns.judgecardx.Utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by rajohns on 2/2/15.
 *
 */
public class DateUtil {
    public static Date sqlDateFromString(String date) {
        DateFormat format = new SimpleDateFormat("MM-dd-yyy", Locale.ENGLISH);
        java.util.Date utilDate;
        Date sqlDate = null;
        try {
            utilDate = format.parse(date);
            sqlDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sqlDate;
    }
}
