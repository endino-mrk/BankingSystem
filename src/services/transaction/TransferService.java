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
    public static boolean transfer(BalanceHolder source, BalanceHolder recipient, double amount) {
        if (BalanceManager.hasEnoughBalance(source, amount)) {
            BalanceManager.adjustAccountBalance(source, -amount);
            AccountDBManager.updateAccountBalance(source);

            // if recipient account is from another bank, subtracts source bank processing fee from the fund transfer amount
            if (!((Account) source).getBankID().equals(((Account) recipient).getBankID())) {
                Bank bank = BankDBManager.fetchBank(((Account) source).getBankID());
                amount -= bank.getProcessingFee();
            }

            BalanceManager.adjustAccountBalance(recipient, amount);
            AccountDBManager.updateAccountBalance(recipient);
            System.out.println("Transfer successful!");
            return true;
        }
        BalanceManager.insufficientBalance();
        System.out.print("Transfer unsuccessful!");
        return false;
    }

    public static boolean transfer(Bank sourceBank, BalanceHolder source, BalanceHolder recipient, double amount) {
        return transfer(source, recipient, amount + sourceBank.getProcessingFee());
    }
}
