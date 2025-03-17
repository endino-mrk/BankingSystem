package transactions;
import accounts.BalanceHolder;

public interface Withdrawable extends BalanceHolder{
    /**
     * Withdraws an amount of money using a given medium.
     * @param amount Amount of money to be withdrawn from.
     */
    public boolean withdrawal(double amount);
}
