package Launcher;

import accounts.CreditAccount;
// import bank.*;
import accounts.Account;
import accounts.CreditAccount;
import accounts.SavingsAccount;
import accounts.Account;
import main.Main;
import main.Menu;
import main.Field;
import Bank.Bank;

import java.util.ArrayList;
import java.util.Comparator;
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
        return loggedBank.getClass().equals(Bank.class);
    }

    /**
     * Bank interaction interface.
     */
    public static void bankInit(){
        if (!isLogged()) {
            System.out.println("Not logged in to any bank.");
            return;
        }

        quack:
        while (true) {
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
                    break quack;
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
        while (true){
            Main.showMenuHeader("Show Accounts");
            Main.showMenu(32);
            String opt = Main.prompt("\nSelect an option: ", true);

            label: 
            switch (opt) {
                // Show All Credit Accounts
                case "1":
                    Main.showMenuHeader("All Credit Accounts");
                    loggedBank.showAccounts(accounts.CreditAccount.class);
                    break label;

                    // Show All Savings Accounts
                case "2":
                    Main.showMenuHeader("All Savings Accounts");
                    loggedBank.showAccounts(SavingsAccount.class);
                    break label;

                    // Show All Accounts
                case "3":
                    Main.showMenuHeader("All Accounts");
                    loggedBank.showAccounts(Account.class);
                    break label;

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

        Accounts.Account a = null;

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
                System.out.println("Logged in to " + name + "bank.");
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
     * @throws NumberFormatException Thrown when an invalid input is given 
     *  for deposit, withdraw, credit limit, and processing fee.
     */
    public static void createNewBank() throws NumberFormatException{
        Main.showMenuHeader("Creating a New Bank");

        Random rand = new Random();
        int bank_id = 20250000;

        // Generates randomized ID for Bank ID
        while (true) {
            int rand_number = rand.nextInt(9999);
            bank_id += rand_number;
            Bank temporary = new Bank(bank_id, "", "");

            // Checks if the randomized ID already exist in the banks
            boolean doesExist = false;
            for (Bank b : Bank) {
                if (new Bank.BankIDComparator().compare(b, temporary) == 0) {
                    doesExist = true;
                    break;
                }
            }

            if (!doesExist){break;}
        }

        Field<String, String> bank_name = new Field<>("bank name", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> bank_passcode = new Field<>("bank passcode", String.class, 8, new Field.StringFieldLengthValidator());

        System.out.println("Generated Bank ID: " + bank_id);
        bank_name.setFieldValue("Enter Bank Name: ");
        bank_passcode.setFieldValue("Enter Bank Passcode: ");

        double depositLimit = 50000;
        double withdrawLimit = 50000;
        double creditLimit = 100000;
        double processingFee = 10;

        try {
            String choice = Main.prompt("Change deposit limit? Default is 50000 (type 'y' to change).", true);
            if (choice.equals("y")) {
                depositLimit = Double.parseDouble(Main.prompt("Enter new deposit limit: ", true));
            }

            choice = Main.prompt("Change withdraw limit? Default is 50000 (type 'y' to change).", true);
            if (choice.equals("y")) {
                withdrawLimit = Double.parseDouble(Main.prompt("Enter new withdraw limit: ", true));
            }

            choice = Main.prompt("Change credit limit? Default is 100000 (type 'y' to change).", true);
            if (choice.equals("y")) {
                creditLimit = Double.parseDouble(Main.prompt("Enter new credit limit: ", true));
            }

            choice = Main.prompt("Change processing fee? Default is 10 (type 'y' to change).", true);
            if (choice.equals("y")) {
                processingFee = Double.parseDouble(Main.prompt("Enter new processing fee: ", true));
            }
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid number format for deposit limit, withdraw limit, credit limit, or processing fee.");
        }

        addBank(new Bank(bank_id, bank_name.getFieldValue(), bank_passcode.getFieldValue(), depositLimit, withdrawLimit, creditLimit, processingFee));
        System.out.println("New bank successfully created!");
    }

    /**
     * Adds the passed Bank object to the ArrayList.
     * @param b - Bank object to add
     */
    private static void addBank(Bank b) {
        Banks.add(b);
    }

    /**
     * Displays the list of registered banks
     */
    public static void showBanksMenu () {
        Main.showMenuHeader("Registered Banks");
        int count = 1;
        for (Bank b : Bank) {
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

    public static Accounts.Account findAccount(String accountNumber) {
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
        return Bank.size();

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
