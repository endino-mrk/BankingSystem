package launcher;


import database.sqlite.BankDBManager;
import bank.Bank;
import main.Main;
import services.bank.BankDisplayerService;
import services.bank.BankLoginService;

public class BankLauncher {
    private static BankLoginService logSession = new BankLoginService();

    public static BankLoginService getLogSession() {
        return logSession;
    }

    /**
     * Bank interaction interface.
     */
    public static void bankInit() {
        if (!logSession.isLogged()) {
            System.out.println("Not logged in to any bank.");
            return;
        }

        quack:
        while (true) {
            Main.showMenuHeader("Bank Menu");
            Main.showMenu(31);
            String opt = Main.prompt("\nSelect an option: ", true);

            switch (opt) {
                case "1":
                    BankDisplayerService.showAccounts(logSession.getLoggedBank().getID());
                    break;
                case "2":
                    BankDisplayerService.createAccounts(logSession.getLoggedBank());
                    break;
                case "3":
                    logSession.logout();
                    break quack;
                default:
                    System.out.println("Input error: Invalid input. \n");
            }
        }

        System.out.println("Logged out of the bank.");
    }

    /**
     * Bank login interface.
     */
    public static void bankLogin() {
        if (logSession.isLogged()) {
            System.out.printf("%s is currently logged in.\n", logSession.getLoggedBank().getName());
            return;
        }

        String bankID = Main.prompt("Enter ID: ", true);

        String passcode = Main.prompt("Enter passcode: ", true);

        Bank bank = BankDBManager.fetchBank(bankID);
        if (bank != null && bank.getPasscode().equals(passcode)) {
            logSession.setLogSession(bank);
            System.out.println("Logged in to " + bank.getName() + " bank.");
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }
}

