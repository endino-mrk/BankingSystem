package services.transaction;

import account.BalanceHolder;
import database.Repository;
import services.BalanceManager;
import account.Account;

import java.util.Map;


/**
 * Handles withdrawal operation of balance
 */
public class WithdrawService {
    private Repository repository;

    public WithdrawService(Repository repository) {
        this.repository = repository;
    }

    public boolean withdraw(Account account, double amount) {
        // Amount must not go above bank withdrawal limit
        if (!(account instanceof BalanceHolder)) {
            return false;
        }

        if (amount <= account.getBank().getWithdrawLimit()) {
            if(BalanceManager.hasEnoughBalance((BalanceHolder) account, amount)) {
                // Subtracts amount from balance
                BalanceManager.adjustAccountBalance((BalanceHolder) account, -amount);
                //insert update amount to database
                if(repository.update(account.getAccountNumber(), Map.of("balance", ((BalanceHolder) account).getBalance()))) {
                    System.out.printf("Successfully withdrew %.2f from this account", amount);
                    return true;
                }
            }
            BalanceManager.insufficientBalance();
        }
        return false;
    }
}
