package services.transaction;

import account.BalanceHolder;
import account.LoanHolder;
import database.sqlite.AccountDBManager;
import services.BalanceManager;
import services.LoanManager;

/**
 * Handles payment processing.
 */
public class PaymentService {
    /**
     * Payment through loan
     * @param source
     * @param recipient
     * @param amount
     * @return
     */
    public static boolean pay(LoanHolder source, BalanceHolder recipient, double amount) {
        if (CreditService.canCredit(source, amount)) {
            LoanManager.adjustLoanAmount(source, amount);
            AccountDBManager.updateAccountLoan(source);
            BalanceManager.adjustAccountBalance(recipient, amount);
            AccountDBManager.updateAccountBalance(recipient);
            System.out.println("Payment successful!");
            return true;
        }
        System.out.println("Payment unsuccessful!");
        return false;
    }

    /**
     * Pay through balance
     * @param source
     * @param recipient
     * @param amount
     * @return
     */
    public static boolean pay(BalanceHolder source, BalanceHolder recipient, double amount) {
        if (BalanceManager.hasEnoughBalance(source, amount)) {
            BalanceManager.adjustAccountBalance(source, -amount);
            AccountDBManager.updateAccountBalance(source);
            BalanceManager.adjustAccountBalance(recipient, amount);
            AccountDBManager.updateAccountBalance(recipient);
            System.out.println("Payment successful!");
        }
        BalanceManager.insufficientBalance();
        System.out.print("Payment unsuccessful!");
        return false;
    }
}
