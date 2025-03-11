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
    private static final ArrayList<Bank> Bank = new ArrayList<>();

    private static Bank loggedBank = null;

    /**
     * Displays the list of registered banks
     */
    public static void showBanksMenu () {
        Main.showMenuHeader("Registered Banks");
        int count = 1;
        for (Bank b : Bank) {
            System.out.printf("[ %d ] %s \n", count, b.getName());
            count++;
        }
        System.out.println();
    }

    /**
     * Returns the total number of registered banks
     * @return the size of the bank list
     */
    public static int bankSize () {
        return Bank.size();

    }

    /**
     * Checks if a bank is currently logged in
     * @return true if a bank is logged in, false if null
     */
    public static Boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Finds a bank using a given comparator and input bank credentials
     * @param comparator:
     * @param bank: bank object containing login details
     * @return the matching bank if found, otherwise null
     */
    public static Bank getBank(Comparator<Bank> comparator, Bank bank){
        for (Bank b : Bank) {
            if (comparator.compare(bank, b) == 0) {
                return b;
            }
        }

        return null;
    }

    /**
     * Sets the current logged in bank session
     * @param b: a bank object to be logged in
     */
    private static void setLogSession(Bank b) {
        if (b != null) {
            System.out.printf("\nBank '%s' successfully logged in! \n", b.getName());
            loggedBank = b;
        }
        else {
            System.out.println("Error: Cannot log in. Invalid credentials.");
        }
    }

    /**
     * Handles bank login by prompting for credentials and verifying them.
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

            Bank inputted = new Bank(id, name, passcode);
            Bank b = getBank(new Bank.BankCredentialsComparator(), inputted);

            if (b != null) {
                setLogSession(b);
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid ID format. Please enter a numeric ID.");
        }
    }

    /**
     * Searches for an account within registered banks
     * @param accountNum: the account number to search for
     * @return the matching account if found, otherwise null
     */
    public static Account findAccount(String accountNum) {
        for (Bank b : Bank) {
            Account account = b.getBankAccount(b, accountNum);
            if (account != null){
                return account;
            }
        }
        return null;
    }

    /**
     * Displays the account menu, allowing users to view accounts.
     */
    private static void showAccounts() {
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
                    break;

                default:
                    System.out.println("Input error: Invalid input. \n");
            }

            if (opt.equals("4")){
                break;
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

        switch (opt) {
            case "1":
                loggedBank.createNewCreditAccount();
                break;

            case "2":
                loggedBank.createNewSavingsAccount();
                break;
            default:
                System.out.println("Input error: Invalid input. \n");
        }

    }

    /**
     * Logs out the currently logged in bank.
     */
    private static void logout() {
        System.out.printf("\n%s logged out.\n", loggedBank.getName()) ;
        loggedBank = null;
    }

    /**
     * Displays the bank menu and allows user interaction.
     */
    public static void bankInit(){
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
                    break;
                default:
                    System.out.println("Input error: Invalid input. \n");
            }

            if (opt.equals("3")) {
                break;
            }
        }
    }

    /**
     * Creates a new bank and adds it to the registered banks.
     */
    public static void createNewBank(){
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

        try {
            Bank newBank = new Bank(bank_id, bank_name.getFieldValue(), bank_passcode.getFieldValue());
            addBank(newBank);
            System.out.println("New bank successfully created!");

        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid number format for bank name or bank passcode");
        }
    }

    /**
     * Adds a new bank to the bank registry
     * @param b: the bank object to be added
     */
    private static void addBank(Bank b) {
        Bank.add(b);
        System.out.printf("\n%s has been added to the Banks! \n", b.getName());
    }
}
