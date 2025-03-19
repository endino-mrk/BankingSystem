package services.bank;

import bank.Bank;

public class BankLoginService {
    private Bank loggedBank = null;

    public BankLoginService() {};
    public Bank getLoggedBank() {
        return loggedBank;
    }

    /**
     * Checks if a bank account is currently logged in.
     * @return `true` if a bank account is logged in, otherwise `false`.
     */
    public boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Creates a new login session for the logged in bank user. Sets up a new value for the loggedBank
     * field.
     * @param b : Bank user that successfully logged in.
     */
    public void setLogSession(Bank bank) {
        if (loggedBank == null) {
            loggedBank = bank;
            System.out.printf("\nBank '%s' successfully logged in! \n", bank.getName());
        }
    }

    /**
     * Destroys the login session for the current user.
     */
    public void logout() {
        System.out.printf("\n%s logged out.\n", loggedBank.getName());
        loggedBank = null;
    }
}


