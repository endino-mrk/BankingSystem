package services;
import account.BalanceHolder;

/**
 * Manages balance amount
 */
public class BalanceManager {
    /**
     * Validates whether this savings account has enough balance to proceed with such a transaction based on the amount that is to be adjusted.
     * @param account The account being referenced
     * @param amount Amount of money to be supposedly adjusted from this account’s balance.
     * @return Flag if transaction can proceed by adjusting the account balance by the amount to be changed.
     */
    public static boolean hasEnoughBalance(BalanceHolder account, double amount){
        return (account.getBalance() - amount) >= 0;
    }

    /**
     * Warns the account owner that their balance is not enough for the transaction to proceed successfully.
     */
    public static void insufficientBalance(){
        System.out.println("Account has insufficient balance to proceed with transaction.");
    }

    /**
     * Adjust the account balance of this savings account based on the amount to be adjusted. If it results to the account balance going less than 0.0, then it is forcibly reset to 0.0.
     * @param account The account being referenced
     * @param amount Amount to be added or subtracted from the account balance.
     */
    public static void adjustAccountBalance(BalanceHolder account, double amount){
        account.setBalance(account.getBalance() + amount);
        if (account.getBalance() < 0){
            account.setBalance(0);
        }
    }
}
