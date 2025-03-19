package services.transaction;

import account.Account;
import account.BalanceHolder;
import account.LoanHolder;
import services.BalanceManager;
import services.LoanManager;

/**
 * Handles payment processing.
 */
public class PaymentService {
    public static boolean pay(Account source, BalanceHolder recipient, double amount) {
        if (LoanManager.canCredit(source, amount)) {
            LoanManager.adjustLoanAmount((LoanHolder) source, amount);
            BalanceManager.adjustAccountBalance(recipient, amount);
            //insert Update to database logic
            return true;
        }
        return false;
    }
}
