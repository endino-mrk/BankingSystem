package launcher;

import bank.Bank;

import java.util.ArrayList;
import java.util.Comparator;

public class BankLauncher {
    private static ArrayList<Bank> Banks = new ArrayList<>();

    private static Bank loggedBank = null;

    // CLASS METHODS HERE W/ PROPER AND COMPLETE DOC STRINGS

    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        for (Bank b : Banks) {
            if (bankComparator.compare(bank, b) == 0) {
                return b;
            }
        }

        return null;
    }
}
