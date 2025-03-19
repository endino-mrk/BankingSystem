package services.account;

import account.Account;
import bank.Bank;
import database.sqlite.BankDBManager;

/**
 * Class responsible for initializing bank login sessions
 */
public class AccountLoginService {
    private static Account loggedAccount = null;
    private static Bank assocBank = null;
    /**
     * Get the Account instance of the currently logged account.
     */
    public static Account getLoggedAccount() {return loggedAccount; }
    /**
     * Get the associated Bank instance of the currently logged account.
     */
    public static Bank getAssocBank(){ return assocBank;}
    /**
     * Verifies if some account is currently logged in.
     */
    public static boolean isLoggedIn() {return loggedAccount != null;}
    /**
     * Bank selection screen before the user is prompted to login. User is prompted for the Bank ID
     * with corresponding bank name.
     * @param bankID bank id of the associated bank
     * @return Bank object based on selected ID
     */
    public static Bank selectBank(String bankID) {
//        assocBank = SQLiteBankRepository.getInstance().get(bankID); // fetch bank using bank retriever
//        return assocBank;
        return null;
    }
    /**
     * Checks inputted credentials during account login.
     * @param accountNum Account number
     * @param passcode passcode
     * @return account object if it passes verification. Null if not.
     */
    public static Account checkCredentials(String accountNum, String passcode){
        //!!!!fetch account directly from database using the associated bank
        //return database verified account
        return null;
    }
    /**
     * Create a login session based on the logged user account.
     * @param account Account that has successfully logged in
     */
    public static void setLogSession(Account account){
        if (loggedAccount == null && assocBank == null) {
            loggedAccount = account;
            assocBank = BankDBManager.fetchBank(account.getBankID());
        }
    }
    /**
     * Destroy the log session of the previously logged user account.
     */
    public static void destroyLogSession(){
        if (loggedAccount != null && assocBank != null) {
            loggedAccount = null;
            assocBank = null;
        }
    }
}

