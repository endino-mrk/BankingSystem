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
            LoanManager.adjustLoanAmount(account, -amount);
            AccountDBManager.updateAccountLoan(account);
            generateTransaction((Account) account, amount);
            System.out.println("Recompense successful!");
            return true;
        }
        System.out.println("Recompense unsuccessful! Amount to compensate is greater than current loan.");
        return false;
    }

    private static void generateTransaction(Account account, double amount) {
        String description = String.format("-%.2f paid to this account's credit.", amount);
        TransactionLogService.logTransaction(account, Transaction.Transactions.Recompense, description);
    }
}