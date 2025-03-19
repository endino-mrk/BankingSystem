package services.transaction;

import account.Account;
import account.BalanceHolder;
import account.LoanHolder;
import services.LoanManager;

/**
 * Manages credit-related transactions.
 */
public class CreditService {
    public static boolean credit(Account account, double amount) {
        if (LoanManager.canCredit(account, amount)) {
            LoanManager.adjustLoanAmount((LoanHolder)account, amount);
            //insert Update to database logic
            return true;
        }
        return false;
    }
}
