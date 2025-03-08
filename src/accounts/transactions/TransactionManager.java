package accounts.transactions;
import accounts.Account;
import bank.Bank;
import accounts.IllegalAccountType;

/**
 * A class responsible for handling all transaction-related logic.
 */
public class TransactionManager{

    private static boolean hasEnoughBalance(Account account) {
        return false;
    }

    private static void insufficientBalance(Account account) {
        
    }

    private static void adjustAccountBalance(Account account, double amount) {

    }

    private static boolean canCredit(Account account) {
        return false;
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
