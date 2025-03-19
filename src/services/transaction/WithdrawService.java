package services.transaction;

import account.BalanceHolder;
import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import services.BalanceManager;
import account.Account;

/**
 * Handles withdrawal operation of balance
 */
public class WithdrawService {
    public boolean withdraw(BalanceHolder account, double amount) {
        // Amount must not go above bank withdrawal limit
        if (canWithdraw((Account) account, amount)){
            if(BalanceManager.hasEnoughBalance(account, amount)) {
                BalanceManager.adjustAccountBalance(account, -amount); // Subtracts amount from balance
                AccountDBManager.updateAccountBalance(account);
                System.out.printf("Withdrawal successful!");
                return true;
            }
            BalanceManager.insufficientBalance();
        }
        System.out.print("Withdrawal unsuccessful!");
        return false;
    }

    private static boolean canWithdraw(Account account, double amount) {
        Bank bank = BankDBManager.fetchBank(account.getBankID());
        if (amount > bank.getWithdrawLimit()) {
            System.out.printf("Amount to withdraw must not go above %.2f.", bank.getWithdrawLimit());
            return false;
        }
        return true;
    }
}
