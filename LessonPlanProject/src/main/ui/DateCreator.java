package ui;

import javax.swing.*;

// create dates and checks for their validity
public class DateCreator {

    // EFFECTS: tests date for validity, and makes given label print error messages if it is invalid
    //          returns true if valid, else returns false
    public static Boolean testDateValidity(String monthString, String dayString, String yearString, JLabel label) {
        int year;
        int month;
        int day;

        try {
            year = Integer.parseInt(yearString);
            month = Integer.parseInt(monthString);
            day = Integer.parseInt(dayString);
        } catch (NumberFormatException e) {
            ElementCreator.setErrorText(label, "Inputs must all be numbers.");
            return false;
        }

        String errorMessage = createErrorMessage(year, month, day);
        if (errorMessage.equals("")) {
            return true;
        } else {
            ElementCreator.setErrorText(label, errorMessage);
            return false;
        }
    }

    // EFFECTS: creates error message based on validity of year, month, and day
    public static String createErrorMessage(int year, int month, int day) {
        String errorMessage = "";
        if (year < 0 || year > 9999) {
            errorMessage += "Year is invalid. ";
        }
        if (month < 1 || month > 12) {
            errorMessage += "Month is invalid. ";
        }
        if (day < 1 || (errorMessage.equals("") && day > getHighBasedOnMonthAndYear(month, year))) {
            errorMessage += "Day is invalid.";
        }
        return errorMessage;
    }

    // EFFECTS: returns the possible highest day depending on the month
    public static int getHighBasedOnMonthAndYear(int month, int year) {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (year % 4 == 0) {
                return 29;
            }
            return 28;
        }
        return 31;
    }

    // EFFECTS: adds "0" to numberAsString if given number is lower than given high
    public static String padString(String numberAsString, int high) {
        int number = Integer.parseInt(numberAsString);
        if (number < high) {
            numberAsString = "0" + numberAsString;
        }
        return numberAsString;
    }

    // EFFECTS: formats date in MM/DD/YYYY
    public static String formatDate(String monthString, String dayString, String yearString) {
        return padString(monthString, 2) + "/" + padString(dayString, 2) + "/"
                + padString(yearString, 4);
    }
}
