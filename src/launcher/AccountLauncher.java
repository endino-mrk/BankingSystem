package launcher;

import account.Account;
import account.CreditAccount;
import account.SavingsAccount;
import bank.Bank;
import database.sqlite.BankDBManager;
import main.Main;
import services.account.AccountLoginService;
import services.bank.BankDisplayerService;

public class AccountLauncher {
    private static AccountLoginService logSession = new AccountLoginService();

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public static void accountInit(){
        if (!logSession.isLoggedIn()){
            System.out.println("No account is logged in");
            return;
        }

        Account loggedAccount = logSession.getLoggedAccount();
        // executes the appropriate account launcher initialization
        loggedAccount.init();
    }

    /**
     * Login an account. Bank must be selected first before logging in. Account existence will depend on the selected bank.
     */
    public static void accountLogin(){
        if (logSession.isLoggedIn()){
            System.out.printf("%s is currently logged in.\n", logSession.getLoggedAccount().getOwnerFullName());
            System.out.printf("Associated Bank: %s.\n", logSession.getAssocBank());
            return;
        }

        // show all banks registered in the system
        BankDisplayerService.showBanks();
        // prompt user to enter bank ID associated with their account
        while (true) {
            String bankID = Main.prompt("Enter Bank ID to Access Your Account: ", true);
            if (BankDBManager.bankExists(bankID)) break;
            System.out.println("Bank Unavailable. There is no bank with corresponding bank ID registered in the system.");
        }

        String accountNum = Main.prompt("Enter Account Number: ", true);
        String passcode = Main.prompt("Enter Passcode: ", true);
        Account account = AccountLoginService.checkCredentials(accountNum, passcode);

        if (account != null){
            logSession.setLogSession(account);
            System.out.println("Login successful");
        }else {
            System.out.println("Login failed.");
        }

    }
    /**
     * Bank selection screen before the user is prompted to login. User is prompted for the Bank ID
     * with corresponding bank name.
     * @param bankID bank id of the associated bank
     * @return Bank object based on selected ID
     */
//    private static Bank selectBank() {
////        assocBank = SQLiteBankRepository.getInstance().get(bankID); // fetch bank using bank retriever
////        return assocBank;
//        String bankID;
//        do {
//            bankID = Main.prompt("Enter Bank ID to Access Your Account: ", true);
//        } while (!BankDBManager.bankExists(bankID));
//
//
//        return null;
//    }
}
