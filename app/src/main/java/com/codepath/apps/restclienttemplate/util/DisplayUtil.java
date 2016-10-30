package com.codepath.apps.restclienttemplate.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by taq on 30/10/2016.
 */

public class DisplayUtil {

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static String getDateDifferenceForDisplay(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        try {
            Date date = sf.parse(rawJsonDate);
            then.setTime(date);
            long nowMs = now.getTimeInMillis();
            long thenMs = then.getTimeInMillis();
            // Calculate difference in milliseconds
            long diff = nowMs - thenMs;
            // Calculate difference in seconds
            long diffMinutes = diff / (60 * 1000);
            long diffHours = diff / (60 * 60 * 1000);
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffMinutes == 0) {
                return "just now";
            } else if (diffMinutes < 60) {
                return diffMinutes + "m";
            } else if (diffHours < 24) {
                return diffHours + "h";
            } else if (diffDays < 7) {
                return diffDays + "d";
            } else {
                sf = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
                return sf.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}