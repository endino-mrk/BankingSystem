package services.transaction;

import account.Account;
import account.BalanceHolder;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import services.BalanceManager;
import bank.Bank;

/**
 * Manages fund transfers between accounts.
 */
public class TransferService {
    public static boolean transfer(BalanceHolder source, String recipient, double amount, double processingFee) {
        if (BalanceManager.hasEnoughBalance(source, amount)) {
            // Update source balance
            BalanceManager.adjustAccountBalance(source, -1 * (amount + processingFee)); // in memory
            AccountDBManager.updateAccountBalance(((Account) source).getAccountNumber(), -1 * (amount + processingFee));

            // update recipient balance
            AccountDBManager.updateAccountBalance(recipient, amount);

            // record transfer transaction for source and recipient
            generateTransaction(((Account) source).getAccountNumber(), recipient, amount);
            System.out.println("Transfer successful!");
            return true;
        }
        BalanceManager.insufficientBalance();
        System.out.print("Transfer unsuccessful!");
        return false;
    }

    private static void generateTransaction(String source, String recipient, double amount) {
        String sourceDesc = String.format("-%.2f transferred from this account to Acc. No. %s.", amount, recipient);
        TransactionLogService.logTransaction(source, Transaction.Transactions.FundTransfer, sourceDesc);

        String recipientDesc = String.format("+%.2f transferred to this account from Acc. No. %s.", amount, source);
        TransactionLogService.logTransaction(recipient, Transaction.Transactions.FundTransfer, recipientDesc);
    }
}
