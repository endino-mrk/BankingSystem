package services.bank;

import bank.Bank;
import database.sqlite.BankDBManager;
import main.Main;
import services.account.AccountCreationService;

import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for displaying bank accounts
 */
public class BankDisplayerService {

    /**
     * Output a menu of all registered or created banks in this session.
     */
    public static void showBanks() {
        // fetch ALL BANKS registered in the database
        HashMap<String, String> banks = BankDBManager.getBanks();
        if (banks != null) {
            Main.showMenuHeader("Registered Banks");
            for (Map.Entry<String, String> entry : banks.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        } else {
            System.out.println("There are currently no banks registered in the system.");
        }
    }


    /**
     * Show the accounts registered to this bank. Must prompt the user to select which type of
     * accounts to show: (1) Credit Accounts, (2) Savings Accounts, and (3) All.
     */
    public static void showAccounts(String bankID) {
        while (true) {
            Main.showMenuHeader("Show Accounts");
            Main.showMenu(32);
            Main.setOption();

            if (Main.getOption() == 1) {
                Main.showMenuHeader("All Credit Accounts");

                HashMap<String, String> creditAccounts = BankDBManager.getCreditAccounts(bankID);
                if (creditAccounts != null) {
                    for (Map.Entry<String, String> entry : creditAccounts.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                } else {
                    System.out.println("There are no currently reigstered credit accounts in this bank.");
                }
                break;
            }
            else if (Main.getOption() == 2) {
                Main.showMenuHeader("All Savings Accounts");

                HashMap<String, String> savingsAccounts = BankDBManager.getSavingsAccounts(bankID);
                if (savingsAccounts != null) {
                    for (Map.Entry<String, String> entry : savingsAccounts.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                } else {
                    System.out.println("There are no currently reigstered savings accounts in this bank.");
                }
                break;
            }
            else if (Main.getOption() == 3) {
                Main.showMenuHeader("All Accounts");

                HashMap<String, String> savingsAccounts = BankDBManager.getAllAccounts(bankID);
                if (savingsAccounts != null) {
                    for (Map.Entry<String, String> entry : savingsAccounts.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                } else {
                    System.out.println("There are no currently accounts in this bank.");
                }
                break;
            }
            else if (Main.getOption() == 4) {
                break;
            }
        }
    }

    /**
     * Bank interaction when creating new accounts for the currently logged in bank.
     */
    public static void createAccounts(Bank bank) {
        Main.showMenuHeader("Creating New Account");
        Main.showMenu(33);
        Main.setOption();


        if (Main.getOption() == 1) {
            AccountCreationService.createNewCreditAccount(bank);
//            .createNewCreditAccount();
        }
        else if (Main.getOption() == 2) {
            AccountCreationService.createNewSavingsAccount(bank);
        }

        String t = (Main.getOption() == 1) ? "Credit" : "Savings";
        System.out.println(t + " account has been created and added to bank.");
    }
}

