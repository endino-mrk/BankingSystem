package launcher;
import database.sqlite.AccountDBManager;
import main.Field;
import services.transaction.RecompenseService;
import services.transaction.PaymentService;
import main.Main;
import account.CreditAccount;
import launcher.AccountLauncher;
import account.Account;
import account.BalanceHolder;
import account.LoanHolder;


public class CreditAccountLauncher extends AccountLauncher {
    public static account.CreditAccount getLoggedAccount() {
        return (logSession.getLoggedAccount() instanceof CreditAccount) ? (CreditAccount) logSession.getLoggedAccount() : null;

    }
    ///  initializes credit account interface
    public static void creditAccountInit(){
        account.CreditAccount account = getLoggedAccount();

        while(true){
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);

            Main.setOption();
            int choice = Main.getOption();

            if (choice == 1) {
                account.getLoan();
            }
            else if (choice == 2){
                loan(account);
            }
            else if (choice == 3){
                recompense(account);
            }
            else if (choice == 4) {
                logSession.destroyLogSession();
                System.out.println("logging out");
                return; //terminates
            }
        }
    }
    /**
     * Method that is utilized to loan payment transaction.
     */
    public static void loan(account.CreditAccount account){
        Field<Double, Double> amount = new Field("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<String, String> recipientID = new Field("recipientID", String.class, "", new Field.StringFieldValidator());

        amount.setFieldValue("Enter amount: ");
        recipientID.setFieldValue("Enter recipient's account number: ");

        Account recipient = AccountDBManager.fetchAccount(recipientID.getFieldValue());

        if (!(account instanceof account.BalanceHolder)){
            System.out.println("Can only pay to a savings account");
            return;
        }
        if (recipient == null){
            System.out.println("Recipient's account could not be found.");
            return;
        }
        PaymentService.pay(account,(BalanceHolder) recipient, amount.getFieldValue());
        System.out.printf("Successfully payed %.2f.\n", amount.getFieldValue());
    }
    /**
     * Method that is utilized to recompense payment transaction.
     */
    public static void recompense(CreditAccount account) {
        Field<Double, Double> amount = new Field("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount to recompense: ");

        RecompenseService.recompense(account,amount.getFieldValue());
        System.out.printf("Successfully recompensed %.2f.\n", amount.getFieldValue());

    }
}
