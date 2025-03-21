package launcher;
import main.Main;
import main.Field;
import bank.Bank;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import services.transaction.DepositService;
import services.transaction.WithdrawService;
import services.transaction.TransferService;
import services.transaction.TransactionLogService;
import services.bank.BankDisplayerService;
import account.SavingsAccount;
import account.Account;
import account.BalanceHolder;

public class SavingsAccountLauncher extends AccountLauncher {
    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS
    public static SavingsAccount getLoggedAccount(){
        Account loggedAccount = logSession.getLoggedAccount();
        if (loggedAccount instanceof SavingsAccount){
            return (SavingsAccount) loggedAccount;
        }
        return null;
    }

    public static void savingsAccountInit(){
        account.SavingsAccount account = getLoggedAccount();

        if (account == null) {
            System.out.println("No logged-in Savings Account found.");
            return;
        }

        while(true){
            Main.showMenuHeader("Savings Account Menu");
            Main.showMenu(51);

            Main.setOption();
            int choice = Main.getOption();

            if (choice == 1) {
                System.out.println("ACCOUNT BALANCE: " + account.getBalance());
            }
            else if (choice == 2){
                deposit(account);
            }
            else if (choice == 3){
                withdraw(account);
            }
            else if (choice == 4){
                transfer(account);
            }
            else if (choice == 5) {
                TransactionLogService.showTransactions(account);
            }
            else if (choice == 6) {
                logSession.destroyLogSession();
                System.out.println("logging out");
                return; //terminates
            }
        }
    }

    /**
     * Method that is utilized to deposit transaction.
     */
    public static void deposit(account.SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());

        amount.setFieldValue("Enter amount: ");

        DepositService.cashDeposit(account, amount.getFieldValue());
        System.out.printf("Deposited an amount of %.2f\n", amount.getFieldValue());
    }

    public static void withdraw(account.SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());

        amount.setFieldValue("Enter amount: ");

        WithdrawService.withdraw(account, amount.getFieldValue());
        System.out.printf("Withdrew an amount %.2f\n", amount.getFieldValue());
    }

    public static void transfer(account.SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        BankDisplayerService.showBanks();

        String bankID = Main.prompt("Select Bank ID: ", true);

        if(bankID.equals(account.getBankID())){
            amount.setFieldValue("Enter amount: ");
            String recipient_accountID = Main.prompt("Enter Recipient's Account ID Number: ", true);
            Account recipient = AccountDBManager.fetchAccount(recipient_accountID);
            TransferService.transfer(account, (BalanceHolder) recipient, amount.getFieldValue());
        } else {
            Bank bank = BankDBManager.fetchBank(account.getBankID());
            if(bank != null) {
                amount.setFieldValue("Enter amount: ");
                String recipient_accountID = Main.prompt("Enter Recipient's Account ID Number: ", true);
                Account recipient = AccountDBManager.fetchAccount(recipient_accountID);
                TransferService.transfer(bank, account, (BalanceHolder) recipient, amount.getFieldValue());
            } else {
                System.out.println("Invalid Bank ID.");
            }
        }
    }
}