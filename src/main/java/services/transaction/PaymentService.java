package services.transaction;

import account.Account;
import account.BalanceHolder;
import account.CreditAccount;
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
    public static boolean pay(LoanHolder source, String recipient, double amount) {
//        if (CreditService.canCredit(source, amount)) {
//            LoanManager.adjustLoanAmount(source, amount);
//            AccountDBManager.updateAccountLoan(source);
//            BalanceManager.adjustAccountBalance(recipient, amount);
//            AccountDBManager.updateAccountBalance(recipient);
//
//            generateTransaction((Account) source, (Account) recipient, amount);
//
//            System.out.println("Payment successful!");
//            return true;

        if (CreditService.canCredit(source, amount)) {
            // updates loan of source
            LoanManager.adjustLoanAmount(source, amount); // in memory
            AccountDBManager.updateAccountLoan(((Account) source).getAccountNumber(), amount); // in db

            // updates balance of recipient in db
            AccountDBManager.updateAccountBalance(recipient, amount);

            // record transaction for source and recipient
            generateTransaction(((Account) source).getAccountNumber(), recipient, amount);
            System.out.println("Payment successful!");
            return true;
        }
//        }
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
//    public static boolean pay(BalanceHolder source, BalanceHolder recipient, double amount) {
////        if (BalanceManager.hasEnoughBalance(source, amount)) {
////            BalanceManager.adjustAccountBalance(source, -amount);
////            AccountDBManager.updateAccountBalance(source);
////            BalanceManager.adjustAccountBalance(recipient, amount);
////            AccountDBManager.updateAccountBalance(recipient);
////            System.out.println("Payment successful!");
////        }
////        BalanceManager.insufficientBalance();
////        System.out.print("Payment unsuccessful!");
//        return false;
//    }

    private static void generateTransaction(String source, String recipient, double amount) {
        // record transaction of source account
        String sourceDesc = String.format("+%.2f paid to Acc. No. %s.", amount, recipient);
        TransactionLogService.logTransaction(source, Transaction.Transactions.Payment, sourceDesc);

        // record transaction of recipient account
        String recipientDesc = String.format("+%.2f received from  Acc. No. %s", amount, source);
        TransactionLogService.logTransaction(recipient, Transaction.Transactions.Payment, recipientDesc);
    }
}
