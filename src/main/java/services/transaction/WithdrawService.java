package services.transaction;

import account.BalanceHolder;
import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import services.BalanceManager;
import account.Account;

import java.security.IdentityScope;

/**
 * Handles withdrawal operation of balance
 */
public class WithdrawService {
    public static boolean withdraw(BalanceHolder account, double amount) {
        // Amount must not go above bank withdrawal limit
        if (canWithdraw((Account) account, amount)){
            if(BalanceManager.hasEnoughBalance(account, amount)) {
                // updates account balance
                BalanceManager.adjustAccountBalance(account, (-1 * amount)); // in memory
                AccountDBManager.updateAccountBalance(((Account) account).getAccountNumber(), (-1 * amount)); // in db

                // record transaction
                generateTransaction(((Account) account).getAccountNumber(), amount);
                System.out.printf("Withdrawal successful!");
                return true;
            }
            BalanceManager.insufficientBalance();
        }
        System.out.print("Withdrawal unsuccessful!");
        return false;
    }

    private static boolean canWithdraw(Account account, double amount) {
        double withdrawLimit = BankDBManager.getBankWithdrawLimit(account.getBankID());
        if (amount > withdrawLimit) {
            System.out.printf("Amount to withdraw must not go above %.2f.", withdrawLimit);
            return false;
        }
        return true;
    }

    private static void generateTransaction(String accountNumber, double amount) {
        String description = String.format("-%.2f withdrawed from this account's balance.", amount);
        TransactionLogService.logTransaction(accountNumber, Transaction.Transactions.Withdraw, description);
    }
}
