package services.transaction;

import account.Account;
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

            generateTransaction((Account) source, (Account) recipient, amount);

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

    private static void generateTransaction(Account source, Account recipient, double amount) {
        String sourceDesc = String.format("+%.2f paid to %s with Acc. No. %s.", amount, recipient.getOwnerFullName(), recipient.getAccountNumber());
        TransactionLogService.logTransaction(source, Transaction.Transactions.Payment, sourceDesc);

        // record transaction of recipient
        String recipientDesc = String.format("+%.2f received from %s with Acc. No. %s", amount, source.getOwnerFullName(), source.getAccountNumber());
        TransactionLogService.logTransaction(recipient, Transaction.Transactions.Payment, recipientDesc);
    }
}
