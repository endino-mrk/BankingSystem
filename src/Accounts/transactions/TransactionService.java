package accounts.transactions;
import accounts.BalanceHolder;
import bank.Bank;
import accounts.IllegalAccountType;
import accounts.Account;
import accounts.LoanHolder;

/**
 * Handles all transaction-related logic. 
 */
public class TransactionService {

    /**
     * Validates whether this savings account has enough balance to proceed with such a transaction based on the amount that is to be adjusted. 
     * @param account 
     * @param amount Amount of money to be supposedly adjusted from this account’s balance.
     * @return Flag if transaction can proceed by adjusting the account balance by the amount to be changed.
     */
    private static boolean hasEnoughBalance(BalanceHolder account, double amount) {
        return (account.getBalance() - amount) >= 0;
    }

    /**
     * Warns the account owner that their balance is not enough for the transaction to proceed successfully.
     */
    private static void insufficientBalance() {
        System.out.println("Account has insufficient balance to proceed with transaction.");
    }

        /**
     * Adjust the account balance of this account based on the amount to be adjusted. If it results to the account balance going less than 0.0, then it is forcibly reset to 0.0. 
     * @param account Account to adjust the balance. 
     * @param amount Amount to be added or subtracted from the account balance.
     */
    private static void adjustAccountBalance(BalanceHolder account, double amount) {
        account.setBalance(account.getBalance() + amount);
        if (account.getBalance() < 0) {
            account.setBalance(0);
        }
    }

    /**
     * @param bank Bank that account object belongs to
     * @param account An account object that can do deposits.
     * @param amount Amount of money to be deposited to the account.
     * @return Flag if transaction is successful or not.
     */
    public static boolean cashDeposit(Bank bank, Depositable account, double amount) {
        // Amount must not go above bank deposit limit.
        if (amount <= bank.getDepositLimit()) {
            adjustAccountBalance(account, amount);
            System.out.printf("Successfully deposited %.2f to this account", amount);
            return true;
        }
        // System.out.println("Unable to deposit, amount surpasses bank deposit limit.");
        return false;
    }

    public static boolean withdraw(Bank bank, Withdrawable account, double amount) {
        // Amount must not go above bank withdrawal limit
        if (amount <= bank.getWithdrawLimit()) {
            if(hasEnoughBalance(account, amount)) {
                // Subtracts amount from balance
                adjustAccountBalance(account, -amount);
                System.out.printf("Successfully withdrawed %.2f from this account", amount);
                return true;
            } 

            insufficientBalance();
        }
        return false;
    }

    /**
     * 
     * @param sourceBank Bank of source account
     * @param source Account doing the transfer
     * @param recipient Account to receive transfer amount
     * @param amount Amount of money to be transferred
     * @throws ILlegalAccountType when recipient account is not a BalanceHolder account. 
     * @return
     */
    public static boolean transfer(Bank sourceBank, Transferrable source, Account recipient, double amount) throws IllegalAccountType{

        if (!(recipient instanceof BalanceHolder)) {
            throw new IllegalAccountType("Recipient account cannot receive fund transfers.");
        }

        
        if(hasEnoughBalance(source, amount)) {
            // Subtracts amount from source account balance
            adjustAccountBalance(source, -amount);
            
            // If transferring to another bank, adjusts amount so processing fee is not accounted for in the fund transfer amount
            if (!(Bank.accountExists(sourceBank, recipient.getAccountNumber()))) {
                amount -= recipient.getBank().getProcessingFee();
            }

            // Adds amount to recipient account balance
            adjustAccountBalance((BalanceHolder) recipient, amount); 
            System.out.printf("Successfully transferred %.2f from this account to account with account number %s", amount, recipient.getAccountNumber());
            return true;
        }   
        
        insufficientBalance();
        return false;
    }

    /**
     * Checks if an account can do additional credit transactions if the amount to credit will not exceed the credit limit set by the bank associated to this account. 
     * @param bank Bank associated with account doing the credit
     * @param account Account to do credit
     * @param amountAdjustment The amount of credit to be adjusted once the said transaction is processed. 
     * @return Flag if this account can continue with the credit transaction.
     */
    private static boolean canCredit(Bank bank, LoanHolder account, double amountAdjustment) {
        return (account.getLoan() + amountAdjustment) <=bank.getCreditLimit();
    }

    /**
     * Adjust the owner’s current loan. Result of adjustment cannot be less than 0.
     * @param account
     * @param amountAdjustment Amount to be adjusted to the loan of this credit account. 
     */
    private static void adjustLoanAmount(LoanHolder account, double amountAdjustment) {
        if (account.getLoan() + amountAdjustment < 0) {
            System.out.println("Loan amount must not be less than 0.");
        } else {
            account.setLoan(account.getLoan() + amountAdjustment);
        }
    }

    public static boolean pay(Bank sourceBank, Payable source, BalanceHolder recipient, double amount) {
        if (canCredit(sourceBank, source, amount)) {
            adjustLoanAmount(source, amount);
            adjustAccountBalance(recipient, amount);
            return true;
        }
        return false;
    }

    public static boolean recompense(Compensable account, double amount) {
        if (account.getLoan() - amount >= 0) {
            adjustLoanAmount(account, -amount);
            return true;
        }
        System.out.println("Amount to compensate is greater than current loan.");
        return false;
    }


}
