package launcher;
import database.sqlite.AccountDBManager;
import main.Field;
import services.transaction.RecompenseService;
import services.transaction.PaymentService;
import main.Main;
import account.CreditAccount;
import account.Account;
import services.transaction.TransactionLogService;


public class CreditAccountLauncher extends AccountLauncher {
    public static CreditAccount getLoggedAccount() {
        Account loggedAccount = logSession.getLoggedAccount();
//        if (loggedAccount instanceof CreditAccount) {
        return (CreditAccount) loggedAccount;
//        }
    }
    ///  initializes credit account interface
    public static void creditAccountInit(){
        account.CreditAccount account = getLoggedAccount();

        if (account == null) {
            System.out.println("No logged-in credit account found.");
            return;
        }

        while(true){
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);
            String choice = Main.prompt("Select option: ", false);

            switch (choice){
                case "1":
                    System.out.println("ACCOUNT CREDIT: " + account.getLoan());
                    break;
                case "2":
                    loan(account);
                    break;
                case "3" :
                    recompense(account);
                    break;
                case "4":
                    TransactionLogService.showTransactions(account);
                    break;
                case "5":
                    logSession.destroyLogSession();
                    System.out.println("Logging out");
                    return; //terminates
                default:
                    System.out.println("Input error: Invalid input. \n");
            }
        }
    }
    /**
     * Method that is utilized to loan payment transaction.
     */
    public static void loan(CreditAccount account){
        Field<Double, Double> amount = new Field("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<String, String> recipientID = new Field("recipientID", String.class, "", new Field.StringFieldValidator());

        recipientID.setFieldValue("Enter recipient's account number: ");

        // fetch recipient type
//        String recipient = AccountDBManager.fetchType(recipientID.getFieldValue());
//
//        if (!recipient.equals("1")) {
//            System.out.println("You can only pay to a savings account.");
//            return;
//        }

        if (AccountDBManager.existsInSavings(recipientID.getFieldValue())) {
            amount.setFieldValue("Enter amount: ");
            PaymentService.pay(account, recipientID.getFieldValue(), amount.getFieldValue());
        } else {
            System.out.println("Invalid recipient account.");
        }
    }
    /**
     * Method that is utilized to recompense payment transaction.
     */
    public static void recompense(CreditAccount account) {
        Field<Double, Double> amount = new Field<>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount to recompense: ");

        RecompenseService.recompense(account,amount.getFieldValue());
    }
}
