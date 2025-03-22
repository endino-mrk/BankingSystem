package services.transaction;

import account.Account;
import account.LoanHolder;
import bank.Bank;
import database.sqlite.BankDBManager;
import services.LoanManager;

/**
 * Manages credit-related transactions.
 */
public class CreditService {
//    public static boolean credit(LoanHolder account, double amount) {
//        if (canCredit(account, amount)) {
//            LoanManager.adjustLoanAmount((LoanHolder)account, amount);
//            //insert Update to database logic
//            return true;
//        }
//        return false;
//    }

    /**
     * Checks if this credit account can do additional credit transactions if the amount to credit will not exceeded the credit limit set by the bank associated to this Credit Account.
     * @param account The account being referenced
     * @param amount The amount of credit to be adjusted once the said transaction is processed.
     * @return Flag if this account can continue with the credit transaction.
     */
    public static boolean canCredit(LoanHolder account, double amount){
        double creditLimit = BankDBManager.getBankCreditLimit(((Account) account).getBankID());
        if (account.getLoan() + amount > creditLimit) {
            System.out.printf("This account's total loan must not go above %.2f.", creditLimit);
            return false;
        }
        return true;
    }
}
