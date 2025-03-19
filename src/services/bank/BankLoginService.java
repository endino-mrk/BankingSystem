package services.bank;

import bank.Bank;

public class BankLoginService {
    private Bank loggedBank;
    public BankLoginService(Bank bank) {
        loggedBank = bank;
    }
}
