package services;

import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;

import java.sql.SQLException;
import java.util.Random;
import java.time.LocalDate;



/**
 * Class responsible for generating unique random IDs.
 */
public class IDGenerator {
    /**
     * Generates a unique Bank ID based on the current date, time, and user's initials.
     * The format of the Bank ID is: YYYYMMX####, where:
     * - YYYY = Current year
     * - MM   = Current month (with leading zero if needed)
     * - X    = First letter of the provided name
     * - #### = Random four-digit number
     *
     * @param name The name of the user, from which the first letter is extracted.
     * @return A unique Bank ID as a String.
     */
    public static String BankIDgenerator(String name) {
        String bankID = "";

        do {
            String month = String.format("%02d", LocalDate.now().getMonthValue());
            int year = LocalDate.now().getYear();
            String iniName = name.substring(0, 1);
            String digits = String.format("%04d", new Random().nextInt(10000));

            bankID = String.valueOf(year) + month + iniName + digits;
        } while (BankDBManager.bankExists(bankID));

        return bankID;

    }

    /**
     * Generates a unique account ID by appending a random 6-digit number to the bank's ID.
     *
     * @param bank The bank object from which the bank ID is retrieved.
     * @return A unique account ID in the format "{bankID}{randomDigits}".
     */
    public static String AccountIDGenerator(Bank bank) {
        String id = "";
        do {
            String bankIDPortion = bank.getID();
            int randomDigits = new Random().nextInt(1000000);
            id = bankIDPortion + randomDigits;
        } while (AccountDBManager.accountExists(id));

        return id;
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
