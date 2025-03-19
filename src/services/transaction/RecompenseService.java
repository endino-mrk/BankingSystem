package services.transaction;

import account.LoanHolder;
import services.LoanManager;

/**
 * Manages recompense transactions.
 */
public class RecompenseService {
    public static boolean recompense(LoanHolder account, double amount) {
        if (account.getLoan() - amount >= 0) {
            LoanManager.adjustLoanAmount(account, -amount);
            // insert update amount to database
            return true;
        }
        System.out.println("Amount to compensate is greater than current loan.");
        return false;
    }
}