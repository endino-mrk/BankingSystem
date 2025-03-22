package services.transaction;

import account.Account;
import account.LoanHolder;
import database.sqlite.AccountDBManager;
import services.LoanManager;

/**
 * Manages recompense transactions.
 */
public class RecompenseService {
    public static boolean recompense(LoanHolder account, double amount) {
        if (account.getLoan() - amount >= 0) {
            // update loan
            LoanManager.adjustLoanAmount(account, (-1 * amount));
            AccountDBManager.updateAccountLoan(((Account) account).getAccountNumber(), (-1 * amount));
            // record loan transaction
            generateTransaction(((Account) account).getAccountNumber(), amount);
            System.out.println("Recompense successful!");
            return true;
        }
        System.out.println("Recompense unsuccessful! Amount to compensate is greater than current loan.");
        return false;
    }

    private static void generateTransaction(String accountNumber, double amount) {
        String description = String.format("-%.2f paid to the credit of this account.", amount);
        TransactionLogService.logTransaction(accountNumber, Transaction.Transactions.Recompense, description);
    }
}