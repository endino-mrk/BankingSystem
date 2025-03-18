package launcher;

// import bank.*;
import accounts.Account;
import accounts.SavingsAccount;
import main.Field;
import bank.Bank;
import main.Main;
import sqlite.SQLiteBank;

import java.util.ArrayList;
import java.util.Random;

public class BankLauncher {
    private static final ArrayList<Bank> Banks = new ArrayList<>();

    private static Bank loggedBank = null;
    
    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS
    /**
     * Checks if the user is currently logged in to a registered bank.
     * 
     * @return boolean - true if user is logged in, false otherwise
     */
    public static boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Retrieves all banks from the SQL. Should only be used once to avoid duplication.
     */
    public static void retrieveFromSQL() {
        Banks.addAll(SQLiteBank.retrieveBanks());
    }

    /**
     * Bank interaction interface.
     */
    public static void bankInit(){
        if (!isLogged()) {
            System.out.println("Not logged in to any bank.");
            return;
        }

        while (loggedBank != null) {
            Main.showMenuHeader("Bank Menu");
            Main.showMenu(31);
            String opt = Main.prompt("\nSelect an option: ", true);

            switch (opt){
                case "1":
                    showAccounts();
                    break;
                case "2":
                    newAccounts();
                    break;
                case "3":
                    logout();
                default:
                    System.out.println("Input error: Invalid input. \n");
            }
        }

        System.out.println("Logged out of the bank.");
    }

    /**
     * Displays the account menu, allowing users to view accounts.
     */
    private static void showAccounts() {
        label: 
        while (true){
            Main.showMenuHeader("Show Accounts");
            Main.showMenu(32);
            String opt = Main.prompt("\nSelect an option: ", true);

            switch (opt) {
                // Show All Credit Accounts
                case "1":
                    Main.showMenuHeader("All Credit Accounts");
                    loggedBank.showAccounts(accounts.CreditAccount.class);
                    break;

                    // Show All Savings Accounts
                case "2":
                    Main.showMenuHeader("All Savings Accounts");
                    loggedBank.showAccounts(SavingsAccount.class);
                    break;

                    // Show All Accounts
                case "3":
                    Main.showMenuHeader("All Accounts");
                    loggedBank.showAccounts(Account.class);
                    break;

                case "4":
                    break label;

                default:
                    System.out.println("Input error: Invalid input. \n");
            }
        }

    }

    /**
     * Handles account creation for logged in banks.
     */
    private static void newAccounts() {
        Main.showMenuHeader("Creating New Account");
        Main.showMenu(33);
        String opt = Main.prompt("\nSelect an option: ", true);

        accounts.Account a = null;

        switch (opt) {
            case "1":
                a = loggedBank.createNewCreditAccount();
                break;
            case "2":
                a = loggedBank.createNewSavingsAccount();
                break;
            default:
                System.out.println("Input error: Invalid input. \n");
        }

        if (a != null) {
            loggedBank.addNewAccount(a);
            String t = (opt.equals("1")) ? "Credit" : "Savings";
            System.out.println(t + " account has been created and added to bank.");
            // break;
        }

    }

    /**
     * Bank login interface.
     */
    public static void bankLogin () {
        if (isLogged()) {
            System.out.printf("%s is currently logged in.\n", loggedBank.getName());
            return;
        }

        try {
            int id = Integer.parseInt(Main.prompt("Enter ID: ", false));
            String name = Main.prompt("Enter name: ", false);
            String passcode = Main.prompt("Enter passcode: ", false);

            Bank b = getBank(new Bank.BankCredentialsComparator(), new Bank(id, name, passcode));

            if (b != null) {
                setLogSession(b);
                System.out.println("Logged in to " + name + " bank.");
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid ID format. Please enter a numeric ID.");
        }
    }

    /**
     * Sets the current log session into the bank to be logged in.
     * @param b - Bank to log in
     */
    private static void setLogSession(Bank b) {
        if (loggedBank == null)  {
            loggedBank = b;
            System.out.printf("\nBank '%s' successfully logged in! \n", b.getName());
        }
        // else {
        //     System.out.println("Error: Cannot log in. Invalid credentials.");
        // }
    }

    /**
     * Logs out the currently logged in bank.
     */
    private static void logout() {
        System.out.printf("\n%s logged out.\n", loggedBank.getName()) ;
        loggedBank = null;
    }

    /**
     * Bank creation interface.
     * 
     * @throws NumberFormatException Thrown when an invalid input is given for deposit, withdraw, credit limit, and processing fee.
     */
    public static void createNewBank() throws NumberFormatException{
        Main.showMenuHeader("Creating a New Bank");

        Random rand = new Random();
        int bankID = 20250000;

        // Generates randomized ID for Bank ID
        while (true) {
            // int rand_number = rand.nextInt(9999);
            bankID += rand.nextInt(9999);
            // Bank temporary = new Bank(bank_id, "", "");

            // Checks if the randomized ID already exist in the banks
            boolean doesExist = false;
            for (Bank b : Banks) {
                if (new Bank.BankIDComparator().compare(b, new Bank(bankID, "", "")) == 0) {
                    doesExist = true;
                    break;
                }
            }

            if (!doesExist){break;}
        }

        Field<String, String> bankName = new Field<>("Bank Name", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> bankPasscode = new Field<>("Bank Passcode", String.class, 8, new Field.StringFieldLengthValidator());

        System.out.println("Generated Bank ID: " + bankID);
        bankName.setFieldValue("Enter Bank Name: ", false);
        while (bankPasscode.getFieldValue() == null || bankPasscode.getFieldValue().length() != 8) {
            bankPasscode.setFieldValue("Enter Bank Passcode (must be exactly 8 digits): ");
        }
        
        // double depositLimit = 50000;
        // double withdrawLimit = 50000;
        // double creditLimit = 100000;
        // double processingFee = 10;
        
        // String choice = Main.prompt("Do you want to change the default deposit limit")
        try {
            boolean validChoice = false;

            while(!(validChoice)) {
                String choice = Main.prompt("Do you want to change default transaction limits and processing fee? [y/n]: ", true).toLowerCase();
                if (choice.equals("y")) {
                    validChoice = true;

                    Field<Double, Double> depositLimit = new Field<>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
                    Field<Double, Double> withdrawLimit = new Field<>("Withdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
                    Field<Double, Double> creditLimit = new Field<>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
                    Field<Double, Double> processingFee = new Field<>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());

                    depositLimit.setFieldValue("Enter deposit limit: ");
                    withdrawLimit.setFieldValue("Enter withdraw limit: ");
                    creditLimit.setFieldValue("Enter credit limit: ");
                    processingFee.setFieldValue("Enter processing fee: ");

                    addBank(new Bank(bankID, bankName.getFieldValue(), bankPasscode.getFieldValue(), depositLimit.getFieldValue(), withdrawLimit.getFieldValue(), creditLimit.getFieldValue(),processingFee.getFieldValue()));
                }
                else if (choice.equals("n")) {
                    addBank(new Bank(bankID, bankName.getFieldValue(), bankPasscode.getFieldValue()));
                    break;
                } else {
                    validChoice = false;
                }
            }
            System.out.println("Bank succesfully created.");
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid number format for deposit limit, withdraw limit, credit limit, or processing fee.");
        }
    }

    /**
     * Adds the passed Bank object to the ArrayList.
     * @param b - Bank object to add
     */
    private static void addBank(Bank b) {
        Banks.add(b);
        SQLiteBank.insertToSQL(b);
    }

    /**
     * Displays the list of registered banks
     */
    public static void showBanksMenu () {
        Main.showMenuHeader("Registered Banks");
        int count = 1;
        for (Bank b : Banks) {
            System.out.printf("[ %d ] %s \n", count, b.toString());
            count++;
        }
        System.out.println();
    }

        /**
     * Checks if a bank exists based on a comparator criteria.
     * 
     * @param comparator - Comparator<Bank> object; used as criteria for searching
     * @param bank - Bank object to be compared
     * @return Bank the matching bank if found, otherwise null
     */
    public static Bank getBank(java.util.Comparator<Bank> comparator, Bank bank) {
        for (Bank b : Banks) {
            if (comparator.compare(b, bank) == 0) {
                return b;
            }
        }

        return null;
    }

    /**
     * Searches for an account within registered banks
     * @param accountNum: the account number to search for
     * @return the matching account if found, otherwise null
     */

    public static Account findAccount(String accountNumber) {
        for (Bank b : Banks) {
            if (Bank.accountExists(b, accountNumber)) {
                return loggedBank.getBankAccount(b, accountNumber);
            }
        }
        return null;
    }

    /**
     * Returns the total number of registered banks
     * @return the size of the bank list
     */
    public static int bankSize () {
        return Banks.size();

    }



    /**
     * Logs out of the currently logged bank.
     */
    // private static void logout() {
    //     if (loggedBank != null) {
    //         int index = Banks.indexOf(loggedBank);
    //         Banks.set(index, loggedBank);
    //         loggedBank = null;
    //     }
    // }








    



 


}
