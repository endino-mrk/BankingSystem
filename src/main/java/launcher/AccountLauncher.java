package launcher;

import account.Account;
import database.sqlite.BankDBManager;
import main.Main;
import services.account.AccountLoginService;
import services.bank.BankDisplayerService;

public class AccountLauncher {
    protected static AccountLoginService logSession = new AccountLoginService();

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS


    public static AccountLoginService getLogSession() {
        return logSession;
    }

    public static void accountInit(){
        // menu "LOGGING TO AN ACCOUNT"
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
}
