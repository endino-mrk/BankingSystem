package services.transaction;

import account.Account;
import account.BalanceHolder;
import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import services.BalanceManager;

/**
 * Handles deposit operation of balance
 */
public class DepositService {
    public static boolean cashDeposit(BalanceHolder account, double amount) {
        // Amount must not go above bank deposit limit.
        if (canDeposit(account, amount)) {
            BalanceManager.adjustAccountBalance(account, amount);
            AccountDBManager.updateAccountBalance(((Account) account).getAccountNumber(), amount);

            generateTransaction(((Account) account).getAccountNumber(), amount);

            System.out.println("Deposit successful!");
            return true;
            }
        System.out.println("\nDeposit unsuccessful!");
        return false;
    }

    private static boolean canDeposit(BalanceHolder account, double amount) {
        // fetch bank deposit limit from database
        double depositLimit = BankDBManager.getBankDepositLimit(((Account) account).getBankID());
        if (amount > depositLimit) {
            System.out.printf("Amount to deposit must not go above %.2f.", depositLimit);
            return false;
        }
        return true;
    }

    private static void generateTransaction(String accountID, double amount) {
        String description = String.format("+%.2f via Cash Deposit.", amount);
        TransactionLogService.logTransaction(accountID, Transaction.Transactions.Deposit, description);
    }
}
