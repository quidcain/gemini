package com.gemini.commons.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/12/18
 * Time: 8:17 PM
 */
public final class DateUtils {

    public static LocalDate toLocalDate(Date input) {
        return LocalDate.fromDateFields(input);
    }

    public static Date toDate(LocalDate input) {
        return input.toDate();
    }

    public static Date toDate(LocalDateTime input) {
        return input.toDate();
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static int toYears(Date input) {
        return Years.yearsBetween(new DateTime(input), DateTime.now()).getYears();
    }

    public static Date addHours(Date now, int hours) {
        return DateUtils.toDate(LocalDateTime.fromDateFields(now).plusHours(hours));
    }

    public static Date addMinutes(Date now, int minutes) {
        return DateUtils.toDate(LocalDateTime.fromDateFields(now).plusMinutes(minutes));
    }

    public static Date toDate(String ddmmyyyy){
        LocalDate localDate = LocalDate.parse(ddmmyyyy, DateTimeFormat.forPattern("ddMyyyy"));
        return localDate.toDate();
    }


    public static String formatDate(String yyyymmdd) {
        LocalDate date = LocalDate.parse(yyyymmdd, DateTimeFormat.forPattern("yyyyMdd"));
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dMMMyyyy").withLocale(Locale.forLanguageTag("es-PR"));
        return date.toString(fmt);
    }

    public static void main(String[] args) {
        System.out.println(toDate("17121988"));

    }

}