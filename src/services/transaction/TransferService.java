//package services.transaction;
//
//import account.Account;
//import account.BalanceHolder;
//import account.IllegalAccountType;
//import services.BalanceManager;
//import bank.Bank;
//
///**
// * Manages fund transfers between accounts.
// */
//public class TransferService {
//    public static boolean transfer(Account source, Account recipient, double amount) throws IllegalAccountType {
//
//        if (!(recipient instanceof BalanceHolder && source instanceof BalanceHolder)) {
//            throw new IllegalAccountType("Account cannot transfer and receive funds.");
//        }
//
//        // database method use to fetch recipient using account number
//        if(BankLauncher.findAccount(recipient.getAccountNumber()) != null) {
//            if(BalanceManager.hasEnoughBalance((BalanceHolder) source, amount)) {
//                // Subtracts amount from source account balance
//                BalanceManager.adjustAccountBalance((BalanceHolder) source, -amount);
//
//                // If transferring to another bank, adjusts amount so processing fee is not accounted for in the fund transfer amount
//                if (!(Bank.accountExists(sourceBank, recipient.getAccountNumber()))) {
//                    amount -= source.getBank().getProcessingFee();
//                }
//
//                // Adds amount to recipient account balance
//                BalanceManager.adjustAccountBalance((BalanceHolder) recipient, amount);
//                //insert update amount to database
//                System.out.printf("Successfully transferred %.2f from this account to account with account number %s", amount, recipient.getAccountNumber());
//                return true;
//            }
//            BalanceManager.insufficientBalance();
//        } else {
//            System.out.println("Recipient account does not exist.");
//        }
//        return false;
//    }
//}
