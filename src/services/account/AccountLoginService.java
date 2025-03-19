package services.account;

import account.Account;
import bank.Bank;

public class AccountLoginService {
    private Account loggedAccount;
    private Bank assocBank;
    public AccountLoginService(Account account) {
        loggedAccount = account;
        assocBank = account.getBank();
    }
}
