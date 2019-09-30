package com.application.seb.projet5_mynews.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {


    /**
     *This method convert the Business API request string date into format yyyy-MM-dd
     * @param date is the Business request string date we want to format
     * @return a string value with the new format
     */
    public static String convertBusinessDate(String date){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date mDate;
        String str = null;

        try {
            mDate = inputFormat.parse(date);
            str = outputFormat.format(mDate);
            //Log.d("Business convert date", str);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;

    }

    /**
     *This method convert the TopStories API request string date into format yyyy-MM-dd
     * @param date is the TopStories request string date we want to format
     * @return a string value with the new format
     */
    public static String convertTopStoriesDate(String date){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-04:00'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date mDate;
        String str = null;

        try {
            mDate = inputFormat.parse(date);
            str = outputFormat.format(mDate);
            //Log.d("TopStories convert date", str);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     *This method convert the Spinner selected date into format yyyy/MM/dd
     * @param year is the Spinner year selected
     * @param month is the Spinner month selected
     * @param dayOfMonth is the Spinner day selected
     * @return a String value with the new format
     */
    public static String convertDateForSpinner(int year, int month, int dayOfMonth){
        return year +"/"+ convertNumber(month) +"/"+ convertNumber(dayOfMonth);
    }

    /**
     * his method convert the Spinner selected date into format yyyyMMdd
     * @param year is the Spinner year selected
     * @param month is the Spinner month selected
     * @param dayOfMonth is the Spinner day selected
     * @return a String value with the new format
     */
    public static String convertDateForSave(int year, int month, int dayOfMonth){
        return year+convertNumber(month)+convertNumber(dayOfMonth);
    }

    /**
     * This method will add a zero before an int value if it is under 10
     * @param input is the int value
     * @return a new int value if necessary
     */
    public static String convertNumber(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + input;
        }
    }

}
