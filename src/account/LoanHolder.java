package account;

/**
 * Interface for accounts that can hold loans.
 */
public interface LoanHolder {
    public void setLoan(double amount);
    public double getLoan();
}
