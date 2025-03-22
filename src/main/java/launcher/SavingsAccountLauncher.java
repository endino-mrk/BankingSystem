package launcher;
import database.sqlite.AccountDBManager;
import database.sqlite.BankDBManager;
import main.Main;
import main.Field;
import services.transaction.DepositService;
import services.transaction.WithdrawService;
import services.transaction.TransferService;
import services.transaction.TransactionLogService;
import services.bank.BankDisplayerService;
import account.SavingsAccount;
import account.Account;


public class SavingsAccountLauncher extends AccountLauncher {
    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS
    public static SavingsAccount getLoggedAccount(){
        Account loggedAccount = logSession.getLoggedAccount();
//        if (loggedAccount instanceof SavingsAccount){
            return (SavingsAccount) loggedAccount;
//        }
//        return null;
    }

    public static void savingsAccountInit(){
        SavingsAccount account = getLoggedAccount();

        if (account == null) {
            System.out.println("No logged-in Savings Account found.");
            return;
        }

        while(true){
            Main.showMenuHeader("Savings Account Menu");
            Main.showMenu(51);

            String choice = Main.prompt("Select option: ", false);

            switch (choice){
                case "1":
                    System.out.println("ACCOUNT BALANCE: " + account.getBalance());
                    break;

                case "2":
                    depositProcess(account);
                    break;
                case "3":
                    withdrawalProcess(account);
                    break;

                case "4":
                    transferProcess(account);
                    break;

                case "5" :
                    TransactionLogService.showTransactions(account);
                    break;

                case "6":
                    logSession.destroyLogSession();
                    System.out.println("Logging out");
                    return;

                default:
                    System.out.println("Input error: Invalid input. \n");
            }
        }
    }

    /**
     * Method that is utilized to deposit transaction.
     */
    public static void depositProcess(SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount: ");

        DepositService.cashDeposit(account, amount.getFieldValue());
    }
    /**
     * Method that is utilized to withdraw transaction.
     */
    public static void withdrawalProcess(SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount: ");

        WithdrawService.withdraw(account, amount.getFieldValue());
    }
    /**
     * Method that is utilized to fund transfer transaction.
     */
    public static void transferProcess(SavingsAccount account) {
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        // display banks to transfer to
        BankDisplayerService.showBanks();
        String bankID = Main.prompt("Select Bank ID: ", true);

        // cannot transfer money to its self
        if(!BankDBManager.bankExists(bankID)){
            System.out.println("Invalid Bank ID.");
            return;
        }
        // gets bank processing fee. if recipient is from a different bank, fetches recipient bank's fee
        double processingFee = 0.0;
        if(!bankID.equals(account.getBankID())) {
            processingFee = BankDBManager.getBankProcessingFee(bankID);
        }

        String recipientID = Main.prompt("Enter Recipient's Account Number: ", true);
        // only proceeds with transfer if recipient is a Savings Account
        if (AccountDBManager.existsInSavings(recipientID)) {
            amount.setFieldValue("Enter amount to be transferred: ");
            TransferService.transfer(account, recipientID, amount.getFieldValue(), processingFee);
        } else {
            System.out.println("Invalid recipient account.");
        }
    }
}
