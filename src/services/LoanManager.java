package services;
import account.LoanHolder;

/**
 * Manages loan-related operations
 */
public class LoanManager {

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
