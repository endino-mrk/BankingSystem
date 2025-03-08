package accounts;
import bank.Bank;

/**
 * A class responsible for handling all transaction-related logic.
 */
public class TransactionManager{

    /**
     * Validates whether this savings account has enough balance to proceed with such a transaction based on the amount that is to be adjusted.
     * @param account - The Savings account being referenced
     * @param amount - Amount of money to be supposedly adjusted from this account’s balance.
     * @return Flag if transaction can proceed by adjusting the account balance by the amount to be changed.
     */
    private static boolean hasEnoughBalance(SavingsAccount account,double amount) {
        return account.getBalance() <= amount;
    }

    /**
     * Warns the account owner that their balance is not enough for the transaction to proceed successfully.
     */
    private static void insufficientBalance() {
        System.out.println("Insufficient balance.");
    }

    /**
     * Adjust the account balance of this savings account based on the amount to be adjusted. If it results to the account balance going less than 0.0, then it is forcibly reset to 0.0.
     * @param account - The Savings account being referenced
     * @param amount - Amount to be added or subtracted from the account balance.
     */
    private static void adjustAccountBalance(SavingsAccount account, double amount) {
        if(amount < account.getBalance()){
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);
        }
        else{
            account.setBalance(0.0);
        }
    }

    /**
     * Checks if this credit account can do additional credit transactions if the amount to credit will not exceeded the credit limit set by the bank associated to this Credit Account.
     * @param account - The Credit account referenced
     * @param amountAdjustment - The amount of credit to be adjusted once the said transaction is processed.
     * @return Flag if this account can continue with the credit transaction.
     */
    private static boolean canCredit(CreditAccount account, double amountAdjustment) {
        return account.getLoan() + amountAdjustment < account.getBank().getCreditLimit();
    }

    private static void adjustLoanAmount(Account account, double amount){

    }

    /**
     * Deposits an amount to an Account object, must not be greater than the deposit limit of the bank the account belongs to.  
     * @param account Account to deposit the amount to
     * @param amount Amount to deposit
     * @return
     */
    public static boolean cashDeposit(Account account, double amount) {
        return false;
    }

    /**
     * 
     * @param amount
     * @return
     */
    public static boolean withdrawal(double amount) {
        return false;
    }


    public static boolean transfer(Account account, double amount) {
        return false;
    }


    public static boolean transfer(Bank bank, Account account, double amount) {
        return false;
    }


    public static boolean recompense(double amount) {
        return false;
    }


    public static boolean pay(Account account, double amount) throws IllegalAccountType{ 
        return false;
    }




}
