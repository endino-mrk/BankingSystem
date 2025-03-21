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
    public static boolean cashDeposit(Account account, double amount) {
        // Amount must not go above bank deposit limit.
        if (!(account instanceof BalanceHolder)){
            System.out.println("Account cannot do deposits.");
            return false;
        }

        if (canDeposit(account, amount)) {
            BalanceManager.adjustAccountBalance((BalanceHolder) account, amount);
            AccountDBManager.updateAccountBalance((BalanceHolder) account);

            generateTransaction(account, amount);

            System.out.println("Deposit successful!");
            return true;
            }
        return false;
    }

    private static boolean canDeposit(Account account, double amount) {
        Bank bank = BankDBManager.fetchBank(account.getBankID());
        if (amount > bank.getDepositLimit()) {
            System.out.printf("Amount to deposit must not go above %.2f.", bank.getDepositLimit());
            return false;
        }
        return true;
    }

    private static void generateTransaction(Account account, double amount) {
        String description = String.format("+%.2f via Cash Deposit.", amount);
        TransactionLogService.logTransaction(account, Transaction.Transactions.Deposit, description);
    }
}
