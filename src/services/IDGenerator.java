package services;

import java.sql.SQLException;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Class responsible for generating unique random IDs.
 */
public class IDGenerator {
    /**
     * Generates a unique Bank ID based on the current date, time, and user's initials.
     * The format of the Bank ID is: YYYYMMDDHHMMX####, where:
     * - YYYY = Current year
     * - MM   = Current month (with leading zero if needed)
     * - DD   = Current day (with leading zero if needed)
     * - HH   = Current hour
     * - MM   = Current minute
     * - X    = First letter of the provided name
     * - #### = Random four-digit number
     *
     * @param name The name of the user, from which the first letter is extracted.
     * @return A unique Bank ID as a String.
     */
    public static String BankIDgenerator(String name) throws SQLException {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Random rand = new Random();

        while (true) {
            // Timedate
            String month = String.format("%02d", today.getMonthValue());
            String day = String.format("%02d", today.getDayOfMonth());
            int year = today.getYear();

            // Time (Hours and minutes)
            String hour = String.format("%02d", now.getHour());
            String minute = String.format("%02d", now.getMinute());


            // Initial name
            String iniName = name.substring(0, 1);

            // Random digits
            String digits = String.format("%04d", rand.nextInt(10000));

            String bankID = String.valueOf(year) + month + day + hour + minute + iniName + digits;

            // Checks if the ID already exist in the DB and if not it will regenerate
            if (!bankExists(bankID)) {
                return bankID;
            } return null;
        }


    }

    private static boolean bankExists(String id) throws SQLException {
        return BankRetriever.getBank(id) != null;
    }


    /**
     * Generates a unique reference number based on the current date, time, and a random four-digit number.
     * The format of the reference number is: MMDDYYYYHHMM####, where:
     * - MM   = Current month
     * - DD   = Current day
     * - YYYY = Current year
     * - HH   = Current hour
     * - MM   = Current minute
     * - #### = Random four-digit number
     *
     * @return A unique reference number as a String.
     */
//    public static String RefNumGenerator() {
//        LocalDate today = LocalDate.now();
//        LocalTime now = LocalTime.now();
//        Random rand = new Random();
//
//        while (true) {
//            // Timedate
//            String month = String.format("%02d", today.getMonthValue());
//            String day = String.format("%02d", today.getDayOfMonth());
//            int year = today.getYear();
//
//            // Time (Hours and minutes)
//            String hour = String.format("%02d", now.getHour());
//            String minute = String.format("%02d", now.getMinute());
//
//            // Random digits
//            String digits = String.format("%04d", rand.nextInt(10000));
//
//            String refNum = month + day + String.valueOf(year) + month + day + hour + minute + digits;
//
//            // assume that the condition is correct to check if the refNum already exist
//            if (!getrefNum) {
//                return refNum;
//            }
//        }
//
//        return null;
//    }

}
