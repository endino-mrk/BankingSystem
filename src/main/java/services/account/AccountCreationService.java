package services.account;

import account.Account;
import account.CreditAccount;
import account.SavingsAccount;
import bank.Bank;
import database.sqlite.AccountDBManager;
import main.Field;
import services.IDGenerator;

import java.util.ArrayList;

public class AccountCreationService {
    /** Handles the processing of inputting the basic information of the account.
     *
     * @return Array list of Field objects, which are the basic account information of the account user.
     */
    private static ArrayList<Field<String, ?>> createNewAccount() {
        // Array List containing all user basic info of Field type
        ArrayList<Field<String, ?>> fields = new ArrayList<>();

        // Create Field objects for every basic info
        Field<String, String> fName = new Field("first name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> lName = new Field("last name", String.class, "", new Field.StringFieldValidator());
        Field<String, String> email = new Field("email", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> pin = new Field("pin", String.class, 6, new Field.StringFieldLengthValidator());

        // Account numbers must be unique across all accounts regardless of bank


        // Set every Field's value
        fName.setFieldValue("Enter first name: ", false);
        lName.setFieldValue("Enter last name: ");
        email.setFieldValue("Enter email address: ");
        pin.setFieldValue("Enter pin (must be 6 digits): ");

        // Add each Field to the info ArrayList
        fields.add(fName);
        fields.add(lName);
        fields.add(email);
        fields.add(pin);

        return fields;
    }


    /** Create a new credit account. Utilizes the createNewAccount() method.
     * @param bank Bank object which this account will be registered/added into.
     * @return A new Credit Account
     */
    public static void createNewCreditAccount(Bank bank) {
        ArrayList<Field<String, ?>> info = createNewAccount();
        String accountNumber = IDGenerator.accountIDGenerator(bank);

        CreditAccount creditAccount = new CreditAccount(bank.getID(), accountNumber, info.get(0).getFieldValue(), info.get(1).getFieldValue(), info.get(2).getFieldValue(), info.get(3).getFieldValue());
        addNewAccount(bank, creditAccount);
    }

    /** Create a new savings account. Utilizes the createNewAccount() method.
     *
     * @return A new Savings Account
     */
    public static void createNewSavingsAccount(Bank bank) {
        ArrayList<Field<String, ?>> info = createNewAccount();

        // Initializes and sets initial balance of account
        Field<Double, Double> balance = new Field("balance", Double.class, 500.0, new Field.DoubleFieldValidator());
        balance.setFieldValue("Enter initial balance (>= 500.00): ");

        String accountNumber = IDGenerator.accountIDGenerator(bank);

        SavingsAccount savingsAccount = new SavingsAccount(bank.getID(), accountNumber, info.get(0).getFieldValue(), info.get(1).getFieldValue(), info.get(2).getFieldValue(), info.get(3).getFieldValue(), balance.getFieldValue());
        addNewAccount(bank, savingsAccount);
    }

    /** Adds a new account to this bank, if the account number of the new account does not exist inside
     the bank.
     *
     * @param account - Account object to be added into this bank.
     */
    private static void addNewAccount(Bank bank, Account account) {
        if (AccountDBManager.existsInBank(bank.getID(), account.getAccountNumber())) {
            System.out.println("This account already exists in the given bank.");
            return;
        }
        AccountDBManager.addAccount(account);
    }
}
