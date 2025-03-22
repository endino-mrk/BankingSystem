package services.account;

import account.Account;
import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;

/**
 * Class responsible for initializing bank login sessions
 */
public class AccountLoginService {
    private Account loggedAccount = null;
    private String assocBank = null;

    public AccountLoginService() {};

    /**
     * Get the Account instance of the currently logged account.
     */
    public Account getLoggedAccount() {return loggedAccount; }
    /**
     * Get the associated Bank instance of the currently logged account.
     */
    public String getAssocBank(){ return assocBank;}
    /**
     * Verifies if some account is currently logged in.
     */
    public boolean isLoggedIn() {return loggedAccount != null;}

    /**
     * Checks inputted credentials during account login.
     * @param accountNum Account number
     * @param passcode passcode
     * @return account object if it passes verification. Null if not.
     */
    public static Account checkCredentials(String accountNum, String passcode){
        Account account = AccountDBManager.fetchAccount(accountNum);
        if (account != null && account.getPin().equals(passcode)) {
            return account;
        }
        return null;
    }
    /**
     * Create a login session based on the logged user account.
     * @param account Account that has successfully logged in
     */
    public void setLogSession(Account account){
        if (loggedAccount == null && assocBank == null) {
            loggedAccount = account;
            assocBank = account.getBankID();
        }
    }
    /**
     * Destroy the log session of the previously logged user account.
     */
    public void destroyLogSession(){
        if (loggedAccount != null && assocBank != null) {
            loggedAccount = null;
            assocBank = null;
        }
    }
}

