package services;
import account.Account;
import account.LoanHolder;

/**
 * Manages loan-related operations
 */
public class LoanManager {
    private LoanHolder account;
    private double amount;

    public LoanManager(LoanHolder account, double amount){
        this.account = account;
        this.amount = amount;
    }

    /**
     * Checks if this credit account can do additional credit transactions if the amount to credit will not exceeded the credit limit set by the bank associated to this Credit Account.
     * @param account The account being referenced
     * @param amountAdjustment The amount of credit to be adjusted once the said transaction is processed.
     * @return Flag if this account can continue with the credit transaction.
     */
    public static boolean canCredit(Account account, double amountAdjustment){
        LoanHolder a = (LoanHolder) account;
        return (a.getLoan() + amountAdjustment) <= account.getBank().getCreditLimit();
    }

    /**
     * Adjust the owner’s current loan. Result of adjustment cannot be less than 0.
     * @param account The account be referenced
     * @param amountAdjustment Amount to be adjusted to the loan of this credit account.
     */
    public static void adjustLoanAmount(LoanHolder account, double amountAdjustment){
        if (account.getLoan() + amountAdjustment < 0){
            System.out.println("Loan amount must not be less than 0.");
        } else {
            account.setLoan(account.getLoan() + amountAdjustment);
        }
    }
}
