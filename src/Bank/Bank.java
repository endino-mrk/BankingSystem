package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Field;
import Main.FieldValidator;

import java.util.ArrayList;
import java.util.Comparator;

public class Bank {
    private int ID;

    private String name, passcode;

    /**
     * depositLimit - The amount of money each Savings Account registered to this bank can deposit at every transaction. Defaults to 50,000.0
     *
     * withdrawLimit - The amount of money withdrawable / transferrable at once, restricted to every Savings Account registered to this bank. Defaults to 50,000.0
     *
     * creditLimit - Limits the amount of credit or loan that all Credit Accounts, registered on this bank, can handle all at once. Defaults to 100,000.0
     */
    private final double depositLimit, withdrawLimit, creditLimit;

    /**
     * Processing fee added when some transaction is involved with another bank. Cannot be lower than 0.0. Defaults to 10.00
     */
    private double processingFee;

    public final ArrayList<Account> BANKACCOUNTS;

    /**
     *
     * @param ID
     * @param name
     * @param passcode
     */
    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = 50000;
        this.withdrawLimit = 50000;
        this.creditLimit = 100000;
        this.processingFee = 10;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    /**
     *
     * @param ID
     * @param name
     * @param passcode
     * @param depositLimit
     * @param withdrawLimit
     * @param creditLimit
     */
    public Bank(int ID, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    /** Show accounts based on option.
     *
     * @param accountType - Type of account to be shown.
     * @param <T>
     */
    public <T> void showAccounts(Class<T> accountType) {

    }

    /** Get the Account object (if it exists) from a given bank.
     *
     * @param bank -  Bank to search from.
     * @param accountNum -  Account number of target account.
     * @return - target Account
     */
    public Account getBankAccount(Bank bank, String accountNum) {
        return;
    }

    /** Handles the processing of inputting the basic information of the account.
     *
     * @return Array list of Field objects, which are the basic account information of the account user.
     */
    public ArrayList<Field<String, ?>>  createNewAccount() {
        // Array List containing all user basic info of Field type
        ArrayList<Field<String, ?>> fields = new ArrayList<>();

        // Create Field objects for every basic info
        Field<String, Integer> accountNumber = new Field("account number", String.class, 12, new Field.StringFieldLengthValidator());
        Field<String, String> fName = new Field("first name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> lName = new Field("last name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> email = new Field("email", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> pin = new Field("pin", String.class, 6, new Field.StringFieldLengthValidator());

        // Set every Field's value
        accountNumber.setFieldValue("Enter account number (must be 12 digits): ");
        fName.setFieldValue("Enter first name: ");
        lName.setFieldValue("Enter last name: ");
        email.setFieldValue("Enter email address: ");
        pin.setFieldValue("Enter pin (must be 6 digits): ");

        // Add each Field to the info ArrayList
        fields.add(accountNumber);
        fields.add(fName);
        fields.add(lName);
        fields.add(email);
        fields.add(pin);

        return fields;
    }

    /** Create a new credit account. Utilizes the createNewAccount() method.
     *
     * @return A new Credit Account
     */
    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, ?>> info = createNewAccount();
        return new CreditAccount(this, info.get(0).getFieldValue(), info.get(1).getFieldValue(), info.get(2).getFieldValue(), info.get(3).getFieldValue(), info.get(4).getFieldValue());
    }

    /** Create a new savings account. Utilizes the createNewAccount() method.
     *
     * @return A new Savings Account
     */
    public SavingsAccount createNewSavingsAccount() {
        ArrayList<Field<String, ?>> info = createNewAccount();

        // Initializes and sets initial balance of account
        Field<Double, Double> balance = new Field("balance", Double.class, 0.0, new Field.DoubleFieldValidator());
        balance.setFieldValue("Enter initial balance: ");

        return new SavingsAccount(this, info.get(0).getFieldValue(), info.get(1).getFieldValue(), info.get(2).getFieldValue(), info.get(3).getFieldValue(), info.get(4).getFieldValue(),balance.getFieldValue());
    }

    /** Adds a new account to this bank, if the account number of the new account does not exist inside
     the bank.
     *
     * @param account - Account object to be added into this bank.
     */
    public void addNewAccount(Account account) {

    }

    /** Checks if an account object exists into a given bank based on some account number.
     *
     * @param bank - Bank to check if account exists.
     * @param accountNum - Account number of target account to check.
     * @return
     */
    public static boolean accountExists(Bank bank, String accountNum) {
        return;
    }

    public String toString() {
        return "";
    }

    /**
     * A comparator that compares if two bank objects are the same.
     */
    public class BankComparator implements Comparator {

    }

    /**
     * A comparator that compares if two bank objects have the same bank id.
     */
    public class BankIDComparator implements Comparator {

    }

    /**
     * A comparator that compares if two bank objects share the same set of credentials.
     */
    public class BankCredentialsComparator implements Comparator {

    }

}
