package services.transaction;

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
            System.out.println("Recompense successful!");
            return true;
        }
        System.out.println("Recompense unsuccessful! Amount to compensate is greater than current loan.");
        return false;
    }
}