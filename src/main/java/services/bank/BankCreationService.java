package services.bank;

import bank.Bank;
import database.sqlite.BankDBManager;
import main.Field;
import main.Main;
import services.IDGenerator;

/**
 * Class responsible for creating banks.
 */
public class BankCreationService {
    /**
     * Creates a new bank record. Utilized separately from the rest of the methods of this class.
     * Throws:
     *      NumberFormatException – May happen when inputting deposit, withdraw, and credit limit,
     *      and processing fee.
     */
    public static void createNewBank() {

        Main.showMenuHeader("Creating a New Bank");

        Field<String, String> bankName = new Field<>("bank name", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> bankPasscode = new Field<>("bank passcode", String.class, 8, new Field.StringFieldLengthValidator());

        // Bank name field
        bankName.setFieldValue("Enter Bank Name: ", false);

        // Randomized ID
        String bankID = IDGenerator.bankIDGenerator(bankName.getFieldValue());
        System.out.println("Generated Bank ID: " + bankID);

        // Bank passcode field
        while (bankPasscode.getFieldValue() == null || bankPasscode.getFieldValue().length() != 8) {
            bankPasscode.setFieldValue("Enter Bank Passcode (must be exactly 8 digits): ");
        }

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

                    BankDBManager.addBank(new Bank(bankID, bankName.getFieldValue(), bankPasscode.getFieldValue(), depositLimit.getFieldValue(), withdrawLimit.getFieldValue(), creditLimit.getFieldValue(),processingFee.getFieldValue()));
                }
                else if (choice.equals("n")) {
                    BankDBManager.addBank(new Bank(bankID, bankName.getFieldValue(), bankPasscode.getFieldValue()));
                    break;
                }
                else {
                    validChoice = false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid number format for deposit limit, withdraw limit, credit limit, or processing fee.");
        }
    }


}