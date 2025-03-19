package services.transaction;

import account.Account;
import account.BalanceHolder;
import bank.Bank;
import database.Repository;
import services.BalanceManager;

import java.util.Map;

/**
 * Handles deposit operation of balance
 */
public class DepositService {
    private Repository repository;

    public DepositService(Repository repository) {
        this.repository = repository;
    }

    public boolean deposit(Account account, double amount) {
        // Amount must not go above bank deposit limit.
        if (!(account instanceof BalanceHolder)){
            System.out.println("Account is not a balance holder");
            return false;
        }

        if (amount <= account.getBank().getWithdrawLimit()) {
            BalanceManager.adjustAccountBalance((BalanceHolder) account, amount);

            if (repository.update(account.getAccountNumber(), Map.of("balance", ((BalanceHolder) account).getBalance()))) {
                System.out.printf("Successfully deposited %.2f to this account", amount);
                return true;
            }
        }
        // System.out.println("Unable to deposit, amount surpasses bank deposit limit.");
        return false;
    }
}
