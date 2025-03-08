package Launcher;

import Bank.Bank;

import java.util.ArrayList;

public class BankLauncher {
    private static ArrayList<Bank> Banks = new ArrayList<>();

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
    public static void bankInit() {
        if (!isLogged()) {
            System.out.println("Not logged in to any bank.");
            return;
        }
        java.util.Scanner s = new java.util.Scanner(System.in);
        String c;

        quack:
        while (true) {
            System.out.print("Bank Interaction Menu\n(1) Create New Account\n(2) View Accounts\n(3) Log Out");
            c = s.nextLine().trim();
            System.out.println();
            
            switch (c) {
                case "1":
                    newAccounts();
                    break;
                case "2":
                    showAccounts();
                    break;
                case "3":
                    logout();
                    break quack;
            }
        }

        System.out.println("Logged out of the bank.");
        s.close();
    }

    /**
     * Account viewing interface.
     */
    private static void showAccounts() {
        java.util.Scanner s = new java.util.Scanner(System.in);
        String type;

        label: 
        while (true) {
            System.out.print("Type of account to create:\n(1) Credit Account\t(2) Savings Account\3(3) All Types (4) Cancel");
            type = s.nextLine().trim();

            switch (type) {
                case "1":
                    loggedBank.showAccounts(Accounts.CreditAccount.class);
                    break label;
                case "2":
                    loggedBank.showAccounts(Accounts.SavingsAccount.class);
                    break label;
                case "3":
                    loggedBank.showAccounts(Accounts.Account.class);
                    break label;
                case "4":
                    break label;
            }
        }

        s.close();
    }

    /**
     * Account creation interface.
     */
    private static void newAccounts() {
        java.util.Scanner s = new java.util.Scanner(System.in);
        String type;

        label:
        while (true) {
            System.out.print("Type of account to create:\n(1) Credit Account\t(2) Savings Account\3(3) Cancel");
            type = s.nextLine().trim();
            Accounts.Account a = null;

            switch (type) {
                case "1":
                    a = loggedBank.createNewCreditAccount();
                    break;
                case "2":
                    a = loggedBank.createNewSavingsAccount();
                    break;
                case "3":
                    break label;
            }

            if (a != null) {
                loggedBank.addNewAccount(a);
                String t = (type.equals("1")) ? "Credit" : "Savings";
                System.out.println(t + " account has been created and added to bank.");
                break;
            }
        }

        s.close();
    }

    /**
     * Bank login interface.
     */
    public static void bankLogin() {
        if (isLogged()) {
            System.out.println("Already logged in to a bank.");
            return;
        }

        java.util.Scanner s = new java.util.Scanner(System.in);

        String name = "";
        String passcode = "";


        System.out.print("Enter name of bank: ");
        name = s.nextLine().trim();

        System.out.print("\nEnter passcode: ");
        passcode = s.nextLine().trim();
        
        Bank b = getBank(new Bank.BankCredentialsComparator(), new Bank(0, name, passcode));
        if (b != null) {
            setLogSession(b);
            System.out.println("Logged in to " + name + "bank.");
        } else {
            System.err.println("Wrong bank credentials!");
        }

        s.close();
    }

    /**
     * Sets the current log session into the bank to be logged in.
     * @param b - Bank to log in
     */
    private static void setLogSession(Bank b) {
        if (loggedBank == null) {
            loggedBank = b;
        }
    }

    /**
     * Logs out of the currently logged bank.
     */
    private static void logout() {
        if (loggedBank != null) {
            int index = Banks.indexOf(loggedBank);
            Banks.set(index, loggedBank);
            loggedBank = null;
        }
    }

    /**
     * Bank creation interface.
     * 
     * @throws NumberFormatException Thrown when an invalid input is given 
     *  for deposit, withdraw, credit limit, and processing fee.
     */
    public static void createNewBank() throws NumberFormatException {
        java.util.Scanner s = new java.util.Scanner(System.in);

        int id = bankSize() + 1; // not sure if i should be from user input or is based from array size or something else
        String name = "";
        String passcode = "";

        int depositLimit = 50000;
        int withdrawLimit = 50000;
        int creditLimit = 100000;
        int processingFee = 10;

        // not sure if i should use fields instead.
        System.out.print("Enter name of bank: ");
        name = s.nextLine().trim();

        System.out.print("\nEnter passcode for bank: ");
        passcode = s.nextLine().trim();

        System.out.print("\nChange deposit limit? Default is 50000 (type 'y' to change). ");
        if (s.nextLine().trim().equals("y")) {
            System.out.print("\nEnter new deposit limit: ");
            depositLimit = Integer.parseInt(s.next());
        }

        System.out.print("\nChange withdraw limit? Default is 50000 (type 'y' to change). ");
        if (s.nextLine().trim().equals("y")) {
            System.out.print("\nEnter new withdraw limit: ");
            withdrawLimit = Integer.parseInt(s.next());
        }

        System.out.print("\nChange credit limit? Default is 100000 (type 'y' to change). ");
        if (s.nextLine().trim().equals("y")) {
            System.out.print("\nEnter new credit limit: ");
            creditLimit = Integer.parseInt(s.next());
        }

        System.out.print("\nChange processing fee? Default is 10 (type 'y' to change). ");
        if (s.nextLine().trim().equals("y")) {
            System.out.print("\nEnter new processing fee: ");
            processingFee = Integer.parseInt(s.next());
        }

        s.close();
        addBank(new Bank(id, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee));
        System.out.println("\nBank has been added.");
    }

    /**
     * Shows all the registered banks.
     */
    public static void showBanksMenu() {
        System.out.println("\n============= ALL BANKS =============");
        for (Bank b : Banks) {
            System.out.println(b);
        }
        System.out.println("\n=====================================");
    }

    /**
     * Adds the passed Bank object to the ArrayList.
     * @param b - Bank object to add
     */
    private static void addBank(Bank b) {
        Banks.add(b);
    }

    /**
     * Checks if a bank exists based on a comparator criteria.
     * 
     * @param comparator - Comparator<Bank> object; used as criteria for searching
     * @param bank - Bank object to be compared
     * @return Bank
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
     * Finds (and returns) the Account based on account number on all registered banks.
     *
     * @param accountNumber
     * @return Account - The account that matches the account number on all registered banks.
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
     * Returns the number of currently registered banks.
     * 
     * @return int - Number of currently registered banks.
     */
    public static int bankSize() {
        return Banks.size();
    }
}
