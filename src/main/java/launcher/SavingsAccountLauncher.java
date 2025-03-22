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

            Main.setOption();
            int choice = Main.getOption();

            if (choice == 1) {
                System.out.println("ACCOUNT BALANCE: " + account.getBalance());
            }
            else if (choice == 2){
                depositProcess(account);
            }
            else if (choice == 3){
                withdrawalProcess(account);
            }
            else if (choice == 4){
                transferProcess(account);
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
    public static void depositProcess(SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount: ");

        DepositService.cashDeposit(account, amount.getFieldValue());
    }

    public static void withdrawalProcess(SavingsAccount account){
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount: ");

        WithdrawService.withdraw(account, amount.getFieldValue());
    }

    public static void transferProcess(SavingsAccount account) {
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<String, Integer> bankID = new Field<>("bankID", String.class, 11, new Field.StringFieldLengthValidator());
        Field<String, Integer> recipientID = new Field<>("recipientID", String.class, 10, new Field.StringFieldLengthValidator());
        // display banks to transfer to
        BankDisplayerService.showBanks();
        bankID.setFieldValue("Enter Bank ID: ");

        if(!BankDBManager.bankExists(bankID.getFieldValue())){
            System.out.println("Invalid Bank ID.");
            return;
        }
        // gets bank processing fee. if recipient is from a different bank, fetches recipient bank's fee
        double processingFee = 0.0;
        if(!bankID.getFieldValue().equals(account.getBankID())) {
            processingFee = BankDBManager.getBankProcessingFee(bankID.getFieldValue());
        }

        recipientID.setFieldValue("Enter Recipient ID: ");
        // only proceed if the recipient ID is not the same ID with the source account ID
        if (!recipientID.getFieldValue().equals(account.getAccountNumber())){
            // only proceeds with transfer if recipient is a Savings Account
            if (AccountDBManager.existsInSavings(recipientID.getFieldValue())) {
                amount.setFieldValue("Enter amount to be transferred: ");
                TransferService.transfer(account, recipientID.getFieldValue(), amount.getFieldValue(), processingFee);
            } else {
                System.out.println("Invalid Recipient account.");
            }
        } else {
            System.out.println("Recipient ID must not be the same with the Source Account ID.");
        }
    }
}