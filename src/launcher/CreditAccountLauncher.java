package launcher.account;
import launcher.AccountLauncher;
import services.account.AccountLoginService;
import services.transaction.RecompenseService;
import services.transaction.PaymentService;
import account.*;
import main.Main;


public class CreditAccountLauncher extends AccountLauncher {
    public static CreditAccount getLoggedAccount() {
        return (AccountLoginService.getLoggedAccount() instanceof CreditAccount) ? (CreditAccount) AccountLoginService.getLoggedAccount() : null;

    }
    ///  initializes credit account interface
    public static void creditAccountInit(){
        CreditAccount account = getLoggedAccount();

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
                AccountLoginService.destroyLogSession();
                System.out.println("logging out");
                return; //terminates
            }
        }
    }
    /**
     * Method that is utilized to loan payment transaction.
     */
    public static void loan(CreditAccount account){
        double amount = Double.parseDouble(Main.prompt("Enter payment amount", false));
        String recipientAccountNum = Main.prompt("Enter recipient's account number: ", false);
        //fetch recipient account DATABASE
        // Account recipient = null;// dunno pa ani

        if (!(account instanceof BalanceHolder)){
            System.out.println("Can only pay to a savings account");
            return;
        }
        if (recipient == null){
            System.out.println("Recipient's account could not be found.");
            return;
        }
        PaymentService.pay(account,(BalanceHolder) recipient,amount);
        System.out.printf("Successfully payed %.2f.\n", amount);
    }
    /**
     * Method that is utilized to recompense payment transaction.
     */
    public static void recompense(CreditAccount account) {
        double amount = Double.parseDouble(Main.prompt("Enter recompense amount", false));
        RecompenseService.recompense(account,amount);
        System.out.printf("Successfully recompensed %.2f.\n", amount);

    }
}
